package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import exceptions.ObjetoDuplicadoException;
import negocio.beans.Cliente;
import negocio.beans.Emprestimo;
import negocio.beans.Proposta;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

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

    public Map<LocalDate, Emprestimo> listarEmprestimosCliente(long uidCliente) {
        NavigableMap<LocalDate, Emprestimo> mapaEmprestimos = new TreeMap<>();
        List<Emprestimo> repositorio = this.repoEmprestimo.listar();

        for (Emprestimo emprestimo : repositorio) {
            if(emprestimo.getCliente().getUid() == uidCliente){
                //Preencher mapa
                mapaEmprestimos.put(emprestimo.getData(), emprestimo);
            }
        }
        return mapaEmprestimos;
    }

    public List<Emprestimo> listarComissõesEmprestimo() {
        List<Emprestimo> comissoesEmprestimo = new ArrayList<>();

        return comissoesEmprestimo;
    }

    public Map<LocalDate, Cliente> listarDevedores() {
        NavigableMap<LocalDate, Cliente> mapaClientes = new TreeMap<>();
        List<Emprestimo> repositorio = this.repoEmprestimo.listar();

        for (Emprestimo emprestimo : repositorio) {
            LocalDate prazo = emprestimo.getData().plusDays(emprestimo.getPrazo());
            long dataPagamento = ChronoUnit.DAYS.between(emprestimo.getDataPagamento(), prazo);
            if(dataPagamento < 0){
                //Preencher mapa
                mapaClientes.put(prazo, emprestimo.getCliente());
            }
        }
        return mapaClientes;
    }

    public Map<Emprestimo, Cliente> listarDevedoresProtegidos() {
        NavigableMap<Emprestimo, Cliente> mapaClientes = new TreeMap<>();
        List<Emprestimo> repositorio = this.repoEmprestimo.listar();

        for (Emprestimo emprestimo : repositorio) {
            LocalDate prazo = emprestimo.getData().plusDays(emprestimo.getPrazo());
            long dataPagamento = ChronoUnit.DAYS.between(emprestimo.getDataPagamento(), prazo);
            if(dataPagamento < 0 /* score > ? */){
                //Preencher mapa
                mapaClientes.put(emprestimo, emprestimo.getCliente());
            }
        }
        return mapaClientes;
    }

    public Map<Emprestimo, Cliente> listarDevedoresAltoRisco() {
        NavigableMap<Emprestimo, Cliente> mapaClientes = new TreeMap<>();
        List<Emprestimo> repositorio = this.repoEmprestimo.listar();

        for (Emprestimo emprestimo : repositorio) {
            LocalDate prazo = emprestimo.getData().plusDays(emprestimo.getPrazo());
            long dataPagamento = ChronoUnit.DAYS.between(emprestimo.getDataPagamento(), prazo);
            if(dataPagamento < 0 /* score < ? */){
                //Preencher mapa
                mapaClientes.put(emprestimo, emprestimo.getCliente());
            }
        }
        return mapaClientes;
    }
}
