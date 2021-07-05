package gui;

import exceptions.PessoaCPFInvalidoException;
import exceptions.PessoaDuplicadoException;
import gui.models.EmpregadoModelo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import negocio.Fachada;
import negocio.beans.Empregado;

public class TelaCadastroEmpregadosController {
    @FXML TextField txtNome;
    @FXML TextField txtCpf;
    @FXML DatePicker dtNascimento;
    @FXML TextField txtEndereco;
    @FXML TextField txtEmail;
    @FXML TextField txtSenha;
    @FXML TextField txtTelefone;
    @FXML TextField txtSalarioBase;

    @FXML TableView<EmpregadoModelo> tblvEmpregadosCadastro;
    @FXML TableColumn<EmpregadoModelo, Long> colEmpregadoUID;
    @FXML TableColumn<EmpregadoModelo, String> colEmpregadoNome;
    @FXML TableColumn<EmpregadoModelo, String> colEmpregadoEmail;
    @FXML TableColumn<EmpregadoModelo, Float> colEmpregadoReputacao;
    @FXML TableColumn<EmpregadoModelo, Float> colEmpregadoSalBase;


    @FXML Button btnCadastrar;
    @FXML Button btnCancelar;

    public void btnCancelarPressed() {
        this.limparPreenchimento();
    }

    public void btnCadastrarPressed() {
        if (!isTextFieldsBlank()) {
            Empregado empregadoCadastro = new Empregado();
            empregadoCadastro.setNome(txtNome.getText());
            empregadoCadastro.setCpf(txtCpf.getText());
            empregadoCadastro.setDataNascimento(dtNascimento.getValue());
            empregadoCadastro.setEndereco(txtEndereco.getText());
            empregadoCadastro.setEmail(txtEmail.getText());
            empregadoCadastro.setSenha(txtSenha.getText());
            empregadoCadastro.setTelefone(txtTelefone.getText());
            try {
                empregadoCadastro.setSalarioBase(Float.parseFloat(txtSalarioBase.getText()));
                Fachada.getInstance().cadastrarPessoa(empregadoCadastro,txtSenha.getText());
                this.atualizarTableViewEmpregado(empregadoCadastro);
                this.limparPreenchimento();
            } catch (NumberFormatException e) {
                this.gerarAlertaErroCadastro("O campo de Salário Base não é válido.");
            } catch (PessoaCPFInvalidoException | PessoaDuplicadoException e) {
                this.gerarAlertaErroCadastro(e.getMessage());
            }
        } else {
            this.gerarAlertaErroCadastro("Verifique se você preencheu todos os campos obrigatórios.");
        }
    }

    private boolean isTextFieldsBlank() {
        return txtNome.getText().isBlank() || txtTelefone.getText().isBlank() || txtEndereco.getText().isBlank() ||
                txtCpf.getText().isBlank() || txtEmail.getText().isBlank() || txtSenha.getText().isBlank() ||
                dtNascimento.getEditor().getText().isBlank();
    }

    private void atualizarTableViewEmpregado(Empregado empregado) {
        EmpregadoModelo empregadoModelo = new EmpregadoModelo(empregado.getUid(), empregado.getNome(),
                empregado.getEmail(), empregado.getReputacao(), empregado.getSalarioBase());
        colEmpregadoUID.setCellValueFactory(new PropertyValueFactory<>("uid"));
        colEmpregadoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmpregadoEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmpregadoReputacao.setCellValueFactory(new PropertyValueFactory<>("reputacao"));
        colEmpregadoSalBase.setCellValueFactory(new PropertyValueFactory<>("salarioBase"));
        tblvEmpregadosCadastro.getItems().add(empregadoModelo);
    }

    private void limparPreenchimento() {
        this.txtNome.setText("");
        this.txtCpf.setText("");
        this.dtNascimento.getEditor().setText("");
        this.txtEmail.setText("");
        this.txtEndereco.setText("");
        this.txtSenha.setText("");
        this.txtTelefone.setText("");
        this.txtSalarioBase.setText("");
    }

    private void gerarAlertaErroCadastro(String justificativa) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro de cadastro");
        alerta.setHeaderText("Parece que tivemos um erro com seu cadastro");
        alerta.setContentText(justificativa);
        alerta.showAndWait();
    }

    public void btnVoltarPressed(ActionEvent event) {
        GerenciadorTelas.getInstance().changeScreen("telaAdmin");
    }

}
