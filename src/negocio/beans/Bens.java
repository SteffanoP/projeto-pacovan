package negocio.beans;

import java.time.LocalDate;

//TODO: Implementação da classe Bens
public class Bens {
    //Variáveis
    private String nome;
    private String descricao;
    private LocalDate dataCadastro;
    private CategoriaBens categoria;
    private double valor;
    private int tempoDeUso;
    private boolean garantia;
    private boolean pendente;

    //Construtores
    public Bens() {

    }

    //Métodos

     //Calcular depreciação do bem, a partir da desvalorização à taxa de 10% por ano de uso. 
    
     public double calcularDepreciacao(int tempoDeUso){
        if(tempoDeUso == 0) 
            return valor;
        else valor -= valor * tempoDeUso * 0.1;
            return valor;
    }

    @Override
    public String toString() {
        return "Bens{" +
                "nome='" + nome + '\'' +
                ", dataCadastro=" + dataCadastro +
                ", tempoDeUso=" + tempoDeUso +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", categoria=" + categoria +
                ", garantia=" + garantia +
                ", pendente=" + pendente +
                '}';
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public int getTempoDeUso() {
        return tempoDeUso;
    }

    public void setTempoDeUso(int tempoDeUso) {
        this.tempoDeUso = tempoDeUso;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public CategoriaBens getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaBens categoria) {
        this.categoria = categoria;
    }

    public boolean isGarantia() {
        return garantia;
    }

    public void setGarantia(boolean garantia) {
        this.garantia = garantia;
    }

    public boolean isPendente() {
        return pendente;
    }

    public void setPendente(boolean pendente) {
        this.pendente = pendente;
    }
}
