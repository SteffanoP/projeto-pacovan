package gui;

import gerenciamento.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import negocio.beans.Proposta;

public class TelaFeedbackPropostaController {
    @FXML Label lblNomeProposta;
    @FXML Label lblStatusProposta;

    @FXML DatePicker dtDataProposta;
    @FXML TextArea txtMotivo;
    @FXML TextField txtValor;
    @FXML TextField txtParcelas;
    @FXML DatePicker txtPrazo;
    @FXML ListView<String> lstvGarantia;
    @FXML Button btnAceitarContraproposta;

    @FXML
    private void initialize() {
        this.initializeHeader();
        this.initializeDetalhesProposta(SessionManager.getInstance().getPropostaSessao());
    }

    @FXML
    public void btnAceitarContraproposta() {
        if (SessionManager.getInstance().getPropostaSessao().isContraproposta()) {
            //TODO: Alterar Proposta para modo aprovado!
        }
    }

    private void initializeHeader() {
        lblNomeProposta.setText(SessionManager.getInstance().getPropostaSessao().getCliente().getNome());
        if (!SessionManager.getInstance().getPropostaSessao().isContraproposta()) {
            lblStatusProposta.setText("está em análise.");
        } else {
            lblStatusProposta.setText("foi analisada e há uma contraproposta para você!");
        }
    }

    private void initializeDetalhesProposta(Proposta proposta) {
        dtDataProposta.getEditor().setText(proposta.getDataToString());
        txtMotivo.setText(proposta.getMotivo());
        txtValor.setText(String.valueOf(proposta.getValorDesejado()));
        txtValor.editableProperty().set(false);
        txtParcelas.setText(String.valueOf(proposta.getParcelasDesejadas()));
        txtParcelas.editableProperty().set(false);
        dtDataProposta.getEditor().setText(proposta.getDataToString());
        try {
            proposta.getGarantia().forEach(bens -> lstvGarantia.getItems().add(bens.getNome()));
        } catch (NullPointerException e) {
            lstvGarantia.getItems().removeAll();
        }

        if (proposta.isContraproposta())
            btnAceitarContraproposta.disableProperty().set(false);
    }
}
