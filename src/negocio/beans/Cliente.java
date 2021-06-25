package negocio.beans;

import java.util.Objects;

//TODO: Implementar a classe Cliente
//TODO: Trabalhar com equals observando classe abstrata
public class Cliente extends Pessoa implements Cloneable {
    //Atributos
    private int score;

    //Construtores
    public Cliente() {
    }

    public Cliente retornaClone() {
        try {
            return (Cliente) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Métodos
    @Override
    public String toString() {
        return super.toString() +
                "Cliente{" +
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
