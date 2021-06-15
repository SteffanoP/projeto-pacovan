package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import exceptions.ClienteDuplicadoException;
import exceptions.ClienteCPFInvalidoException;
import exceptions.ObjetoDuplicadoException;
import negocio.beans.Cliente;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ControladorCliente {
    private Repositorio<Cliente> repoCliente;

    public ControladorCliente() {
        this.repoCliente = new RepositorioCRUD<>();
    }

    public void cadastrarCliente(Cliente cliente, String senhaCliente) throws ClienteCPFInvalidoException,
            NoSuchAlgorithmException, ClienteDuplicadoException {

        if (cliente == null || senhaCliente == null) return; //TODO: Tratar erros para GUI

        //Verifica se cpf é válido
        if (!cliente.cpfValido()) {
            throw new ClienteCPFInvalidoException(cliente.getCpf());
        }

        //Digest da senha
        MessageDigest algoritmoEncrypt = MessageDigest.getInstance("SHA-256");
        byte[] senhaDisgest = algoritmoEncrypt.digest(senhaCliente.getBytes(StandardCharsets.UTF_8));

        StringBuilder senhaHex = new StringBuilder();

        for (byte b : senhaDisgest) {
            senhaHex.append(String.format("%02X",0xFF &b));
        }

        cliente.setSenha(senhaHex.toString());

        //Adicionar cliente ao repoCliente
        try {
            this.repoCliente.inserir(cliente);
        } catch (ObjetoDuplicadoException e) {
            throw new ClienteDuplicadoException(e);
        }

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

    public Repositorio<Cliente> getRepoCliente() {
        return repoCliente;
    }
}
