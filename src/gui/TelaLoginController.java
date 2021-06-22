package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import negocio.Fachada;

public class TelaLoginController {

    @FXML TextField txtEmail;
    @FXML TextField txtSenha;
    @FXML SplitMenuButton splMenuPessoa;
    @FXML MenuItem splMenuItemCliente;
    @FXML MenuItem splMenuItemEmpregado;
    @FXML Button btnLoginPressed;

    @FXML
    public void btnLoginPressed(ActionEvent event) {
        System.out.println(Fachada.getInstance().autenticarPessoa(txtEmail.getText(),txtSenha.getText(),
                splMenuPessoa.getText().equals("Empregado")));
    }

    @FXML
    public void btnSplMenuClientePressed(ActionEvent event) {
        this.setSplMenuPessoa(splMenuItemCliente.getText());
    }

    @FXML
    public void btnSplMenuEmpregadoPressed(ActionEvent event) {
        this.setSplMenuPessoa(splMenuItemEmpregado.getText());
    }

    private void setSplMenuPessoa(String menuItemEscolhido) {
        this.splMenuPessoa.setText(menuItemEscolhido);
    }
}
