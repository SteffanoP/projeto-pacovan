package io.models;

import negocio.beans.Empregado;
import negocio.beans.Emprestimo;

import java.util.List;

public class RelatorioEmpregado {
    private Empregado empregado;
    private List<Emprestimo> devedores;
    private List<Emprestimo> devedoresProtegidos;
    private List<Emprestimo> devedoresAltoRisco;

    public RelatorioEmpregado(Empregado empregado, List<Emprestimo> devedores, List<Emprestimo> devedoresProtegidos,
                              List<Emprestimo> devedoresAltoRisco) {
        if (empregado != null) {
            this.empregado = empregado;
            this.devedores = devedores;
            this.devedoresProtegidos = devedoresProtegidos;
            this.devedoresAltoRisco = devedoresAltoRisco;
        }
    }

    public Empregado getEmpregado() {
        return empregado;
    }

    public void setEmpregado(Empregado empregado) {
        this.empregado = empregado;
    }

    public List<Emprestimo> getDevedores() {
        return devedores;
    }

    public void setDevedores(List<Emprestimo> devedores) {
        this.devedores = devedores;
    }

    public List<Emprestimo> getDevedoresProtegidos() {
        return devedoresProtegidos;
    }

    public List<Emprestimo> getDevedoresAltoRisco() {
        return devedoresAltoRisco;
    }
}
