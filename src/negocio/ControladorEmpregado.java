package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import negocio.beans.Cliente;
import negocio.beans.Empregado;

import java.util.ArrayList;
import java.util.List;

public class ControladorEmpregado {
    private Repositorio<Empregado> repoEmpregado;

    public ControladorEmpregado() {
        this.repoEmpregado = new RepositorioCRUD<>();
    }

    public void cadastrarEmpregado() {

    }

    public boolean autenticarEmpregado(Empregado empregado) {
        boolean valido = false;

        return valido;
    }

    public String informacoesPessoais(Empregado empregado) {
        String infoCliente = "";

        return infoCliente;
    }

    public void alterarCadastro(Cliente cliente) {

    }

    
    public List<Empregado> listarEmpregados() {
        List<Empregado> listaEmpregados = new ArrayList<>();
        repoEmpregado.listar().addAll(listaEmpregados);

        return listaEmpregados;
    }
}
