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
}
