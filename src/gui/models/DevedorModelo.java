package gui.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import negocio.beans.Cliente;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DevedorModelo {
    private SimpleDoubleProperty valorDevido;
    private SimpleStringProperty dataPagamento;
    private SimpleStringProperty nomeCliente;
    private SimpleDoubleProperty parcelas;
    private SimpleFloatProperty confiancaPagamento;
    private SimpleLongProperty numProtocolo;

    public DevedorModelo(Double valorDevido, LocalDate dataPagamento, Cliente cliente, Double parcelas,
                         Float confiancaPagamento) {
        this.valorDevido = new SimpleDoubleProperty(valorDevido);
        this.dataPagamento = new SimpleStringProperty(this.formatadorData(dataPagamento));
        this.nomeCliente = new SimpleStringProperty(cliente.getNome());
        this.parcelas = new SimpleDoubleProperty(parcelas);
        this.confiancaPagamento = new SimpleFloatProperty(confiancaPagamento);
    }

    public DevedorModelo(Double valorDevido, LocalDate dataPagamento, Cliente cliente, Double parcelas,
                         Float confiancaPagamento, Long numProtocolo) {
        this.valorDevido = new SimpleDoubleProperty(valorDevido);
        this.dataPagamento = new SimpleStringProperty(this.formatadorData(dataPagamento));
        this.nomeCliente = new SimpleStringProperty(cliente.getNome());
        this.parcelas = new SimpleDoubleProperty(parcelas);
        this.confiancaPagamento = new SimpleFloatProperty(confiancaPagamento);
        this.numProtocolo = new SimpleLongProperty(numProtocolo);
    }

    private String formatadorData(LocalDate data) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatador);
    }

    public double getValorDevido() {
        return valorDevido.get();
    }

    public SimpleDoubleProperty valorDevidoProperty() {
        return valorDevido;
    }

    public void setValorDevido(double valorDevido) {
        this.valorDevido.set(valorDevido);
    }

    public String getDataPagamento() {
        return dataPagamento.get();
    }

    public SimpleStringProperty dataPagamentoProperty() {
        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento.set(dataPagamento);
    }

    public String getNomeCliente() {
        return nomeCliente.get();
    }

    public SimpleStringProperty nomeClienteProperty() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente.set(nomeCliente);
    }

    public double getParcelas() {
        return parcelas.get();
    }

    public SimpleDoubleProperty parcelasProperty() {
        return parcelas;
    }

    public void setParcelas(double parcelas) {
        this.parcelas.set(parcelas);
    }

    public float getConfiancaPagamento() {
        return confiancaPagamento.get();
    }

    public SimpleFloatProperty confiancaPagamentoProperty() {
        return confiancaPagamento;
    }

    public void setConfiancaPagamento(float confiancaPagamento) {
        this.confiancaPagamento.set(confiancaPagamento);
    }

    public long getNumProtocolo() {
        return numProtocolo.get();
    }

    public SimpleLongProperty numProtocoloProperty() {
        return numProtocolo;
    }

    public void setNumProtocolo(long numProtocolo) {
        this.numProtocolo.set(numProtocolo);
    }
}
