package negocio.beans;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private long numProtocolo;
    private boolean aprovado;

    //Construtores
    public Proposta() {
    }

    //Métodos

    /**
     * Método que calcula o valor total dos BENS como garantia em uma proposta.
     *
     * @return um valor que se refere ao valor total de garantias.
     */
    public double valorTotalBENS() {
        return garantia.stream().mapToDouble(Bens::getValor).sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proposta)) return false;
        Proposta proposta = (Proposta) o;
        return getNumProtocolo() == proposta.getNumProtocolo() && Objects.equals(getCliente(), proposta.getCliente()) && Objects.equals(getData(), proposta.getData()) && Objects.equals(getMotivo(), proposta.getMotivo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCliente(), getData(), getMotivo(), getNumProtocolo());
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

    public String getDataToString() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.data.format(formatador);
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

    public String getPrazoData() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.data.plusDays(prazo).format(formatador);
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

    public long getNumProtocolo() {
        return numProtocolo;
    }

    public void setNumProtocolo(long numProtocolo) {
        this.numProtocolo = numProtocolo;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }
}
