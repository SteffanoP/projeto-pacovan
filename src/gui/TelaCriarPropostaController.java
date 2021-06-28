package gui;

import exceptions.PessoaInexistenteException;
import exceptions.PropostaInvalidaException;
import gerenciamento.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import negocio.Fachada;
import negocio.beans.Bens;
import negocio.beans.Cliente;
import negocio.beans.Proposta;

import java.time.LocalDate;
import java.time.Period;

public class TelaCriarPropostaController {
    @FXML TextField txtCliente;
    @FXML TextArea txtMotivo;
    @FXML TextField txtValor;
    @FXML TextField txtParcelas;
    @FXML DatePicker dtPrazo;

    @FXML SplitMenuButton splMenuGarantia;

    @FXML ListView<String> lstvGarantias;

    boolean initialized = false;

    public void onbtnCriarPropostaPressed() {
        if (!this.isTextFieldsBlank()) {
            Proposta proposta = new Proposta();
            try {
                proposta.setCliente((Cliente) SessionManager.getInstance().getPessoaSessao());
                proposta.setMotivo(txtMotivo.getText());
                proposta.setValorDesejado(Double.parseDouble(txtValor.getText()));
                proposta.setParcelasDesejadas(Double.parseDouble(txtParcelas.getText()));
                proposta.setPrazo(Period.between(LocalDate.now(),dtPrazo.getValue()).getDays()); //TODO: Verificar!!!
                //TODO: Setar as garantias de empréstimo
                Fachada.getInstance().criarProposta(proposta);
                //TODO: Transição para tela principal do cliente
            } catch (NumberFormatException e) {
                this.gerarAlertaErroCadastro("Há campos com dados inválidos!");
            } catch (PropostaInvalidaException e) {
                this.gerarAlertaErroCadastro(e.getMessage());
            }
        } else {
            this.gerarAlertaErroCadastro("Verifique se você preencheu todos os campos.");
        }
    }

    private boolean isTextFieldsBlank() {
        return txtMotivo.getText().isBlank() || txtValor.getText().isBlank() || txtParcelas.getText().isBlank() ||
                dtPrazo.getEditor().getText().isBlank(); //TODO: Garantias
    }

    private void gerarAlertaErroCadastro(String justificativa) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro de cadastro");
        alerta.setHeaderText("Parece que tivemos um erro com seu cadastro");
        alerta.setContentText(justificativa);
        alerta.showAndWait();
    }

    private void atualizarListaBens() throws PessoaInexistenteException {
        splMenuGarantia.getItems().removeAll();
        for (Bens bens :
                Fachada.getInstance().listarBensAprovados(SessionManager.getInstance().getPessoaSessao().getUid()).values()) {
            MenuItem item = new MenuItem(bens.getNome());
            splMenuGarantia.getItems().add(item);
        }
    }

    private void initialize() {
        txtCliente.setText(SessionManager.getInstance().getPessoaSessao().getNome());
        try {
            this.atualizarListaBens();
        } catch (PessoaInexistenteException e) {
            e.printStackTrace();
        }
    }

    public void onMouseEntered() {
        if (!initialized) {
            this.initialize();
            initialized = true;
        }
    }
}
