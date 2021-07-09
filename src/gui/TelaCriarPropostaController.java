package gui;

import exceptions.PessoaInexistenteException;
import exceptions.PropostaInvalidaException;
import gerenciamento.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import negocio.Fachada;
import negocio.beans.Bens;
import negocio.beans.Cliente;
import negocio.beans.Proposta;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TelaCriarPropostaController {
    @FXML TextField txtCliente;
    @FXML TextArea txtMotivo;
    @FXML TextField txtValor;
    @FXML TextField txtParcelas;
    @FXML DatePicker dtPrazo;

    @FXML SplitMenuButton splMenuGarantia;
    @FXML Button btnAplicarGarantia;

    @FXML ListView<String> lstvGarantias;

    private List<Bens> listGarantiasTemp = new ArrayList<>();

    @FXML
    public void btnAplicarGarantiaPressed() {
        String nomeBens = splMenuGarantia.getText();
        if (!nomeBens.equals("Selecione o BENS")) {
            long uidCliente = SessionManager.getInstance().getPessoaSessao().getUid();
            listGarantiasTemp.add(Fachada.getInstance().buscarBensCliente(uidCliente, nomeBens));
            this.atualizarListViewGarantias();
            splMenuGarantia.setText("Selecione o BENS");
            splMenuGarantia.getItems().removeIf(bens -> bens.getText().equals(nomeBens));
        }
    }

    public void onbtnCriarPropostaPressed() {
        if (!this.isTextFieldsBlank()) {
            Proposta proposta = new Proposta();
            try {
                proposta.setCliente((Cliente) SessionManager.getInstance().getPessoaSessao());
                proposta.setMotivo(txtMotivo.getText());
                proposta.setValorDesejado(Double.parseDouble(txtValor.getText()));
                proposta.setParcelasDesejadas(Double.parseDouble(txtParcelas.getText()));
                proposta.setPrazo((int) LocalDate.now().until(dtPrazo.getValue(), ChronoUnit.DAYS));
                proposta.setGarantia(listGarantiasTemp);
                Fachada.getInstance().criarProposta(proposta);
                SessionManager.getInstance().setPropostaSessao(
                        Fachada.getInstance().listarPropostasCliente(proposta.getCliente().getUid()).stream()
                        																			.reduce((primeiro, segundo) -> segundo)
                        																			.orElse(null)
                );
                GerenciadorTelas.getInstance().changeScreen("telaFeedbackProposta");
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
        splMenuGarantia.getItems().clear();
        long uidCliente = SessionManager.getInstance().getPessoaSessao().getUid();
        for (Bens bens :
                Fachada.getInstance().listarBensCliente(uidCliente).stream().filter(bens -> !bens.isGarantia())
                        .collect(Collectors.toList())) {
            MenuItem item = new MenuItem(bens.getNome());
            item.setOnAction(event -> splMenuGarantia.setText(item.getText()));
            splMenuGarantia.getItems().add(item);
        }
    }

    private void atualizarListViewGarantias() {
        lstvGarantias.getItems().clear();
        listGarantiasTemp.forEach(bens -> lstvGarantias.getItems().add(bens.getNome()));
    }
    
    @FXML
    public void btnVoltarPressed(ActionEvent event) {
        GerenciadorTelas.getInstance().changeScreen("telaCliente");
    }

    @FXML
    private void initialize() {
        txtCliente.setText(SessionManager.getInstance().getPessoaSessao().getNome());
        try {
            this.atualizarListaBens();
        } catch (PessoaInexistenteException e) {
            e.printStackTrace();
        }
    }
}
