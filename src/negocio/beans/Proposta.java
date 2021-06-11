package negocio.beans;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Proposta {
    //Atributos
    private Cliente cliente;
    private LocalDate data;
    private String motivo;
    private double valorDesejado;
    private double parcelasDesejadas; //todo: rever escrita
    private int prazo;
    private List<Bens> garantia;
    private boolean contraproposta;

    //Construtores
    public Proposta() {
    }

    //Métodos

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proposta)) return false;
        Proposta proposta = (Proposta) o;
        return Double.compare(proposta.getValorDesejado(), getValorDesejado()) == 0 && Double.compare(proposta.getParcelasDesejadas(), getParcelasDesejadas()) == 0 && getPrazo() == proposta.getPrazo() && isContraproposta() == proposta.isContraproposta() && Objects.equals(getCliente(), proposta.getCliente()) && Objects.equals(getData(), proposta.getData()) && Objects.equals(getMotivo(), proposta.getMotivo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCliente(), getData(), getMotivo(), getValorDesejado(), getParcelasDesejadas(), getPrazo(), isContraproposta());
    }

    @Override
    public String toString() {
        return "Proposta{" +
                "cliente=" + cliente +
                ", data=" + data +
                ", motivo='" + motivo + '\'' +
                ", valorDesejado=" + valorDesejado +
                ", parcelasDesejadas=" + parcelasDesejadas +
                ", prazo=" + prazo +
                ", garantia=" + garantia +
                ", contraproposta=" + contraproposta +
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

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public double getValorDesejado() {
        return valorDesejado;
    }

    public void setValorDesejado(double valorDesejado) {
        this.valorDesejado = valorDesejado;
    }

    public double getParcelasDesejadas() {
        return parcelasDesejadas;
    }

    public void setParcelasDesejadas(double parcelasDesejadas) {
        this.parcelasDesejadas = parcelasDesejadas;
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

    public boolean isContraproposta() {
        return contraproposta;
    }

    public void setContraproposta(boolean contraproposta) {
        this.contraproposta = contraproposta;
    }
}
