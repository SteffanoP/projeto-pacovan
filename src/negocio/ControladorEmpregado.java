package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import negocio.beans.Empregado;

public class ControladorEmpregado {
    private Repositorio<Empregado> repoEmpregado;

    public ControladorEmpregado() {
        this.repoEmpregado = new RepositorioCRUD<>();
    }

    public boolean autenticarEmpregado(Empregado empregado) {
        boolean valido = false;

        return valido;
    }
}
