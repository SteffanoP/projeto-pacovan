package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import exceptions.*;
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

        //Set de UID do Cliente
        cliente.setUid(repoCliente.listar().size());

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

    /**
     * Método que altera os dados cadastrados de um cliente por meio da substituição do objeto {@code Cliente} antigo
     * por um novo objeto do tipo {@code Cliente}.
     *
     * @param uidCliente se refere ao identificador único e exclusivo do cliente que se vai alterar o cadastro.
     * @param clienteDadosNovo o novo objeto que irá substituir o {@code Cliente} antigo.
     * @throws ClienteInexistenteException poderá acontecer caso o {@code uidCliente} não esteja atribuído a nenhum
     * cliente.
     */
    public void alterarDadosPessoais(long uidCliente, Cliente clienteDadosNovo) throws ClienteInexistenteException {
        //TODO: Revisar regras de negócio e se aplicam neste espaço
        for (Cliente cliente : this.repoCliente.listar()) {
            if (cliente.getUid() == uidCliente) {
                try {
                    this.repoCliente.atualizar(cliente,clienteDadosNovo);
                    return;
                } catch (ObjetoInexistenteException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new ClienteInexistenteException("Cliente não Existe!");
    }

    /**
     * Método que altera o atributo {@code senha} de um objeto do tipo {@code Cliente} por meio da substituição do
     * atributo anterior por um novo atributo de senha.
     *
     * @param uidCliente se refere ao identificador único e exclusivo do cliente que se vai alterar o cadastro.
     * @param novaSenha se refere a nova senha que será cadastrada no repositório que armazena o digest da senha.
     * @throws ClienteInexistenteException poderá acontecer caso o {@code uidCliente} não esteja atribuído a nenhum
     * cliente.
     */
    public void alterarSenhaCliente(long uidCliente, String novaSenha) throws ClienteInexistenteException {
        //Verifica se o cliente existe
        boolean clienteExiste = false;

        for (Cliente c : this.repoCliente.listar()) {
            if (c.getUid() == uidCliente) {
                clienteExiste = true;
                break;
            }
        }

        if (!clienteExiste) {
            throw new ClienteInexistenteException("Cliente não existe!");
        }

        //Digest da nova senha
        StringBuilder senhaHex = new StringBuilder();

        try {
            MessageDigest algoritmoEncrypt = MessageDigest.getInstance("SHA-256");
            byte[] senhaDigest = algoritmoEncrypt.digest(novaSenha.getBytes(StandardCharsets.UTF_8));
            for (byte b : senhaDigest) {
                senhaHex.append(String.format("%02X",0xFF &b));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return;
        }

        //Alteração de novo cliente
        for (Cliente cSenhaAntiga : this.repoCliente.listar()) {
            if (cSenhaAntiga.getUid() == uidCliente) {
                Cliente cSenhaNova = cSenhaAntiga.retornaClone();
                cSenhaNova.setSenha(senhaHex.toString());
                try {
                    this.repoCliente.atualizar(cSenhaAntiga,cSenhaNova);
                } catch (ObjetoInexistenteException e) {
                    //Garante que realmente não existe o cliente
                    throw new ClienteInexistenteException("Cliente não existe!");
                }
            }
        }
    }

    public Repositorio<Cliente> getRepoCliente() {
        return repoCliente;
    }
}
