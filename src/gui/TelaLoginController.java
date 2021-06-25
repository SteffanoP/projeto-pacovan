package gui;

import exceptions.PessoaInexistenteException;
import gerenciamento.SessaoUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import negocio.Fachada;
import negocio.beans.Cliente;
import negocio.beans.Empregado;
import negocio.beans.Pessoa;
import gui.GerenciadorTelas;

public class TelaLoginController {

    @FXML TextField txtEmail;
    @FXML TextField txtSenha;
    @FXML SplitMenuButton splMenuPessoa;
    @FXML MenuItem splMenuItemCliente;
    @FXML MenuItem splMenuItemEmpregado;
    @FXML Button btnLoginPressed;

    @FXML
    public void btnLoginPressed(ActionEvent event) {
        if (Fachada.getInstance().autenticarPessoa(txtEmail.getText(),txtSenha.getText(),
                splMenuPessoa.getText().equals("Empregado"))) {
            try {
                Pessoa pessoa = Fachada.getInstance().buscarPessoa(txtEmail.getText());
                SessaoUsuario.getInstance().setPessoaSessao(pessoa);
                if (pessoa instanceof Cliente) {
                	GerenciadorTelas.getInstance().changeScreen("telaCliente");
                }
            } catch (PessoaInexistenteException e) {
                e.printStackTrace();
            }
        } else {
            this.gerarAlertaErroAutenticacao("O usuário e senha não conferem ou você não está cadastrado.");
        }
    }

    @FXML
    public void btnSplMenuClientePressed(ActionEvent event) {
        this.setSplMenuPessoa(splMenuItemCliente.getText());
    }

    @FXML
    public void btnSplMenuEmpregadoPressed(ActionEvent event) {
        this.setSplMenuPessoa(splMenuItemEmpregado.getText());
    }

    private void gerarAlertaErroAutenticacao(String justificativa) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro de Autenticação");
        alerta.setHeaderText("Parece que tivemos um erro com sua tentativa de login");
        alerta.setContentText(justificativa);
        alerta.showAndWait();
    }

    private void setSplMenuPessoa(String menuItemEscolhido) {
        this.splMenuPessoa.setText(menuItemEscolhido);
    }
}
