package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import exceptions.ObjetoDuplicadoException;
import exceptions.PessoaCPFInvalidoException;
import exceptions.PessoaDuplicadoException;
import negocio.beans.Cliente;
import negocio.beans.Empregado;
import negocio.beans.Pessoa;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ControladorPessoa {
    private Repositorio<Cliente> repoCliente;
    private Repositorio<Empregado> repoEmpregado;

    public ControladorPessoa() {
        this.repoCliente = new RepositorioCRUD<>();
        this.repoEmpregado = new RepositorioCRUD<>();
    }

}
