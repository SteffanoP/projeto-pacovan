package negocio.beans;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

//TODO: Implementação da função Empréstimo
public class Emprestimo {
    //Variáveis
    private Cliente cliente;
    private LocalDate data;
    private LocalDate dataPagamento;
    private double valor;
    private double parcelas;
    private int prazo;
    private List<Bens> garantia;
    private Empregado empregado;
    private float confiancaPagamento;
    private long numProtocolo;

    //Construtores
    public Emprestimo() {
    }

    public Emprestimo(Proposta proposta) {
        this.setCliente(proposta.getCliente());
        this.setData(proposta.getData());
        this.setValor(proposta.getValorDesejado());
        this.setParcelas(proposta.getParcelasDesejadas());
        this.setPrazo(proposta.getPrazo());
        this.setGarantia(proposta.getGarantia());
    }

    //Métodos
    public double valorBens() {
        double valor = 0;
        //TODO: Método de valorBens()
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Emprestimo)) return false;
        Emprestimo that = (Emprestimo) o;
        return Double.compare(that.getValor(), getValor()) == 0 && Double.compare(that.getParcelas(), getParcelas()) == 0 && getPrazo() == that.getPrazo() && Float.compare(that.getConfiancaPagamento(), getConfiancaPagamento()) == 0 && Objects.equals(getCliente(), that.getCliente()) && Objects.equals(getData(), that.getData()) && Objects.equals(getDataPagamento(), that.getDataPagamento()) && Objects.equals(getEmpregado(), that.getEmpregado());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCliente(), getData(), getDataPagamento(), getValor(), getParcelas(), getPrazo(), getEmpregado(), getConfiancaPagamento());
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "cliente=" + cliente +
                ", data=" + data +
                ", dataPagamento=" + dataPagamento +
                ", valor=" + valor +
                ", parcelas=" + parcelas +
                ", prazo=" + prazo +
                ", garantia=" + garantia +
                ", empregado=" + empregado +
                ", confiancaPagamento=" + confiancaPagamento +
                '}';
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getParcelas() {
        return parcelas;
    }

    public void setParcelas(double parcelas) {
        this.parcelas = parcelas;
    }

    public int getPrazo() {
        return prazo;
    }

    public void setPrazo(int prazo) {
        this.prazo = prazo;
    }

    public List<Bens> getGarantia() {
        return garantia;
    }

    public void setGarantia(List<Bens> garantia) {
        this.garantia = garantia;
    }

    public Empregado getEmpregado() {
        return empregado;
    }

    public void setEmpregado(Empregado empregado) {
        this.empregado = empregado;
    }

    public float getConfiancaPagamento() {
        return confiancaPagamento;
    }

    public void setConfiancaPagamento(float confiancaPagamento) {
        this.confiancaPagamento = confiancaPagamento;
    }

    public long getNumProtocolo() {
        return numProtocolo;
    }

    public void setNumProtocolo(long numProtocolo) {
        this.numProtocolo = numProtocolo;
    }
}
