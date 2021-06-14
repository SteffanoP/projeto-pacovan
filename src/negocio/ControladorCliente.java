package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import negocio.beans.Cliente;

import java.util.List;

public class ControladorCliente {
    private Repositorio<Cliente> repoCliente;

    public ControladorCliente() {
        this.repoCliente = new RepositorioCRUD<>();
    }

    public void cadastrarCliente(Cliente cliente) {

    }

    public boolean autenticarCliente(Cliente cliente) {
        boolean valido = false;
        //Regra de autenticação do cliente
        return valido;
    }

    public String informacoesPessoais(Cliente cliente) {
        String infoCliente = "";

        return infoCliente;
    }

    public int calcularScore(Cliente cliente) {
        int score = 0;

        return score;
    }

    public void alterarCadastro(Cliente cliente) {

    }


}
