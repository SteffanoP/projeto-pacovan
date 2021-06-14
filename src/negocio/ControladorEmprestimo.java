package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import negocio.beans.Emprestimo;

import java.util.ArrayList;
import java.util.List;

public class ControladorEmprestimo {
    private Repositorio<Emprestimo> repoEmprestimo;

    public ControladorEmprestimo() {
        this.repoEmprestimo = new RepositorioCRUD<>();
    }

    public String emprestimoEmDetalhe() {
        Emprestimo emprestimo = null;

        return emprestimo.toString();
    }
    public List<Emprestimo> listarEmprestimosCliente(long uidCliente) {
        List<Emprestimo> emprestimosCliente = new ArrayList<>();

        return emprestimosCliente;
    }
}
