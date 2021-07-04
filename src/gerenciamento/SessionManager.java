package gerenciamento;

import negocio.beans.*;

public class SessionManager {
    private static SessionManager instance;
    private Pessoa pessoaSessao;
    private Cliente clienteSessao;
    private Bens bensSessao;
    private Proposta propostaSessao;
    private Emprestimo emprestimoSessao;
    private Movimentacao movimentacaoSessao;
    
    private SessionManager() {

    }
    
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public Pessoa getPessoaSessao() {
        return pessoaSessao;
    }

    public void setPessoaSessao(Pessoa pessoaSessao) {
        this.pessoaSessao = pessoaSessao;
    }

    public Cliente getClienteSessao() {
        return clienteSessao;
    }

    public void setClienteSessao(Cliente clienteSessao) {
        this.clienteSessao = clienteSessao;
    }

    public Bens getBensSessao() {
        return bensSessao;
    }

    public void setBensSessao(Bens bensSessao) {
        this.bensSessao = bensSessao;
    }

    public Proposta getPropostaSessao() {
        return propostaSessao;
    }

    public void setPropostaSessao(Proposta propostaSessao) {
        this.propostaSessao = propostaSessao;
    }

    public Emprestimo getEmprestimoSessao() {
        return emprestimoSessao;
    }

    public void setEmprestimoSessao(Emprestimo emprestimoSessao) {
        this.emprestimoSessao = emprestimoSessao;
    }

    public Movimentacao getMovimentacaoSessao() {
        return movimentacaoSessao;
    }

    public void setMovimentacaoSessao(Movimentacao movimentacaoSessao) {
        this.movimentacaoSessao = movimentacaoSessao;
    }
}
