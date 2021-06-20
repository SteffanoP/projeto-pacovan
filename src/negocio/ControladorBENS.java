package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import exceptions.PessoaInexistenteException;
import negocio.beans.Bens;
import negocio.beans.Cliente;
import negocio.beans.Movimentacao;

import java.time.LocalDate;
import java.util.*;

public class ControladorBENS {
    private Repositorio<Bens> repoBENS;

    public ControladorBENS() {
        this.repoBENS = new RepositorioCRUD<>();
    }

    public void inserirBens(Bens bens) {

    /**
     * Método que insere bem no repositório.
     * @param bens bem a ser inserido no repositório. O objeto receberá a data do cadastro e receberá os parametros
     *             falso para o atributo {@code garantia} e verdadeiro para o atributo {@code pendente}.
     * @throws BensDuplicadoException caso o bem cadastrado já tenha sido inserido.
     */
    public void inserirBens(Bens bens) throws BensDuplicadoException{
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

    public Map<LocalDate,Bens> listarBensEmpresa() {
        NavigableMap<LocalDate, Bens> mapaBens = new TreeMap<>();
        List<Bens> benEmpresaList = repoBENS.listar();
            for( Bens ben : benEmpresaList){
               mapaBens.put(ben.getDataCadastro(), ben);
            }
        return mapaBens;
    }

    public Map<LocalDate,Bens> listarBensCliente(long uidCliente) throws PessoaInexistenteException {
        NavigableMap<LocalDate, Bens> mapaBensCliente = new TreeMap<>();
        boolean benClienteExiste = false;
        List<Bens> benClienteList = repoBENS.listar();

        for(Bens ben : benClienteList){
            if(ben.getCliente().getUid() == uidCliente) {

                benClienteExiste = true;
                mapaBensCliente.put(ben.getDataCadastro(), ben);
            }

        }if(!benClienteExiste)  throw new PessoaInexistenteException("Cliente Não existe!");
            return mapaBensCliente;
}


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
        if(!pendente)  throw new PessoaInexistenteException("Cliente Não existe!");

        return mapaBensPendentes;
    }

    public  Map<LocalDate,Bens> listarBensAprovados(long uidCliente) throws PessoaInexistenteException{
        NavigableMap<LocalDate, Bens> mapaBensaprovados = new TreeMap<>();
        boolean aprovado = false;
        List<Bens> aproveList = repoBENS.listar();

        for(Bens ben : aproveList){
            if(ben.getCliente().getUid() == uidCliente && (!ben.isPendente())) {
              aprovado = true;

              mapaBensaprovados.put(ben.getDataCadastro(), ben);
            }
        }if(!aprovado)  throw new PessoaInexistenteException("Cliente Não existe!");

        return mapaBensaprovados;
    }

    public Map<LocalDate,Bens> listarBensGarantia (long uidCliente) throws PessoaInexistenteException{
        NavigableMap<LocalDate, Bens> mapaBensGarantia = new TreeMap<>();
        boolean garantia = false;
        List<Bens> garantiaList = repoBENS.listar();

            for(Bens ben: garantiaList){
                if(ben.getCliente().getUid() == uidCliente && ben.isGarantia()){
                    garantia = true;
                    mapaBensGarantia.put(ben.getDataCadastro(), ben);
                }
            }if(!garantia)  throw new PessoaInexistenteException ("Cliente Não existe!");

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

    public void alterarBens(Bens bens) {
    /**
     * Método que altera um bem por outro no repositório
     * @param bensAntigo bem inicialmente cadastrado.
     * @param bensNovo novo bem a ser inserido para substituir
     * @throws BensInexistenteException caso o bem a ser substituído não exista no repositório.
     */
    public void alterarBens(Bens bensAntigo, Bens bensNovo) throws BensInexistenteException{
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

    public void removerBens(Bens bens) {
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
