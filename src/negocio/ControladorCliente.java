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

    /**
     * A função de {@code cadastrarCliente} cria e adiciona um objeto do tipo {@code cliente} com senha no formato hash,
     * em especial com o algoritmo SHA-256, dessa forma, armazena apenas o digest da senha com a função hash. Há também
     * a verificação se o cpf é válido, com a validação básica de um número cpf. O objeto criado por essa função é
     * armazenado no objeto {@code repoCliente}, que se trata do repositório/banco de dados de clientes.
     *
     * @param cliente objeto que cliente a ser inserido no repoCliente.
     * @param senhaCliente senha do cliente a ser tratada para um digest SHA-256.
     * @throws ClienteCPFInvalidoException exceção que determinada quando o CPF do cliente é considerado inválido.
     * @throws ClienteDuplicadoException exceção que é determinada quando há um cliente duplicado no repositório
     */
    public void cadastrarCliente(Cliente cliente, String senhaCliente) throws ClienteCPFInvalidoException,
            ClienteDuplicadoException {

        if (cliente == null || senhaCliente == null) return; //TODO: Tratar erros para GUI

        //Verifica se cpf é válido
        if (!cliente.cpfValido()) {
            throw new ClienteCPFInvalidoException(cliente.getCpf());
        }

        //Digest da senha
        StringBuilder senhaHex = new StringBuilder();

        try {
            MessageDigest algoritmoEncrypt = MessageDigest.getInstance("SHA-256");
            byte[] senhaDisgest = algoritmoEncrypt.digest(senhaCliente.getBytes(StandardCharsets.UTF_8));
            for (byte b : senhaDisgest) {
                senhaHex.append(String.format("%02X",0xFF &b));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return;
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
