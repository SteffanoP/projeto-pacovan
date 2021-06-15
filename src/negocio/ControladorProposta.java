package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import negocio.beans.Cliente;
import negocio.beans.Proposta;

import java.util.ArrayList;
import java.util.List;

public class ControladorProposta {
    private Repositorio<Proposta> repoProposta;

    public ControladorProposta() {
        this.repoProposta = new RepositorioCRUD<>();
    }

    public void criarProposta() {

    }

    public void criarContraProposta(Proposta proposta) {

    }

    public String propostaEmDetalhe() {
        Proposta proposta = null;

        return proposta.toString();
    }

    public List<Proposta> listarPropostasCliente(long uidCliente) {
        List<Proposta> propostasClientes = new ArrayList<>();

        return propostasClientes;
    }

    public List<Proposta> listarContraPropostas(long uidCliente) {
        List<Proposta> clienteContraPropostas = new ArrayList<>();

        return clienteContraPropostas;
    }

    public List<Proposta> listarPropostasPendentes() {
        List<Proposta> propostasPendentes = new ArrayList<>();

        return propostasPendentes;
    }
}
