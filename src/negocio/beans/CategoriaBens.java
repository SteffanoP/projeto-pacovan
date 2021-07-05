package negocio.beans;

public enum CategoriaBens {
    MOVEL_FUNGIVEL("Fungível"),
    MOVEL_INFUNGIVEL("Infungível"),
    IMOVEL("Imóvel");

    private String nome;
    CategoriaBens(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
