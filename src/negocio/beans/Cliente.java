package negocio.beans;

import java.util.Objects;

//TODO: Implementar a classe Cliente
//TODO: Trabalhar com equals observando classe abstrata
public class Cliente extends Pessoa {
    //Atributos
    private int score;

    //Construtores
    public Cliente() {
    }

    //Métodos
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return getScore() == cliente.getScore();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getScore());
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "score=" + score +
                '}';
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
