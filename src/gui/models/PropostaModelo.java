package gui.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PropostaModelo {
    private SimpleLongProperty numProtocolo;
    private SimpleStringProperty data;
    private SimpleDoubleProperty valorDesejado;
    private SimpleDoubleProperty parcelasDesejadas;

    public PropostaModelo(Long numProtocolo, LocalDate data, Double valorDesejado, Double parcelasDesejadas) {
        this.numProtocolo = new SimpleLongProperty(numProtocolo);
        this.data = new SimpleStringProperty(this.formatadorData(data));
        this.valorDesejado = new SimpleDoubleProperty(valorDesejado);
        this.parcelasDesejadas = new SimpleDoubleProperty(parcelasDesejadas);
    }

    private String formatadorData(LocalDate data) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatador);
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

    public String getData() {
        return data.get();
    }

    public SimpleStringProperty dataProperty() {
        return data;
    }

    public void setData(String data) {
        this.data.set(data);
    }

    public double getValorDesejado() {
        return valorDesejado.get();
    }

    public SimpleDoubleProperty valorDesejadoProperty() {
        return valorDesejado;
    }

    public void setValorDesejado(double valorDesejado) {
        this.valorDesejado.set(valorDesejado);
    }

    public double getParcelasDesejadas() {
        return parcelasDesejadas.get();
    }

    public SimpleDoubleProperty parcelasDesejadasProperty() {
        return parcelasDesejadas;
    }

    public void setParcelasDesejadas(double parcelasDesejadas) {
        this.parcelasDesejadas.set(parcelasDesejadas);
    }
}
