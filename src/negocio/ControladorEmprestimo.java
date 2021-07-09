package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import exceptions.*;
import negocio.beans.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ControladorEmprestimo {
    private static final long QTD_DIAS_PARA_1_PAGAMENTO = 30;
    private static final long QTD_DIAS_PARA_PROX_PAGAMENTO = 30;
    private static final float CONFIANCA_PAGAMENTO_INICIAL = 50.0F;
    private Repositorio<Emprestimo> repoEmprestimo;
    private static long contadorProtocolo = 1;

    public ControladorEmprestimo() {
        this.repoEmprestimo = new RepositorioCRUD<>();
    }

    /**
     * Método que cria um empréstimo a partir de uma proposta aprovada por um empregado, neste método são aplicadas
     * regras de negócio, como a primeira data de pagamento do empréstimo que se baseia na
     * {@code QTD_DIAS_PARA_1_PAGAMENTO}, o responsável pelo empréstimo {@code empregado}, e da
     * {@code CONFIANCA_PAGAMENTO_INICIAL}, que se trata da probalidade do próximo pagamento.
     *
     * @param proposta se refere a uma proposta aprovada e a ser transformada em empréstimo aprovado.
     * @param empregado se refere ao objeto {@code Empregado} que será responsável pelo empréstimo.
     * @throws EmprestimoDuplicadoException poderá acontecer caso o {@code Emprestimo} já esteja cadastro no sistema de
     * {@code repoEmprestimo}.
     */
    public void criarEmprestimo(Proposta proposta, Empregado empregado) throws EmprestimoDuplicadoException,
            BensInexistenteException {
        //Evitar criação de empréstimo fantasma
        if (proposta == null || empregado == null) return;

        Emprestimo emprestimo = new Emprestimo(proposta);
        LocalDate dataPagamento = LocalDate.now();

        //Atribuindo a primeira data de pagamento do empréstimo
        dataPagamento = dataPagamento.plusDays(QTD_DIAS_PARA_1_PAGAMENTO);
        emprestimo.setDataPagamento(dataPagamento);

        //Atribuindo o empregado responsável pelo empréstimo
        emprestimo.setEmpregado(empregado);

        //Atribuindo a confiança de pagamento inicial
        emprestimo.setConfiancaPagamento(CONFIANCA_PAGAMENTO_INICIAL);

        //Atribuindo o número de protocolo disponível para cadastro
        emprestimo.setNumProtocolo(contadorProtocolo);

        try {
            repoEmprestimo.inserir(emprestimo);
            Fachada.getInstance().aplicarBensComoGarantia(proposta.getGarantia());
            contadorProtocolo++;
        } catch (ObjetoDuplicadoException e) {
            throw new EmprestimoDuplicadoException("Parece que esse empréstimo já existe!");
        }
    }

    /**
     * Método que faz a busca de um {@code Emprestimo} no repositório de Empréstimos por meio de um número de protocolo.
     *
     * @param numProtocolo se refere ao número único dado a cada empréstimo, quando cada empréstimo é criado.
     * @return retorna um {@code Emprestimo} referente ao número de protocolo pedido do repositório.
     * @throws EmprestimoInexistenteException poderá acontecer caso o número de protocolo seja inválido ou se a proposta
     * não existir no repositório de empréstimos.
     */
    public Emprestimo buscarEmprestimo(long numProtocolo) throws EmprestimoInexistenteException {
        if (numProtocolo < 1) throw new EmprestimoInexistenteException("O número de protocolo é inválido");
        Emprestimo emprestimo = null;

        List<Emprestimo> emprestimoList = new ArrayList<>(this.repoEmprestimo.listar());
        boolean emprestimoEncontrado = false;
        for (int i = 0; i < emprestimoList.size() && !emprestimoEncontrado; i++) {
            emprestimo = emprestimoList.get(i);
            if (numProtocolo == emprestimo.getNumProtocolo()) {
                emprestimoEncontrado = true;
            }
        }

        if (!emprestimoEncontrado) throw new EmprestimoInexistenteException("O Protocolo para esse empréstimo não " +
                "existe!");

        return emprestimo;
    }

    /**
     * Método que procura por detalhes de um empréstimo efetuado em uma determinada {@code dataEmprestimo} por um
     * {@code cliente}, visto que, há apenas 1 empréstimo do mesmo cliente por dia. O método lista os empréstimos de
     * um cliente e procura por empréstimos por data.
     *
     * @param cliente se refere ao {@code Cliente} que realizou o {@code Emprestimo}.
     * @param dataEmprestimo se refere a data em que o {@code Cliente} realizou o {@code Emprestimo}.
     * @return uma {@code String} de dados, com base no método {@code toString()} do objeto {@code Emprestimo}.
     * @throws EmprestimoInexistenteException poderá acontecer caso não exista um empréstimo para esse {@code cliente}
     * ou não existe um empréstimo para esse {@code cliente} na determinada {@code dataEmprestimo}.
     */
    public String emprestimoEmDetalhe(Cliente cliente, LocalDate dataEmprestimo) throws EmprestimoInexistenteException {
        if (cliente == null || dataEmprestimo == null) throw new EmprestimoInexistenteException("Essa requisição " +
                "parece inválida!");

        List<Emprestimo> listaEmprestimosCliente = new ArrayList<>(this.listarEmprestimosCliente(cliente.getUid()));
        String emprestimoEmDetalhe = "";

        boolean emprestimoEncontrado = false;
        for (int i = 0; i < listaEmprestimosCliente.size() && !emprestimoEncontrado; i++) {
            Emprestimo emprestimo = listaEmprestimosCliente.get(i);
            if (dataEmprestimo.equals(emprestimo.getData())) {
                emprestimoEncontrado = true;
                emprestimoEmDetalhe = emprestimo.toString();
            }
        }

        if (!emprestimoEncontrado) {
            throw new EmprestimoInexistenteException("Empréstimo não encontrado!");
        }

        return emprestimoEmDetalhe;
    }

    /**
     * Método que retorna todos os empréstimos feitos pelo cliente identificado por seu {@code uid} através de uma
     * {@code List} do qual armazena todos os objetos do tipo {@code Emprestimo}.
     *
     * @param uidCliente se refere ao identificador único e exclusivo do cliente.
     * @return List de empréstimos filtrados por cliente.
     */
    public List<Emprestimo> listarEmprestimosCliente(long uidCliente) {
        return this.repoEmprestimo.listar().stream()
                                           .filter(emprestimo -> emprestimo.getCliente().getUid() == uidCliente)
                                           .collect(Collectors.toList());
    }

    /**
     * Método que lista todas as comissões de empréstimos de que um empregado é responsável.
     *
     * @param empregado se refere ao empregado que se quer listar os empréstimos no qual ele é responsável.
     * @return uma lista de empréstimos do qual o empregado é responsável.
     */
    public List<Emprestimo> listarComissoesEmprestimo(Empregado empregado) {
        List<Emprestimo> comissoesEmprestimo = new ArrayList<>();

        for (Emprestimo emprestimo : this.repoEmprestimo.listar()) {
            if (emprestimo.getEmpregado().getUid() == empregado.getUid())
                comissoesEmprestimo.add(emprestimo);
        }

        return comissoesEmprestimo;
    }

    /**
     * Método que retorna uma {@code List} de {@code Emprestimo} referente ao {@code prazo} de todos os
     * {@code Emprestimo} que não pagaram até a data de pagamento.
     * 
     * @return Lista de Empréstimos filtrados por data de pagamento.
     */
    public List<Emprestimo> listarDevedores() {
        List<Emprestimo> devedores = new ArrayList<>();

        for (Emprestimo emprestimo : this.repoEmprestimo.listar()) {
            LocalDate prazo = emprestimo.getData().plusDays(emprestimo.getPrazo());
            long dataPagamento = ChronoUnit.DAYS.between(emprestimo.getDataPagamento(), prazo);
            if(dataPagamento < 0){
                //Preencher Lista
                devedores.add(emprestimo);
            }
        }

        return devedores;
    }

    /**
     * Método que retorna uma {@code List} de {@code Emprestimo} referente ao {@code prazo} de todos os
     * {@code Emprestimo} que não foram pagos até a data de pagamento, porém possuem um {@code score} alto.
     * 
     * @return Lista de Empréstimos filtrados por data de pagamento e score alto.
     */
    public List<Emprestimo> listarDevedoresProtegidos() {
        List<Emprestimo> devedoresProtegidos = new ArrayList<>();

        for (Emprestimo emprestimo : this.repoEmprestimo.listar()) {
            LocalDate prazo = emprestimo.getData().plusDays(emprestimo.getPrazo());
            long dataPagamento = ChronoUnit.DAYS.between(emprestimo.getDataPagamento(), prazo);
            if(dataPagamento < 0 && emprestimo.getCliente().getScore() >= 75){
                //Preencher Lista
                devedoresProtegidos.add(emprestimo);
            }
        }
        return devedoresProtegidos;
    }

    /**
     * Método que retorna uma {@code List} de {@code Emprestimo} referente ao {@code prazo} de todos os
     * {@code Emprestimo} que não foram pagos até o {@code prazo} e além disso possuem um {@code score} baixo.
     * 
     * @return Lista de Empréstimo filtrados por data de pagamento e score baixo.
     */
    public List<Emprestimo> listarDevedoresAltoRisco() {
        List<Emprestimo> devedoresAltoRisco = new ArrayList<>();

        for (Emprestimo emprestimo : this.repoEmprestimo.listar()) {
            LocalDate prazo = emprestimo.getData().plusDays(emprestimo.getPrazo());
            long dataPagamento = ChronoUnit.DAYS.between(emprestimo.getDataPagamento(), prazo);
            if(dataPagamento < 0 && emprestimo.getCliente().getScore() <= 35){
                //Preencher mapa
                devedoresAltoRisco.add(emprestimo);
            }
        }

        return devedoresAltoRisco;
    }

    /**
     * Método privado que atualiza o repositório de empréstimos ao inserir um novo empréstimo, portanto que o número
     * de protocolo seja único.
     *
     * @param novoObjEmprestimo se refere ao objeto atualiza que contém o número de protocolo do empréstimo a ser
     *                          atualizado
     * @throws EmprestimoInexistenteException poderá acontecer caso o {@code numProtocolo} do empréstimo não existir
     * no repositórios de empréstimos.
     */
    private void atualizarEmprestimo(Emprestimo novoObjEmprestimo) throws EmprestimoInexistenteException {
        Emprestimo antigoObjEmprestimo = this.buscarEmprestimo(novoObjEmprestimo.getNumProtocolo());
        try {
            this.repoEmprestimo.atualizar(antigoObjEmprestimo,novoObjEmprestimo);
        } catch (ObjetoInexistenteException e) {
            throw new EmprestimoInexistenteException("Empréstimo não encontrado!");
        }
    }

    /**
     * Método privado que quita um empréstimo e o remove do repositório de empréstimos, portanto que o número do
     * protocolo seja único a aquele empréstimo.
     *
     * @param numProtocolo se refere ao número de protocolo que se deseja quitar/remover o empréstimo.
     * @throws EmprestimoInexistenteException poderá acontecer caso o empréstimo não exista com o número de protocolo.
     */
    private void quitarEmprestimo(long numProtocolo) throws EmprestimoInexistenteException {
        Emprestimo emprestimoQuitado = this.buscarEmprestimo(numProtocolo);
        try {
            this.repoEmprestimo.remover(emprestimoQuitado);
        } catch (ObjetoInexistenteException e) {
            throw new EmprestimoInexistenteException("Empréstimo não encontrado!");
        }
    }

    /**
     * Método que realiza o pagamento de um empréstimo e atualiza o empréstimo no repositório de Empréstimos
     *
     * @param numProtocolo se refere ao número de protocolo do empréstimo que se deseja realizar um pagamento.
     * @param movimentacao se refere a movimentação que irá debitar do valor da parcela mensal do pagamento.
     * @throws EmprestimoInexistenteException poderá acontecer se não existir um empréstimo com o número de protocolo
     * veículado em {@code numProtocolo}.
     * @throws MovimentacaoDuplicadaException poderá acontecer se já existir uma operação semelhante durante a operação
     * de pagamento do empréstimo.
     */
    public void pagarEmprestimo(long numProtocolo, Movimentacao movimentacao) throws EmprestimoInexistenteException,
            MovimentacaoDuplicadaException {
        Emprestimo emprestimo = this.buscarEmprestimo(numProtocolo);

        //Set da nova data de pagamento após pagar o empréstimo
        if (movimentacao.getValor() == emprestimo.getParcelas()) {
            /* É tratado dessa forma, pois não há um sistema com pagamentos diferenciados, logo todos pagamentos são
             * realizados de acordo com o valor de parcelas.
             */
            emprestimo.setDataPagamento(emprestimo.getDataPagamento().plusDays(QTD_DIAS_PARA_PROX_PAGAMENTO));
        } else {
            return; //Temporário, enquanto não é tratado outros tipos de pagamentos.
        }

        //Set do novo valor devido
        emprestimo.setValor(emprestimo.getValor() - movimentacao.getValor());

        //Caso seja quitado o valor, o empréstimo é quitado
        if (emprestimo.getValor() <= 0) {
            this.quitarEmprestimo(emprestimo.getNumProtocolo());
        } else {
            this.atualizarEmprestimo(emprestimo);
        }
        Fachada.getInstance().gerarMovimentacao(movimentacao);
    }
    
    public double calcularValorParcelas(Emprestimo emprestimo) {
    	double valorParcela = emprestimo.getValor() / emprestimo.getParcelas();
    	return valorParcela;
    }
    
    public LocalDate calcularProximoPrazo(Emprestimo emprestimo, int parcela) {
    	long dias = ChronoUnit.DAYS.between(emprestimo.getData(), emprestimo.getData().plusDays(emprestimo.getPrazo()));
    	long proximaData = (long) (dias / emprestimo.getParcelas());
    	LocalDate proximoPrazo = emprestimo.getData().plusDays(proximaData*parcela);
    	return proximoPrazo;
    }
    
    public void calcularScore(Cliente cliente) {
    	List<Emprestimo> emprestimos = listarEmprestimosCliente(cliente.getUid());
    	int score = 0;
    	for (Emprestimo emprestimo : emprestimos) {
			long diferencaEmDias = ChronoUnit.DAYS.between(emprestimo.getDataPagamento(), emprestimo.getData().plusDays(emprestimo.getPrazo()));
			if (diferencaEmDias >= 5) score = cliente.getScore() + 2;
			else if (diferencaEmDias >= 0) score = cliente.getScore() + 1;
			else if (diferencaEmDias > -7) score = cliente.getScore() - 1;
			else {
				int semana = (int) diferencaEmDias/7;
				score = cliente.getScore() - (2 * semana);
			}
		}
    	cliente.setScore(score);
    }
}
