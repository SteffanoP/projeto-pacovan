package gui;

import exceptions.EmprestimoInexistenteException;
import gerenciamento.SessionManager;
import gui.models.DevedorModelo;
import gui.models.EmprestimoModelo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import negocio.Fachada;
import negocio.beans.Emprestimo;

import java.util.ArrayList;
import java.util.List;

public class TelaDevedorDetalheController {
    @FXML TableView<DevedorModelo> tblvEmprestimos;
    @FXML TableColumn<DevedorModelo, Double> colValorDevido;
    @FXML TableColumn<DevedorModelo, String> colDataPagamento;
    @FXML TableColumn<DevedorModelo, String> colNomeCliente;
    @FXML TableColumn<DevedorModelo, Double> colParcelas;
    @FXML TableColumn<DevedorModelo, Float> colConfiancaPagamento;
    @FXML TableColumn<DevedorModelo, Long> colProtocolo;

    @FXML Button btnVerDetalhesEmprestimo;
    @FXML Button btnVoltar;

    private boolean initialized = false;

    @FXML
    public void onMouseEntered() {
        if (!initialized) {
            this.initialize();
            initialized = true;
        }
    }

    @FXML
    public void tblvEmprestimosOnMouseClicked() {
        if (tblvEmprestimos.getSelectionModel().getSelectedItem() != null) {
            long numProtocolo = tblvEmprestimos.getSelectionModel().getSelectedItem().getNumProtocolo();
            try {
                SessionManager.getInstance().setEmprestimoSessao(Fachada.getInstance().buscarEmprestimo(numProtocolo));
            } catch (EmprestimoInexistenteException e) {
                this.gerarAlertaErro("Empréstimos", "busca dos empréstimos", e.getMessage());
            }
        }
    }

    @FXML
    public void btnVerDetalhesEmprestimoPressed() {
        if (SessionManager.getInstance().getEmprestimoSessao() != null) {
            //TODO: Tela de Empréstimo em detalhe
        } else
            this.gerarAlertaErro("Empréstimos", "o empréstimo", "Parece que você não" +
                    " selecionou um Empréstimo");
    }

    @FXML
    public void btnVoltarPressed(ActionEvent event) {
        GerenciadorTelas.getInstance().changeScreen("telaEmpregado");
    }

    private void initialize() {
        this.initilializeTableViews();
        this.atualizarTableViews();
    }

    private void initilializeTableViews() {
        //Seta as properties da Tableview Empréstimos Cliente
        colValorDevido.setCellValueFactory(new PropertyValueFactory<>("valorDevido"));
        colDataPagamento.setCellValueFactory(new PropertyValueFactory<>("dataPagamento"));
        colNomeCliente.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        colParcelas.setCellValueFactory(new PropertyValueFactory<>("parcelas"));
        colConfiancaPagamento.setCellValueFactory(new PropertyValueFactory<>("confiancaPagamento"));
        colProtocolo.setCellValueFactory(new PropertyValueFactory<>("numProtocolo"));
    }

    private void atualizarTableViews() {
        //Atualiza TableView de Empréstimos do Cliente
        List<Emprestimo> emprestimoList = new ArrayList<>(Fachada.getInstance().listarEmprestimosCliente(
                SessionManager.getInstance().getClienteSessao().getUid()).values());
        for (Emprestimo emprestimo : emprestimoList) {
            DevedorModelo devedorModelo = new DevedorModelo(emprestimo.getValor(),emprestimo.getDataPagamento(),
                    emprestimo.getCliente(), emprestimo.getParcelas(), emprestimo.getConfiancaPagamento(),
                    emprestimo.getNumProtocolo());
            tblvEmprestimos.getItems().add(devedorModelo);
        }
    }

    private void gerarAlertaErro(String titulo, String subtitulo, String justificativa) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro de " + titulo);
        alerta.setHeaderText("Parece que tivemos um erro com " + subtitulo);
        alerta.setContentText(justificativa);
        alerta.showAndWait();
    }
}
