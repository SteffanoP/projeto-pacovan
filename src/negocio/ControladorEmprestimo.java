package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import exceptions.EmprestimoDuplicadoException;
import exceptions.EmprestimoInexistenteException;
import exceptions.ObjetoDuplicadoException;
import negocio.beans.Cliente;
import negocio.beans.Empregado;
import negocio.beans.Emprestimo;
import negocio.beans.Proposta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorEmprestimo {
    private static final long QTD_DIAS_PARA_1_PAGAMENTO = 30;
    private static final float CONFIANCA_PAGAMENTO_INICIAL = 50.0F;
    private Repositorio<Emprestimo> repoEmprestimo;

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
    public void criarEmprestimo(Proposta proposta, Empregado empregado) throws EmprestimoDuplicadoException {
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

        try {
            repoEmprestimo.inserir(emprestimo);
        } catch (ObjetoDuplicadoException e) {
            throw new EmprestimoDuplicadoException("Parece que esse empréstimo já existe!");
        }

    }

    public String emprestimoEmDetalhe() {
        Emprestimo emprestimo = null;

        return emprestimo.toString();
    }

    public List<Emprestimo> listarEmprestimosCliente(long uidCliente) {
        List<Emprestimo> emprestimosCliente = new ArrayList<>();

        return emprestimosCliente;
    }

    public List<Emprestimo> listarComissõesEmprestimo() {
        List<Emprestimo> comissoesEmprestimo = new ArrayList<>();

        return comissoesEmprestimo;
    }

    public Map<Cliente, Emprestimo> listarDevedores() {
        Map<Cliente, Emprestimo> listaDevedores = new HashMap<>();

        return listaDevedores;
    }

    public Map<Cliente, Emprestimo> listarDevedoresProtegidos() {
        Map<Cliente, Emprestimo> listaDevedores = new HashMap<>();

        return listaDevedores;
    }

    public Map<Cliente, Emprestimo> listarDevedoresAltoRisco() {
        Map<Cliente, Emprestimo> listaDevedores = new HashMap<>();

        return listaDevedores;
    }
}
