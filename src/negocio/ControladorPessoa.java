package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import exceptions.*;
import negocio.beans.Cliente;
import negocio.beans.Empregado;
import negocio.beans.Pessoa;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControladorPessoa {
    private Repositorio<Cliente> repoCliente;
    private Repositorio<Empregado> repoEmpregado;
    private static long contadorUID;

    public ControladorPessoa() {
        this.repoCliente = new RepositorioCRUD<>();
        this.repoEmpregado = new RepositorioCRUD<>();
    }
    /**
     A função de {@code cadastrarPessoa} é de criar e adiciona um objeto do tipo {@code Pessoa} com senha no formato
     * hash, em especial com o algoritmo SHA-256, dessa forma, armazena apenas o digest da senha com a função hash. Há
     * também a verificação se o cpf é válido, com a validação básica de um número cpf. O objeto criado por essa função
     * é armazenado no objeto {@code repoCliente} {@code repoEmpregado}, que se trata do repositório/banco de dados de
     * clientes e empregados, de acordo com sua instância.
     *
     * @param pessoa objeto {@code Pessoa} a ser inserido no repoCliente.
     * @param senhaPessoa senha da pessoa a ser tratada para um digest SHA-256.
     * @throws PessoaCPFInvalidoException exceção que determinada quando o CPF da pessoa é considerado inválido.
     * @throws PessoaDuplicadoException exceção que é determinada quando há uma pessoa duplicada no seu respectivo
     * repositório.
     */
    public void cadastrarPessoa(Pessoa pessoa, String senhaPessoa) throws
            PessoaCPFInvalidoException, PessoaDuplicadoException {

        if (pessoa == null || senhaPessoa == null) return;

        if (!pessoa.cpfValido()) {
            throw new PessoaCPFInvalidoException("CPF Inválido!");
        }

        //Set de UID da pessoa
        pessoa.setUid(contadorUID);

        //Set de Senha da Pessoa
        pessoa.setSenha(digestSHA256(senhaPessoa));

        if (pessoa instanceof Cliente) {
            Cliente cliente = (Cliente) pessoa;
            //Set de Score inicial
            cliente.setScore(50);

            try {
                this.repoCliente.inserir(cliente);
                contadorUID++;
            } catch (ObjetoDuplicadoException e) {
                throw new PessoaDuplicadoException("Pessoa já registrada no sistema!");
            }
        } else if (pessoa instanceof Empregado) {
            try {
                this.repoEmpregado.inserir((Empregado) pessoa);
                contadorUID++;
            } catch (ObjetoDuplicadoException e) {
                throw new PessoaDuplicadoException("Pessoa já registrada no sistema!");
            }
        }
    }

    /**
     * A função de {@code autenticarPessoa} faz o processo de autenticação de um usuário do sistema, com base nas
     * informações cadastradas nos repositórios de pessoas, seja do Cliente ou do Empregado.
     *
     * @param email email do usuário cadastrado, se trata do atributo {@code email} do objeto {@code Pessoa}.
     * @param senha senha da pessoa a ser autenticada.
     * @param isEmpregado atributo que especifica se está autenticando um {@code Cliente} ou um {@code Empregado}.
     * @return {@code true} se a pessoa foi autenticada com sucesso e {@code false} caso a pessoa não tenha sido
     * autenticada com sucesso
     */
    public boolean autenticarPessoa(String email, String senha, boolean isEmpregado) {
        boolean emailValidado = false;
        boolean validado = false;
        String senhaDigest;
        String senhaCadastro = "";

        if (email == null || senha == null) return false;

        if (!isEmpregado) {
            List<Cliente> clienteList = this.repoCliente.listar();
            for (int i = 0; (i < clienteList.size()) && (!emailValidado); i++) {
                if (email.equals(clienteList.get(i).getEmail())) {
                    senhaCadastro = clienteList.get(i).getSenha();
                    emailValidado = true;
                }
            }
        } else {
            List<Empregado> empregadoList = this.repoEmpregado.listar();
            for (int i = 0; (i < empregadoList.size()) && (!emailValidado); i++) {
                if (email.equals(empregadoList.get(i).getEmail())) {
                    senhaCadastro = empregadoList.get(i).getSenha();
                    emailValidado = true;
                }
            }
        }

        if (emailValidado) {
            //Digest da senha
            senhaDigest = digestSHA256(senha);

            validado = senhaDigest.equals(senhaCadastro);
        }

        return validado;
    }

    /**
     * Método que faz a busca de uma {@code Pessoa} dentro do repositório de todos os usuários.
     *
     * @param email se trata do parâmetro de busca do usuário
     * @return retorna um objeto abstrato do tipo {@code Pessoa}
     * @throws PessoaInexistenteException poderá acontecer caso o {@code email} não esteja atribuído a nenhuma
     * {@code Pessoa}.
     */
    public Pessoa buscarPessoa(String email) throws PessoaInexistenteException {
        Pessoa pessoa = null;
        List<Pessoa> listPessoas = new ArrayList<>(repoCliente.listar());
        listPessoas.addAll(repoEmpregado.listar());

        boolean pessoaEncontrada = false;
        for (int i = 0; (i < listPessoas.size()) && !pessoaEncontrada; i++) {
            Pessoa p = listPessoas.get(i);
            if (email.equals(p.getEmail())) {
                pessoa = p;
                pessoaEncontrada = true;
            }
        }

        if (!pessoaEncontrada)
            throw new PessoaInexistenteException("Essa pessoa não foi encontrada!");

        return pessoa;
    }

    /**
     * Método que faz a busca de uma {@code Pessoa} dentro do repositório de todos os usuários.
     *
     * @param uidPessoa se trata do parâmetro de busca do usuário
     * @return retorna um objeto abstrato do tipo {@code Pessoa}
     * @throws PessoaInexistenteException poderá acontecer caso o {@code email} não esteja atribuído a nenhuma
     * {@code Pessoa}.
     */
    public Pessoa buscarPessoa(long uidPessoa) throws PessoaInexistenteException {
        Pessoa pessoa = null;
        List<Pessoa> listPessoas = new ArrayList<>(repoCliente.listar());
        listPessoas.addAll(repoEmpregado.listar());

        boolean pessoaEncontrada = false;
        for (int i = 0; (i < listPessoas.size()) && !pessoaEncontrada; i++) {
            Pessoa p = listPessoas.get(i);
            if (uidPessoa == p.getUid()) {
                pessoa = p;
                pessoaEncontrada = true;
            }
        }

        if (!pessoaEncontrada)
            throw new PessoaInexistenteException("Essa pessoa não foi encontrada!");

        return pessoa;
    }

    /**
     * Método que altera os dados cadastrados de uma pessoa por meio da substituição do objeto {@code Pessoa} antigo
     * por um novo objeto do tipo {@code Pessoa}.
     *
     * @param pessoaDadosNovo o novo objeto que irá substituir a {@code Pessoa} antiga.
     * @throws PessoaInexistenteException poderá acontecer caso o {@code uidCliente} não esteja atribuído a nenhum
     * cliente.
     */
    public void alterarDadosPessoais(Pessoa pessoaDadosNovo) throws PessoaInexistenteException {
        if (pessoaDadosNovo instanceof Cliente) {
            List<Cliente> clienteList = this.repoCliente.listar();
            boolean clienteAtualizado = false;
            for (int i = 0; (i < clienteList.size()) && !clienteAtualizado; i++) {
                Cliente cliente = clienteList.get(i);
                if (cliente.getUid() == pessoaDadosNovo.getUid()) {
                    try {
                        this.repoCliente.atualizar(cliente, (Cliente) pessoaDadosNovo);
                        clienteAtualizado = true;
                    } catch (ObjetoInexistenteException e) {
                        throw new PessoaInexistenteException("Cliente não Existe!");
                    }
                }
            }
        } else if (pessoaDadosNovo instanceof Empregado) {
            List<Empregado> empregadoList = this.repoEmpregado.listar();
            boolean empregadoAtualizado = false;
            for (int i = 0; (i < empregadoList.size()) && !empregadoAtualizado; i++) {
                Empregado empregado = empregadoList.get(i);
                if (empregado.getUid() == pessoaDadosNovo.getUid()) {
                    try {
                        this.repoEmpregado.atualizar(empregado, (Empregado) pessoaDadosNovo);
                        empregadoAtualizado = true;
                    } catch (ObjetoInexistenteException e) {
                        throw new PessoaInexistenteException("Empregado não Existe!");
                    }
                }
            }
        } else {
            throw new PessoaInexistenteException("Esta pessoa não existe!");
        }
    }

    /**
     * Método que altera o atributo {@code senha} de um objeto do tipo {@code Cliente} por meio da substituição do
     * atributo anterior por um novo atributo de senha.
     * @param pessoa se refere a pessoa que se vai alterar a senha
     * @param novaSenha se refere a nova senha que será cadastrada no repositório que armazena o digest da senha.
     * @throws PessoaInexistenteException poderá acontecer caso a {@code Pessoa} não esteja atribuída a nenhuma
     * instância Pessoa dos repositórios.
     */
    public void alterarSenha(Pessoa pessoa, String novaSenha) throws PessoaInexistenteException {
        boolean pessoaExiste = false;

        if (pessoa instanceof Cliente) {
            List<Cliente> clienteList = this.repoCliente.listar();
            for (int i = 0; (i < clienteList.size()) && !pessoaExiste; i++) {
                Cliente cSenhaAntigo = clienteList.get(i);
                if (cSenhaAntigo.getUid() == pessoa.getUid()) {
                    Cliente cSenhaNova = cSenhaAntigo.retornaClone();
                    cSenhaNova.setSenha(digestSHA256(novaSenha));
                    try {
                        this.repoCliente.atualizar(cSenhaAntigo,cSenhaNova);
                    } catch (ObjetoInexistenteException e) {
                        throw new PessoaInexistenteException("Cliente não existe!");
                    }
                    pessoaExiste = true;
                }
            }
        } else if (pessoa instanceof Empregado) {
            List<Empregado> empregadoList = this.repoEmpregado.listar();
            for (int i = 0; (i < empregadoList.size()) && !pessoaExiste; i++) {
                Empregado eSenhaAntigo = empregadoList.get(i);
                if (eSenhaAntigo.getUid() == pessoa.getUid()) {
                    Empregado eSenhaNova = eSenhaAntigo.retornaClone();
                    eSenhaNova.setSenha(digestSHA256(novaSenha));
                    try {
                        this.repoEmpregado.atualizar(eSenhaAntigo,eSenhaNova);
                    } catch (ObjetoInexistenteException e) {
                        throw new PessoaInexistenteException("Cliente não existe!");
                    }
                    pessoaExiste = true;
                }
            }
        } else {
            throw new PessoaInexistenteException("Esta pessoa não existe!");
        }
    }


    private static String digestSHA256(String senha) {
        StringBuilder senhaHex = new StringBuilder();

        try {
            MessageDigest algoritmoEncrypt = MessageDigest.getInstance("SHA-256");
            byte[] digest = algoritmoEncrypt.digest(senha.getBytes(StandardCharsets.UTF_8));
            for(byte b :  digest){
                senhaHex.append(String.format("%02X",0xFF &b));
            }
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        return senhaHex.toString();
    }

    /**
     * Método que lista todos os clientes do repositório de clientes {@code repoCliente}.
     * @return uma lista não modificável do repositório de clientes.
     */
    public List<Cliente> listarClientes() {
        return Collections.unmodifiableList(repoCliente.listar());
    }

    /**
     * Método que lista todos os empregados do repositório de empregados {@code repoEmpregado}.
     * @return uma lista não modificável do repositório de empregados.
     */
    public List<Empregado> listarEmpregados() {
        return Collections.unmodifiableList(repoEmpregado.listar());
    }

    /**
     * Método que retorna uma String de dados de uma pessoa.
     *
     * @param pessoa se refere ao usuário que irá ser verificado os dados no repositório de pessoas.
     * @return uma {@code String} de dados dos atributos do usuário.
     * @throws PessoaInexistenteException poderá acontecer caso a {@code Pessoa} não esteja atribuída a nenhuma
     * instância Pessoa dos repositórios.
     */
    public String informacoesPessoais(Pessoa pessoa) throws PessoaInexistenteException {
        boolean pessoaEncontrada = false;
        String infoPessoa = "";
        List<Pessoa> pessoaList = new ArrayList<>();
        pessoaList.addAll(this.listarClientes());
        pessoaList.addAll(this.listarEmpregados());

        for (int i = 0; i < pessoaList.size() && !pessoaEncontrada; i++) {
            Pessoa p = pessoaList.get(i);
            if (p.getUid() == pessoa.getUid()) {
                pessoaEncontrada = true;
                infoPessoa = p.toString();
            }
        }

        if (!pessoaEncontrada) throw new PessoaInexistenteException("Pessoa não encontrada!");

        return infoPessoa;
    }
}
