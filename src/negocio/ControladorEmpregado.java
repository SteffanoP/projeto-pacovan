package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
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

    public void alterarCadastro(Cliente cliente) {

    }

    public List<Empregado> listarEmpregados() {
        List<Empregado> listaEmpregado = new ArrayList<>();

        return listaEmpregado;
    }
}
