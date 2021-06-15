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
