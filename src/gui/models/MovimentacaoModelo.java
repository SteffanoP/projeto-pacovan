package gui.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import negocio.beans.TipoMovimentacao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MovimentacaoModelo {
    private SimpleStringProperty instante;
    private SimpleStringProperty tipoMovimentacao;
    private SimpleDoubleProperty valor;

    public MovimentacaoModelo(LocalDateTime instante, TipoMovimentacao tipoMovimentacao, Double valor) {
        this.instante = new SimpleStringProperty(this.formatadorDataHora(instante));
        this.tipoMovimentacao = new SimpleStringProperty(this.formatadorTipoMovimentacao(tipoMovimentacao));
        this.valor = new SimpleDoubleProperty(valor);
    }

    private String formatadorTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
        String strTipoMovimentacao = "";

        switch (tipoMovimentacao) {
            case CREDITO:
                strTipoMovimentacao = "Crédito";
                break;
            case DEBITO:
                strTipoMovimentacao = "Débito";
                break;
            case EMPRESTIMO:
                strTipoMovimentacao = "Empréstimo";
                break;
            case BENS:
                strTipoMovimentacao = "Garantia por Bens";
                break;
        }

        return strTipoMovimentacao;
    }

    private String formatadorDataHora(LocalDateTime data) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return data.format(formatador);
    }

    public String getInstante() {
        return instante.get();
    }

    public SimpleStringProperty instanteProperty() {
        return instante;
    }

    public void setInstante(String instante) {
        this.instante.set(instante);
    }

    public String getTipoMovimentacao() {
        return tipoMovimentacao.get();
    }

    public SimpleStringProperty tipoMovimentacaoProperty() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(String tipoMovimentacao) {
        this.tipoMovimentacao.set(tipoMovimentacao);
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
}
