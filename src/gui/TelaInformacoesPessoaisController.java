package gui;

import exceptions.PessoaInexistenteException;
import gerenciamento.SessaoUsuario;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import negocio.Fachada;
import negocio.beans.Empregado;
import negocio.beans.Pessoa;

public class TelaInformacoesPessoaisController {
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
        Pessoa pessoa = SessaoUsuario.getInstance().getPessoaSessao();

        txtNome.setText(pessoa.getNome());
        txtCpf.setText(pessoa.getCpf());
        txtEndereco.setText(pessoa.getEndereco());
        txtTelefone.setText(pessoa.getTelefone());
        txtEmail.setText(pessoa.getEmail());
        dtNascimento.getEditor().setText(SessaoUsuario.getInstance().getPessoaSessao().getDataNascimentoToString());
    }

    @FXML
    public void btnEditarDadosPressed() {
        txtNome.setEditable(true);
        txtSenha.setEditable(true);
        txtEndereco.setEditable(true);
        txtTelefone.setEditable(true);
    }

    @FXML
    public void btnSalvarDadosPressed() {
        if (this.autenticarSenha(txtSenha.getText())) {
            Pessoa pessoa = SessaoUsuario.getInstance().getPessoaSessao();
            pessoa.setNome(txtNome.getText());
            pessoa.setEndereco(txtEndereco.getText());
            pessoa.setTelefone(txtTelefone.getText());
            try {
                Fachada.getInstance().alterarDadosPessoais(pessoa);
                SessaoUsuario.getInstance().setPessoaSessao(pessoa);
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
                Pessoa pessoa = SessaoUsuario.getInstance().getPessoaSessao();
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
    public void onMouseEntered() {
        if (!initialized) {
            this.initialize();
            initialized = true;
        }
    }

    private boolean autenticarSenha(String senha) {
        return Fachada.getInstance().autenticarPessoa(txtEmail.getText(), senha,
                SessaoUsuario.getInstance().getPessoaSessao() instanceof Empregado);
    }

    private void gerarAlertaErroAutenticacao(String justificativa) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro de Autenticação");
        alerta.setHeaderText("Parece que tivemos um erro com sua tentativa de alteração de dados");
        alerta.setContentText(justificativa);
        alerta.showAndWait();
    }
}
