package negocio.beans;

import java.util.Objects;

//TODO: Implementar a classe Cliente
//TODO: Trabalhar com equals observando classe abstrata
public class Cliente extends Pessoa {
    private int score;

    public Cliente() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return score == cliente.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(score);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "score=" + score +
                '}';
    }
}
