package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import exceptions.*;
import negocio.beans.Bens;
import negocio.beans.CategoriaBens;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ControladorBENS {
    private Repositorio<Bens> repoBENS;

    public ControladorBENS() {
        this.repoBENS = new RepositorioCRUD<>();
    }

    /**
     * Método que insere bem no repositório.
     * @param bens bem a ser inserido no repositório. O objeto receberá a data do cadastro e receberá os parametros
     *             falso para o atributo {@code garantia} e verdadeiro para o atributo {@code pendente}.
     * @throws BensDuplicadoException caso o bem cadastrado já tenha sido inserido.
     */
    public void inserirBens(Bens bens) throws BensDuplicadoException {
        //Atribui a data de cadastro do bem
        bens.setDataCadastro(LocalDate.now());
        //Seta os valores para os atributos pendente e garantia
        bens.setPendente(true);
        bens.setGarantia(false);

        try {
            this.repoBENS.inserir(bens);
        } catch (ObjetoDuplicadoException e) {
            throw new BensDuplicadoException("Bens já registrado no sistema!");
        }
    }

    /**
     * Método que faz a busca de um BENS do cliente por meio do nome de seu BENS. Utiliza a técnica lambda/stream de
     * java 8 que filtra de acordo com o nome do BENS e seleciona o elemento (caso haja uma lista de elementos
     * seleciona apenas o último elemento de uma lista; caso para duplicados) de mesmo nome.
     *
     * @param uidCliente se refere ao {@code uidCliente} do qual se trata o BENS.
     * @param nomeBens se refere ao {@code nome} do Bens do qual se trata o BENS.
     * @return irá retornar o BENS pesquisado sobre os parâmetros anteriores
     */
    public Bens buscarBensCliente(long uidCliente, String nomeBens) {
        List<Bens> listBensCliente = new ArrayList<>(this.listarBensCliente(uidCliente));
        return listBensCliente.stream()
                .filter(bens -> bens.getNome().equals(nomeBens))
                .reduce((a,b) -> b) //TODO: Tratar se houver duplicados
                .orElse(null);
    }

    /**
     * Método que retorna todos os BENS cadastrados no repositório de BENS.
     * @return lista de BENS do repositório de Bens
     */
    public List<Bens> listarBens() {
        return this.repoBENS.listar();
    }

    /**
     * Método que lista um {@code List<Bens>} de todos os Bens da empresa, filtrado por um {@code CategoriaBens}
     * parametrizada.
     *
     * @param categoria se refere a categoria de bens que se pretende listar.
     * @return uma lista com os bens filtrados por meio de uma {@code CategoriaBens} com parâmetro.
     */
    public List<Bens> listarBensEmpresaCategoria(CategoriaBens categoria) {
        return this.repoBENS.listar().stream()
                                     .filter(bens -> bens.getCategoria().equals(categoria))
                                     .collect(Collectors.toList());
    }

    /**
     * Método que lista dos Bens do Repositório de Bens, filtrado por Cliente, neste caso pelo {@code uidCliente}.
     *
     * @param uidCliente se refere ao identificador único e exclusivo do cliente que filtra os Bens.
     * @return lista de bens filtrado para o cliente especificado.
     */
    public List<Bens> listarBensCliente(long uidCliente) {
        return this.repoBENS.listar().stream()
                                     .filter(bens -> bens.getCliente().getUid() == uidCliente)
                                     .collect(Collectors.toList());
    }

    /**
     * Método que lista os BENS pendentes do cliente do quais são objetos do tipo {@code Bens} que tem seu atributo
     * {@code pendente true}.
     * 
     * @param uidCliente se refere ao identificador único e exclusivo do cliente.
     * @return Lista de Bens filtrados por cliente e por pendência.
     */
    public List<Bens> listarBensPendentes(long uidCliente) {
        return this.repoBENS.listar().stream()
                                     .filter(bens -> bens.getCliente().getUid() == uidCliente)
                                     .filter(Bens::isPendente)
                                     .collect(Collectors.toList());
    }

    /**
     * Método que lista todos os BENS aprovados criado para armazenar objetos do tipo {@code Bens} que tem seu atributo
     * {@code pendente false}.
     * 
     * @return Lista de Bens filtrados por aprovação.
     */
    public List<Bens> listarBensAprovados() {
        return this.repoBENS.listar().stream().filter(bens -> !bens.isPendente()).collect(Collectors.toList());
    }

    /**
     * Método que lista todos os BENS que são garantia criado para armazenar objetos do tipo {@code Bens} que tem seu
     * atributo {@code garantia true}.
     * 
     * @return Lista de Bens filtrados por garantia.
     */
    public List<Bens> listarBensGarantia() {
        return this.repoBENS.listar().stream().filter(Bens::isGarantia).collect(Collectors.toList());
    }

    public double calcularValorBensCliente(long uidCliente) throws PessoaInexistenteException{
        double valor = 0;
        boolean existevalor  = false;
        List<Bens> valorBenList = repoBENS.listar();
        for(Bens ben: valorBenList) {
            if (ben.getCliente().getUid() == uidCliente){
                existevalor = true;
                valor = ben.getValor() ;
            }
        }if(!existevalor)  throw new PessoaInexistenteException("Cliente Não existe!");

        return valor;
    }

    /**
     * Método que, por meio de lista de Bens, filtra os bens e os aplica como garantia; ou seja realiza o set do
     * atributo de {@code garantia} como {@code true}.
     *
     * @param bensAntigos se refere a lista de Bens ao qual será aplicado como garantia.
     * @throws BensInexistenteException poderá acontecer caso haja um BENS que não pertença ao repositório de BENS
     */
    public void aplicarBensComoGarantia(List<Bens> bensAntigos) throws BensInexistenteException {
        if (bensAntigos == null) return;

        List<Bens> bensComoGarantia = bensAntigos.stream()
                                                 .peek(bens -> {bens.setGarantia(true);})
                                                 .collect(Collectors.toList());
        for (Bens bensAntigo : bensAntigos) {
            for (Bens bensNovos : bensComoGarantia) {
                try {
                    this.alterarBens(bensAntigo, bensNovos);
                } catch (BensInexistenteException e) {
                    throw new BensInexistenteException("Há Bens de garantia que não existem!");
                }
            }
        }
    }

    /**
     * Método que altera um bem por outro no repositório
     * @param bensAntigo bem inicialmente cadastrado.
     * @param bensNovo novo bem a ser inserido para substituir
     * @throws BensInexistenteException caso o bem a ser substituído não exista no repositório.
     */
    public void alterarBens(Bens bensAntigo, Bens bensNovo) throws BensInexistenteException {
        boolean bensExiste = false;

        List<Bens> bensList = this.repoBENS.listar();
        for (int i = 0; (i < bensList.size()) && !bensExiste; i++) {
            Bens bens = bensList.get(i);

            if (bens.equals(bensAntigo)) {
                try {
                    this.repoBENS.atualizar(bensAntigo, bensNovo);
                } catch (ObjetoInexistenteException e) {
                    throw new BensInexistenteException("O bem não existe!");
                }
                bensExiste = true;
            }
        }
    }

    /**
     * Método que remove bens do repositório.
     * @param bens bem a ser removido do repositório.
     * @throws BensInexistenteException caso o bem a ser substituído não exista no repositório.
     */
    public void removerBens(Bens bens) throws BensInexistenteException{
        try {
            this.repoBENS.remover(bens);
        } catch (ObjetoInexistenteException e) {
            throw new BensInexistenteException("O bem não existe!");
        }
    }
}
