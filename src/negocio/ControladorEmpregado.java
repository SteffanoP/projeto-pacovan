package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import exceptions.*;
import negocio.beans.Cliente;
import negocio.beans.Empregado;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class ControladorEmpregado {
    private Repositorio<Empregado> repoEmpregado;

    public ControladorEmpregado() {
        this.repoEmpregado = new RepositorioCRUD<>();
    }

     /**
     * A função de {@code cadastrarEmpregado} cria e adiciona um objeto do tipo {@code empregado} com senha no formato hash,
     * em especial com o algoritmo SHA-256, dessa forma, armazena apenas o digest da senha com a função hash. Há também
     * a verificação se o cpf é válido, com a validação básica de um número cpf. O objeto criado por essa função é
     * armazenado no objeto {@code repoEmpregado}, que se trata do repositório/banco de dados de empregados.
     *
     * @param empregado objeto que empregado a ser inserido no repoEmpregado.
     * @param senhaEmpregado senha do empregado a ser tratada para um digest SHA-256.
     * @throws EmpregadoCPFInvalidoException exceção que determinada quando o CPF do empregado é considerado inválido.
     * @throws EmpregadoDuplicadoException exceção que é determinada quando há um empregado duplicado no repositório
     */
    public void cadastrarEmpregado(Empregado empregado, String senhaEmpregado) throws EmpregadoCPFInvalidoException,
            EmpregadoDuplicadoException {

        if (empregado == null || senhaEmpregado == null) return; //TODO: Tratar erros para GUI

        //Verifica se cpf é válido
        if (!empregado.cpfValido()) {
            throw new EmpregadoCPFInvalidoException(empregado.getCpf());
        }

        //Digest da senha
        StringBuilder senhaHex = new StringBuilder();

        try {
            MessageDigest algoritmoEncrypt = MessageDigest.getInstance("SHA-256");
            byte[] senhaDisgest = algoritmoEncrypt.digest(senhaEmpregado.getBytes(StandardCharsets.UTF_8));
            for (byte b : senhaDisgest) {
                senhaHex.append(String.format("%02X",0xFF &b));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return;
        }

        empregado.setSenha(senhaHex.toString());

        //Set de UID do Empregado
        empregado.setUid(repoEmpregado.listar().size());

        //Adicionar empregado ao repoCliente
        try {
            this.repoEmpregado.inserir(empregado);
        } catch (ObjetoDuplicadoException e) {
            throw new EmpregadoDuplicadoException(e);
        }

    }

    public void cadastrarEmpregado() {

    }

    public boolean autenticarEmpregado(String _email, String _senha) {
        boolean emailValidado = false;
        boolean validado = false;
        String stringSenha = " ";
        String stringSenhaCadastro = " ";

        for(Empregado e : this.repoEmpregado.listar()){
            if(e.getEmail().equals(_email)){
                stringSenhaCadastro = e.getSenha();
                emailValidado = true;
            }
        }

        if(emailValidado){
            //Digest e senha
            StringBuilder senhaHex = new StringBuilder();

            try{
                MessageDigest algoritmoEncrypt = MessageDigest.getInstance("SHA-256");
                byte[] senhaDigest = algoritmoEncrypt.digest(_senha.getBytes(StandardCharsets.UTF_8));
                for(byte b :  senhaDigest){
                    senhaHex.append(String.format("%02X",0xFF &b));
                }
            }catch (NoSuchAlgorithmException e){
                e.printStackTrace();
                return false;
            }

           stringSenha = senhaHex.toString();

                if (stringSenha.equals(stringSenhaCadastro)){
                    validado = true;
                }
                else {
                    validado = false;
                }

        }

        return validado;
    }

    public String informacoesPessoais(Empregado empregado) {
        String infoCliente = "";

        return infoCliente;
    }

    
    
    public void alterarCadastro(Empregado empregado) {

    }

    /**
     * Método que altera os dados cadastrados de um empregado por meio da substituição do objeto {@code Empregado} antigo
     * por um novo objeto do tipo {@code Empregado}.
     *
     * @param uidEmpregado se refere ao identificador único e exclusivo do empregado que se vai alterar o cadastro.
     * @param empregadoDadosNovo o novo objeto que irá substituir o {@code Empregado} antigo.
     * @throws EmpregadoInexistenteException poderá acontecer caso o {@code uidEmpregado} não esteja atribuído a nenhum
     * empregado.
     */
    public void alterarDadosPessoais(long uidEmpregado, Empregado empregadoDadosNovo) throws
            EmpregadoInexistenteException {
        //TODO: Revisar regras de negócio e se aplicam neste espaço
        for (Empregado empregado : this.repoEmpregado.listar()) {
            if (empregado.getUid() == uidEmpregado) {
                try {
                    this.repoEmpregado.atualizar(empregado, empregadoDadosNovo);
                    return;
                } catch (ObjetoInexistenteException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new EmpregadoInexistenteException("Empregado não Existe!");
    }

    public List<Empregado> listarEmpregados() {
        List<Empregado> listaEmpregado = new ArrayList<>();

        return listaEmpregado;
    }
}
