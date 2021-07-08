package negocio.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Movimentacao {
    //Variáveis
    private String descricao;
    private LocalDateTime instante;
    private Cliente cliente;
    private TipoMovimentacao tipoMovimentacao;
    private double valor;

    //Construtores
    public Movimentacao() {
    }

    //Métodos

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movimentacao)) return false;
        Movimentacao that = (Movimentacao) o;
        return Double.compare(that.getValor(), getValor()) == 0 && Objects.equals(getDescricao(), that.getDescricao()) && Objects.equals(getInstante(), that.getInstante()) && Objects.equals(getCliente(), that.getCliente()) && getTipoMovimentacao() == that.getTipoMovimentacao();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescricao(), getInstante(), getCliente(), getTipoMovimentacao(), getValor());
    }

    @Override
    public String toString() {
        return "Movimentacao{" +
                "descricao='" + descricao + '\'' +
                ", instante=" + instante +
                ", cliente=" + cliente +
                ", tipoMovimentacao=" + tipoMovimentacao +
                ", valor=" + valor +
                '}';
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getInstante() {
        return instante;
    }

    public String getInstanteToString() {
        return this.instante.format(DateTimeFormatter.ofPattern("HH/mm dd/MM/yyyy"));
    }

    public void setInstante(LocalDateTime instante) {
        this.instante = instante;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoMovimentacao getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
