package gui;

import exceptions.PessoaCPFInvalidoException;
import exceptions.PessoaDuplicadoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import negocio.Fachada;
import negocio.beans.Cliente;

public class TelaCadastrarClienteController {
    @FXML TabPane tabPane;
    @FXML Tab tabPessoais;
    @FXML Tab tabCadastro;

    @FXML Button btnAtlhCadastro;
    @FXML Button btnAtlhPessoais;

    @FXML TextField txtNome;
    @FXML DatePicker dtNascimento;
    @FXML TextField txtTelefone;
    @FXML TextField txtEndereco;
    @FXML TextField txtCpf;

    @FXML TextField txtEmail;
    @FXML PasswordField txtSenha;
    @FXML PasswordField txtSenhaConf;

    public void btnCadastrarPressed() {
        if (this.isTextFieldsBlank()) {
            this.gerarAlertaErroCadastro("Verifique se você preencheu todos os campos");
        } else if (txtSenha.getText().equals(txtSenhaConf.getText())) {
            Cliente clienteCadastro = new Cliente();
            clienteCadastro.setNome(txtNome.getText());
            clienteCadastro.setDataNascimento(dtNascimento.getValue());
            clienteCadastro.setTelefone(txtTelefone.getText());
            clienteCadastro.setEndereco(txtEndereco.getText());
            clienteCadastro.setCpf(txtCpf.getText());
            clienteCadastro.setEmail(txtEmail.getText());
            try {
                Fachada.getInstance().cadastrarPessoa(clienteCadastro,txtSenha.getText());
                GerenciadorTelas.getInstance().changeScreen("telaLogin");
            } catch (PessoaDuplicadoException | PessoaCPFInvalidoException e) {
                this.gerarAlertaErroCadastro(e.getMessage());
            }
        } else {
            this.gerarAlertaErroCadastro("As senhas de confirmação não batem");
        }
        //TODO: Confirmar com "cadastro realizado com sucesso" e voltar para tela inicial
    }

    private boolean isTextFieldsBlank() {
        return txtNome.getText().isBlank() || txtTelefone.getText().isBlank() || txtEndereco.getText().isBlank() ||
                txtCpf.getText().isBlank() || txtEmail.getText().isBlank() || txtSenha.getText().isBlank() ||
                txtSenhaConf.getText().isBlank() || dtNascimento.getEditor().getText().isBlank();
    }

    private void gerarAlertaErroCadastro(String justificativa) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro de cadastro");
        alerta.setHeaderText("Parece que tivemos um erro com seu cadastro");
        alerta.setContentText(justificativa);
        alerta.showAndWait();
    }

    public void btnAtalhoPessoaisPressed() {
        tabPane.getSelectionModel().selectNext();
    }

    public void btnAtalhoCadastroPressed() {
        tabPane.getSelectionModel().selectPrevious();
    }

    public void btnRetornarPressed(ActionEvent event) {
        GerenciadorTelas.getInstance().changeScreen("telaLogin");
    }
}
