package gui.models;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmpregadoModelo {
    private SimpleLongProperty uid;
    private final SimpleStringProperty nome;
    private SimpleStringProperty email;
    private final SimpleFloatProperty reputacao;
    private SimpleFloatProperty salarioBase;

    public EmpregadoModelo(String nome, Float reputacao) {
        this.nome = new SimpleStringProperty(nome);
        this.reputacao = new SimpleFloatProperty(reputacao);
    }

    public EmpregadoModelo(Long uid, String nome, String email, Float reputacao, Float salarioBase) {
        this.uid = new SimpleLongProperty(uid);
        this.nome = new SimpleStringProperty(nome);
        this.email = new SimpleStringProperty(email);
        this.reputacao = new SimpleFloatProperty(reputacao);
        this.salarioBase = new SimpleFloatProperty(salarioBase);
    }

    public long getUid() {
        return uid.get();
    }

    public SimpleLongProperty uidProperty() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid.set(uid);
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

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public float getReputacao() {
        return reputacao.get();
    }

    public SimpleFloatProperty reputacaoProperty() {
        return reputacao;
    }

    public void setReputacao(float reputacao) {
        this.reputacao.set(reputacao);
    }

    public float getSalarioBase() {
        return salarioBase.get();
    }

    public SimpleFloatProperty salarioBaseProperty() {
        return salarioBase;
    }

    public void setSalarioBase(float salarioBase) {
        this.salarioBase.set(salarioBase);
    }
}
