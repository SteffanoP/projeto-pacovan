package negocio.beans;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

//TODO: Implementação da classe Pessoa
public abstract class Pessoa {
    //Variáveis
    private long uid;
    private String nome;
    private String telefone;
    private String cpf;
    private String endereco;
    private String email;
    private LocalDate dataNascimento;
    private String senha;

    //Construtores
    public Pessoa() {

    }

    //Método de checagem de pessoa maior de idade
    public boolean pessoaDeMaior(){
        long anos = ChronoUnit.YEARS.between(this.dataNascimento, LocalDate.now());
        boolean deMaior = false;
        if(anos > 18) deMaior = true;
        return deMaior;
    }

    //Métodos
    @Override
    public String toString() {
        return "Pessoa{" +
                "uid=" + uid +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", cpf='" + cpf + '\'' +
                ", endereco='" + endereco + '\'' +
                ", email='" + email + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", senha='" + senha + '\'' +
                '}';
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
