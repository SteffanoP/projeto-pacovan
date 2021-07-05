package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import exceptions.*;
import negocio.beans.Bens;
import negocio.beans.CategoriaBens;
import java.time.LocalDate;
import java.util.*;

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
            throw new BensDuplicadoException("Bens jÃ¡ registrado no sistema!");
        }
    }

    public Map<LocalDate,Bens> listarBensEmpresa() {
        NavigableMap<LocalDate, Bens> mapaBens = new TreeMap<>();
        List<Bens> benEmpresaList = repoBENS.listar();
            for( Bens ben : benEmpresaList){
               mapaBens.put(ben.getDataCadastro(), ben);
            }
        return mapaBens;
    }

    /**
     * Método que lista um {@code Map<LocalDate, Bens>} de todos os Bens da empresa, filtrado por uma
     * {@code CategoriaBens} parametrizada.
     *
     * @param categoria se refere a categoria de bens que se pretende listar.
     * @return um {@code Map<LocalDate, Bens>} com os bens filtrados por meio de uma {@code CategoriaBens} como
     * parâmetro.
     */
    public Map<LocalDate, Bens> listarBensEmpresaCategoria(CategoriaBens categoria) {
        NavigableMap<LocalDate, Bens> mapaBens = new TreeMap<>();
        List<Bens> bensList = this.repoBENS.listar();

        for (Bens bens : bensList) {
            if (bens.getCategoria().equals(categoria)) {
                mapaBens.put(bens.getDataCadastro(), bens);
            }
        }
        return  mapaBens;
    }

    /**
     * Método que lista os BENS do cliente ordenados por sua data de criação por meio de um {@code Map} criado para armazenar 
     * objetos do tipo {@code Bens} e ordená-los a partir do seu atributo {@code dataCadastro}.
     * 
     * @param uidCliente se refere ao identificador Ãºnico e exclusivo do cliente.
     * @return Map de Bens ordenados por data.
     */
    public Map<LocalDate,Bens> listarBensCliente(long uidCliente) {
        NavigableMap<LocalDate, Bens> mapaBensCliente = new TreeMap<>();
        List<Bens> benClienteList = repoBENS.listar();

        for(Bens ben : benClienteList){
            if(ben.getCliente().getUid() == uidCliente) {
                mapaBensCliente.put(ben.getDataCadastro(), ben);
            }

        }
            return mapaBensCliente;
    }

    /**
     * Método que lista os BENS pendentes do cliente ordenados por sua data de criação por meio de um {@code Map} criado para armazenar 
     * objetos do tipo {@code Bens} que tem seu atributo {@code pendente} true e ordená-los a partir do seu atributo {@code dataCadastro}.
     * 
     * @param uidCliente se refere ao identificador único e exclusivo do cliente.
     * @throws PessoaInexistenteException se o cliente não for encontrado.
     * @return Map de Bens ordenados por data.
     */
    public  Map<LocalDate,Bens> listarBensPendentes(long uidCliente) throws PessoaInexistenteException{

        NavigableMap<LocalDate, Bens> mapaBensPendentes = new TreeMap<>();
        boolean pendente = false;
        List<Bens> pendenteList = repoBENS.listar();

        for(Bens ben : pendenteList){
            if(ben.getCliente().getUid() == uidCliente && ben.isPendente()) {

                pendente = true;

                mapaBensPendentes.put(ben.getDataCadastro(), ben);
            }
        }
        if(!pendente)  throw new PessoaInexistenteException("Cliente NÃ£o existe!");

        return mapaBensPendentes;
    }

    /**
     * Método que lista todos os BENS aprovados ordenados por sua data de criação por meio de um {@code Map} criado para armazenar 
     * objetos do tipo {@code Bens} que tem seu atributo {@code pendente} false e ordená-los a partir do seu atributo {@code dataCadastro}.
     * 
     * @return Map de Bens ordenados por data.
     */
    public  Map<LocalDate,Bens> listarBensAprovados() {
        NavigableMap<LocalDate, Bens> mapaBensaprovados = new TreeMap<>();
        List<Bens> aproveList = repoBENS.listar();

        for(Bens ben : aproveList){
            if(!ben.isPendente()) mapaBensaprovados.put(ben.getDataCadastro(), ben);   
        }
        
        return mapaBensaprovados;
    }

    /**
     * Método que lista todos os BENS que são garantia ordenados por sua data de criação por meio de um {@code Map} criado para armazenar 
     * objetos do tipo {@code Bens} que tem seu atributo {@code garantia} true e ordená-los a partir do seu atributo {@code dataCadastro}.
     * 
     * @return Map de Bens ordenados por data.
     */
    public Map<LocalDate,Bens> listarBensGarantia() {
        NavigableMap<LocalDate, Bens> mapaBensGarantia = new TreeMap<>();
        List<Bens> garantiaList = repoBENS.listar();

            for(Bens ben: garantiaList){
                if(ben.isGarantia()) mapaBensGarantia.put(ben.getDataCadastro(), ben);
            }

        return mapaBensGarantia;
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
                    throw new BensInexistenteException("O bem nÃ£o existe!");
                }
                bensExiste = true;
            }
        }
    }

    /**
     * Método que remove bens do repositório.
     * @param bens bem a ser removido do repositório.
     * @throws BensInexistenteException caso o bem a ser substituído nÃ£o exista no repositório.
     */
    public void removerBens(Bens bens) throws BensInexistenteException{
        try {
            this.repoBENS.remover(bens);
        } catch (ObjetoInexistenteException e) {
            throw new BensInexistenteException("O bem nÃ£o existe!");
        }
    }
}
