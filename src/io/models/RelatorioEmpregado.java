package io.models;

import negocio.beans.Empregado;
import negocio.beans.Emprestimo;

import java.util.List;

public class RelatorioEmpregado {
    private Empregado empregado;
    private List<Emprestimo> devedores;

    public RelatorioEmpregado(Empregado empregado, List<Emprestimo> devedores) {
        if (empregado != null) {
            this.empregado = empregado;
            this.devedores = devedores;
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
}
