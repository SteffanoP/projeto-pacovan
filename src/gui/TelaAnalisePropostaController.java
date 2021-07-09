package gui;

import exceptions.BensInexistenteException;
import exceptions.EmprestimoDuplicadoException;
import exceptions.PropostaInvalidaException;
import gerenciamento.SessionManager;
import gui.models.BensModelo;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import negocio.Fachada;
import negocio.beans.Bens;
import negocio.beans.Empregado;
import negocio.beans.Proposta;

import java.util.List;

public class TelaAnalisePropostaController {
    @FXML TextField txtNomeCliente;
    @FXML DatePicker dtData;
    @FXML TextArea txtMotivo;
    @FXML TextField txtValor;
    @FXML TextField txtParcelas;
    @FXML TextField txtPrazo;
    @FXML Button btnEditarContraproposta;
    @FXML Button btnCriarContraproposta;

    @FXML TableView<BensModelo> tblvGarantia;
    @FXML TableColumn<BensModelo, Double> colValor;
    @FXML TableColumn<BensModelo, String> colNome;
    @FXML TableColumn<BensModelo, String> colDescricao;
    @FXML TableColumn<BensModelo, Integer> colTempoUso;
    @FXML TableColumn<BensModelo, String> colDataCadastro;

    @FXML Button btnVoltarTela;
    @FXML Button btnCriarEmprestimo;

    @FXML
    private void initialize() {
        this.initializeTableViews();
        Proposta propostaSessao = SessionManager.getInstance().getPropostaSessao();
        this.initializeDetalhesDaProposta(propostaSessao);
        this.atualizaTableViewBens(tblvGarantia, SessionManager.getInstance().getPropostaSessao().getGarantia());
    }

    @FXML
    public void btnEditarContrapropostaPressed() {
        this.btnEditarContraproposta.disableProperty().set(true);
        this.btnCriarContraproposta.disableProperty().set(false);
        this.btnCriarEmprestimo.disableProperty().set(true);

        this.txtMotivo.editableProperty().set(true);
        this.txtValor.editableProperty().set(true);
        this.txtParcelas.editableProperty().set(true);
        this.txtPrazo.editableProperty().set(true);
    }

    @FXML
    public void btnCriarContrapropostaPressed() {
        Proposta novaContraproposta = SessionManager.getInstance().getPropostaSessao();
        try {
            novaContraproposta.setMotivo(txtMotivo.getText());
            novaContraproposta.setValorDesejado(Double.parseDouble(txtValor.getText()));
            novaContraproposta.setParcelasDesejadas(Double.parseDouble(txtParcelas.getText()));
            novaContraproposta.setPrazo(Integer.parseInt(txtPrazo.getText()));
            Fachada.getInstance().criarContraProposta(novaContraproposta);
            this.btnCriarContraproposta.disableProperty().set(true);
        } catch (NumberFormatException e) {
            this.gerarAlertaErro("Erro de Contraproposta", "Campos númericos inválidos",
                    "Há campos númericos com um formato inválido.");
        } catch (PropostaInvalidaException e) {
            this.gerarAlertaErro("Erro de Contraproposta", "Proposta Inválida", e.getMessage());
        }
    }

    @FXML
    public void btnVoltarTelaPressed() {
        GerenciadorTelas.getInstance().changeScreen("telaEmpregado");
    }

    @FXML
    public void btnCriarEmprestimoPressed() {
        try {
            Fachada.getInstance().criarEmprestimo(SessionManager.getInstance().getPropostaSessao(),
                    (Empregado) SessionManager.getInstance().getPessoaSessao());
        } catch (EmprestimoDuplicadoException e) {
            this.gerarAlertaErro("Erro de Empréstimo","Empréstimo Duplicado", e.getMessage());
        } catch (BensInexistenteException e) {
            this.gerarAlertaErro("Erro de Empréstimo", "Bens de Garantia Inexistente", e.getMessage());
        }
        this.btnCriarEmprestimo.disableProperty().set(true);
    }

    private void initializeTableViews() {
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colTempoUso.setCellValueFactory(new PropertyValueFactory<>("tempoDeUso"));
        colDataCadastro.setCellValueFactory(new PropertyValueFactory<>("dataCadastro"));
    }

    private void initializeDetalhesDaProposta(Proposta propostaSessao) {
        this.txtNomeCliente.setText(propostaSessao.getCliente().getNome());
        this.dtData.getEditor().setText(propostaSessao.getData().toString());
        this.txtMotivo.setText(propostaSessao.getMotivo());
        this.txtValor.setText(String.valueOf(propostaSessao.getValorDesejado()));
        this.txtParcelas.setText(String.valueOf(propostaSessao.getParcelasDesejadas()));
        this.txtPrazo.setText(String.valueOf(propostaSessao.getPrazo()));

        this.txtNomeCliente.editableProperty().set(false);
        this.dtData.getEditor().editableProperty().set(false);
        this.txtMotivo.editableProperty().set(false);
        this.txtValor.editableProperty().set(false);
        this.txtParcelas.editableProperty().set(false);
        this.txtPrazo.editableProperty().set(false);
    }

    private void atualizaTableViewBens(TableView<BensModelo> tableView, List<Bens> bensList) {
        tableView.getItems().clear();
        if (bensList != null) {
            for (Bens bens : bensList) {
                BensModelo bensModelo = new BensModelo(bens.getDataCadastro(), bens.getValor(), bens.getNome(),
                        bens.getDescricao(), bens.getTempoDeUso());
                tableView.getItems().add(bensModelo);
            }
        }
    }

    private void gerarAlertaErro(String titulo, String subtitulo ,String justificativa) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(subtitulo);
        alerta.setContentText(justificativa);
        alerta.showAndWait();
    }
}
