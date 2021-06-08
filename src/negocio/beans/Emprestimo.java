package negocio.beans;

import java.time.LocalDate;
import java.util.List;

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

    //Construtores
    public Emprestimo() {
    }

    //Métodos
    public double valorBens() {
        double valor = 0;
        //TODO: Método de valorBens()
        return valor;
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
}
