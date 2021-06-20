package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import negocio.beans.Bens;
import negocio.beans.Cliente;

import java.util.ArrayList;
import java.util.List;

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

    public List<Bens> listarBensEmpresa() {
        List<Bens> bensList = new ArrayList<>();

        return bensList;
    }

    public List<Bens> listarBensCliente(long uidCliente) {
        List<Bens> bensList = new ArrayList<>();

        return bensList;
    }

    public List<Bens> listarBensPendentes(long uidCliente) {
        List<Bens> bensList = new ArrayList<>();

        return bensList;
    }

    public List<Bens> listarBensAprovados(long uidCliente) {
        List<Bens> bensList = new ArrayList<>();

        return bensList;
    }

    public List<Bens> listarBensEmGarantia(long uidCliente) {
        List<Bens> bensList = new ArrayList<>();

        return bensList;
    }

    public double calcularValorBensCliente(long uidCliente) {
        double valor = 0;

        return valor;
    }

    public void alterarBens(Bens bens) {

    }

    public void removerBens(Bens bens) {

    }
}
