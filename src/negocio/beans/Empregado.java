package negocio.beans;

import java.util.Objects;

//TODO: Implementação da classe Empregado
public class Empregado extends Pessoa {
    //Variáveis
    private float salarioBase;
    private float reputacao;
    private int privilegio;

    //Construtores
    public Empregado() {
    }

    //Métodos

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Empregado)) return false;
        Empregado empregado = (Empregado) o;
        return Float.compare(empregado.getSalarioBase(), getSalarioBase()) == 0 && Float.compare(empregado.getReputacao(), getReputacao()) == 0 && getPrivilegio() == empregado.getPrivilegio();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSalarioBase(), getReputacao(), getPrivilegio());
    }

    @Override
    public String toString() {
        return "Empregado{" +
                "salarioBase=" + salarioBase +
                ", reputacao=" + reputacao +
                ", privilegio=" + privilegio +
                '}';
    }

    public float getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(float salarioBase) {
        this.salarioBase = salarioBase;
    }

    public float getReputacao() {
        return reputacao;
    }

    public void setReputacao(float reputacao) {
        this.reputacao = reputacao;
    }

    public int getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(int privilegio) {
        this.privilegio = privilegio;
    }
}
