package gerenciamento;

import negocio.beans.Pessoa;

public class SessaoUsuario {
    private static SessaoUsuario instance;
    private Pessoa pessoaSessao;
    
    private SessaoUsuario() {

    }
    
    public static SessaoUsuario getInstance() {
        if (instance == null) {
            instance = new SessaoUsuario();
        }
        return instance;
    }

    public Pessoa getPessoaSessao() {
        return pessoaSessao;
    }

    public void setPessoaSessao(Pessoa pessoaSessao) {
        this.pessoaSessao = pessoaSessao;
    }
}
