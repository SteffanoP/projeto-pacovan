package gui.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import negocio.beans.Bens;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BensModelo {
    private final SimpleStringProperty dataCadastro;
    private final SimpleDoubleProperty valor;
    private final SimpleStringProperty nome;
    private final SimpleStringProperty descricao;
    private final SimpleIntegerProperty tempoDeUso;

    public BensModelo(LocalDate dataCadastro, Double valor, String nome, String descricao, Integer tempoDeUso) {
        this.dataCadastro = new SimpleStringProperty(formatadorData(dataCadastro));
        this.valor = new SimpleDoubleProperty(valor);
        this.nome = new SimpleStringProperty(nome);
        this.descricao = new SimpleStringProperty(descricao);
        this.tempoDeUso = new SimpleIntegerProperty(tempoDeUso);
    }

    private String formatadorData(LocalDate data) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatador);
    }

    public String getDataCadastro() {
        return dataCadastro.get();
    }

    public SimpleStringProperty dataCadastroProperty() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro.set(dataCadastro);
    }

    public double getValor() {
        return valor.get();
    }

    public SimpleDoubleProperty valorProperty() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor.set(valor);
    }

    public String getNome() {
        return nome.get();
    }

    public SimpleStringProperty nomeProperty() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getDescricao() {
        return descricao.get();
    }

    public SimpleStringProperty descricaoProperty() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }

    public int getTempoDeUso() {
        return tempoDeUso.get();
    }

    public SimpleIntegerProperty tempoDeUsoProperty() {
        return tempoDeUso;
    }

    public void setTempoDeUso(int tempoDeUso) {
        this.tempoDeUso.set(tempoDeUso);
    }
}
