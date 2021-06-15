package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import negocio.beans.Cliente;
import negocio.beans.Emprestimo;
import negocio.beans.Proposta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorEmprestimo {
    private Repositorio<Emprestimo> repoEmprestimo;

    public ControladorEmprestimo() {
        this.repoEmprestimo = new RepositorioCRUD<>();
    }

    public void criarEmprestimo(Proposta proposta) {

    }

    public String emprestimoEmDetalhe() {
        Emprestimo emprestimo = null;

        return emprestimo.toString();
    }

    public List<Emprestimo> listarEmprestimosCliente(long uidCliente) {
        List<Emprestimo> emprestimosCliente = new ArrayList<>();

        return emprestimosCliente;
    }

    public List<Emprestimo> listarComissõesEmprestimo() {
        List<Emprestimo> comissoesEmprestimo = new ArrayList<>();

        return comissoesEmprestimo;
    }

    public Map<Cliente, Emprestimo> listarDevedores() {
        Map<Cliente, Emprestimo> listaDevedores = new HashMap<>();

        return listaDevedores;
    }

    public Map<Cliente, Emprestimo> listarDevedoresProtegidos() {
        Map<Cliente, Emprestimo> listaDevedores = new HashMap<>();

        return listaDevedores;
    }

    public Map<Cliente, Emprestimo> listarDevedoresAltoRisco() {
        Map<Cliente, Emprestimo> listaDevedores = new HashMap<>();

        return listaDevedores;
    }
}
