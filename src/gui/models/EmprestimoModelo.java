package gui.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmprestimoModelo {
    private SimpleStringProperty dataPagamento;
    private SimpleDoubleProperty parcelas;
    private SimpleDoubleProperty valor;
    private SimpleStringProperty empregado;

    public EmprestimoModelo(LocalDate dataPagamento, Double parcelas, Double valor, String empregado) {
        this.dataPagamento = new SimpleStringProperty(this.formatadorData(dataPagamento));
        this.parcelas = new SimpleDoubleProperty(parcelas);
        this.valor = new SimpleDoubleProperty(valor);
        this.empregado = new SimpleStringProperty(empregado);
    }

    private String formatadorData(LocalDate data) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatador);
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

    public double getParcelas() {
        return parcelas.get();
    }

    public SimpleDoubleProperty parcelasProperty() {
        return parcelas;
    }

    public void setParcelas(double parcelas) {
        this.parcelas.set(parcelas);
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

    public String getEmpregado() {
        return empregado.get();
    }

    public SimpleStringProperty empregadoProperty() {
        return empregado;
    }

    public void setEmpregado(String empregado) {
        this.empregado.set(empregado);
    }
}
