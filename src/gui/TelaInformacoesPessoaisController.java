package gui;

import exceptions.PessoaInexistenteException;
import gerenciamento.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import negocio.Fachada;
import negocio.beans.Cliente;
import negocio.beans.Empregado;
import negocio.beans.Pessoa;

public class TelaInformacoesPessoaisController {
    @FXML Button btnVoltarTela;

    //Elementos Parte de Dados
    @FXML TextField txtNome;
    @FXML TextField txtCpf;
    @FXML TextArea txtEndereco;
    @FXML TextField txtTelefone;
    @FXML TextField txtEmail;
    @FXML DatePicker dtNascimento;
    @FXML PasswordField txtSenha;

    @FXML Button btnEditarDados;
    @FXML Button btnSalvarDados;

    //Elemento Parte de Alteração de Senha
    @FXML PasswordField txtSenhaAtual;
    @FXML PasswordField txtNovaSenha;
    @FXML PasswordField txtConfNovaSenha;

    @FXML Button btnSalvarSenha;

    boolean initialized = false;

    private void initialize() {
        Pessoa pessoa = SessionManager.getInstance().getPessoaSessao();

        txtNome.setText(pessoa.getNome());
        txtCpf.setText(pessoa.getCpf());
        txtEndereco.setText(pessoa.getEndereco());
        txtTelefone.setText(pessoa.getTelefone());
        txtEmail.setText(pessoa.getEmail());
        dtNascimento.getEditor().setText(SessionManager.getInstance().getPessoaSessao().getDataNascimentoToString());
    }

    @FXML
    public void btnEditarDadosPressed() {
        this.ativarEdicaoDados(true);
    }

    @FXML
    public void btnSalvarDadosPressed() {
        if (this.autenticarSenha(txtSenha.getText())) {
            Pessoa pessoa = SessionManager.getInstance().getPessoaSessao();
            pessoa.setNome(txtNome.getText());
            pessoa.setEndereco(txtEndereco.getText());
            pessoa.setTelefone(txtTelefone.getText());
            try {
                Fachada.getInstance().alterarDadosPessoais(pessoa);
                SessionManager.getInstance().setPessoaSessao(pessoa);
                this.ativarEdicaoDados(false);
                this.txtSenha.setText("");
                //TODO: Mensagem de alteração com sucesso
            } catch (PessoaInexistenteException e) {
                e.printStackTrace();
            }
        } else {
            this.gerarAlertaErroAutenticacao("O seu email e senha não conferem!");
        }
    }

    @FXML
    public void btnSalvarSenhaPressed() {
        if (this.autenticarSenha(txtSenhaAtual.getText())) {
            if (txtNovaSenha.getText().equals(txtConfNovaSenha.getText())) {
                Pessoa pessoa = SessionManager.getInstance().getPessoaSessao();
                try {
                    Fachada.getInstance().alterarSenha(pessoa,txtNovaSenha.getText());
                    this.txtNovaSenha.setText("");
                    this.txtConfNovaSenha.setText("");
                    //TODO: Mensagem de confirmação de nova senha
                } catch (PessoaInexistenteException e) {
                    e.printStackTrace();
                }
            } else {
                this.gerarAlertaErroAutenticacao("As senhas não batem!");
            }
        } else {
            this.gerarAlertaErroAutenticacao("A sua senha atual não confere!");
        }
        this.txtSenhaAtual.setText("");
    }

    @FXML
    public void btnVoltarTelaPressed() {
        if (SessionManager.getInstance().getPessoaSessao() instanceof Cliente)
            GerenciadorTelas.getInstance().changeScreen("telaCliente");
        else if (SessionManager.getInstance().getPessoaSessao() instanceof Empregado)
            GerenciadorTelas.getInstance().changeScreen("telaEmpregado");
    }

    @FXML
    public void onMouseEntered() {
        if (!initialized) {
            this.initialize();
            initialized = true;
        }
    }

    private void ativarEdicaoDados(boolean edicao) {
        txtNome.setEditable(edicao);
        txtNome.setDisable(!edicao);
        txtSenha.setEditable(edicao);
        txtSenha.setDisable(!edicao);
        txtEndereco.setEditable(edicao);
        txtEndereco.setDisable(!edicao);
        txtTelefone.setEditable(edicao);
        txtTelefone.setDisable(!edicao);
    }

    private boolean autenticarSenha(String senha) {
        return Fachada.getInstance().autenticarPessoa(txtEmail.getText(), senha,
                SessionManager.getInstance().getPessoaSessao() instanceof Empregado);
    }

    private void gerarAlertaErroAutenticacao(String justificativa) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro de Autenticação");
        alerta.setHeaderText("Parece que tivemos um erro com sua tentativa de alteração de dados");
        alerta.setContentText(justificativa);
        alerta.showAndWait();
    }
}
