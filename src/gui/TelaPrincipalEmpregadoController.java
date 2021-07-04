package gui;

import exceptions.PessoaInexistenteException;
import exceptions.PropostaInvalidaException;
import gerenciamento.SessionManager;
import gui.models.DevedorModelo;
import gui.models.PropostaModelo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import negocio.Fachada;
import negocio.beans.Empregado;
import negocio.beans.Emprestimo;
import negocio.beans.Proposta;

import java.util.ArrayList;
import java.util.List;

public class TelaPrincipalEmpregadoController {
    @FXML Label lblNomeUsuario;
    @FXML Button btnMeusDados;

    @FXML TableView<PropostaModelo> tblvPropostas;
    @FXML TableColumn<PropostaModelo, String> colDataProposta;
    @FXML TableColumn<PropostaModelo, Double> colValorDesejadoProposta;
    @FXML TableColumn<PropostaModelo, Double> colParcelasDesejadasProposta;
    @FXML TableColumn<PropostaModelo, Long> colNumProtocoloProposta;

    @FXML TableView<DevedorModelo> tblvDevedores;
    @FXML TableColumn<DevedorModelo, Double> colValorDevidoDevedores;
    @FXML TableColumn<DevedorModelo, String> colDataPagamentoDevedores;
    @FXML TableColumn<DevedorModelo, String> colNomeClienteDevedores;
    @FXML TableColumn<DevedorModelo, Double> colParcelasDevedores;
    @FXML TableColumn<DevedorModelo, Float> colConfiancaPagamentoDevedores;

    @FXML TableView<DevedorModelo> tblvDProtegidos;
    @FXML TableColumn<DevedorModelo, Double> colValorDProtegidos;
    @FXML TableColumn<DevedorModelo, String> colDataPagamentoDProtegidos;
    @FXML TableColumn<DevedorModelo, String> colNomeClienteDProtegidos;
    @FXML TableColumn<DevedorModelo, Double> colParcelasDProtegidos;
    @FXML TableColumn<DevedorModelo, Float> colConfiancaPagamentoProtegidos;

    @FXML TableView<DevedorModelo> tblvDAltoRisco;
    @FXML TableColumn<DevedorModelo, Double> colValorDevidoDAltoRisco;
    @FXML TableColumn<DevedorModelo, String> colDataPagamentoDAltoRisco;
    @FXML TableColumn<DevedorModelo, String> colNomeClienteDAltoRisco;
    @FXML TableColumn<DevedorModelo, Double> colParcelasDAltoRisco;
    @FXML TableColumn<DevedorModelo, Float> colConfiancaPagamentoAltoRisco;

    @FXML ListView<String> lstvComissoes;

    @FXML Button btnAnaliseProposta;
    @FXML Button btnConfirmarDevedor;

    private boolean initialized = false;

    private void initialize() {
        lblNomeUsuario.setText(SessionManager.getInstance().getPessoaSessao().getNome());

        this.initializeTableViews();

        try {
            List<Proposta> propostas = new ArrayList<>(Fachada.getInstance().listarPropostasPendentes().values());
            for (Proposta proposta : propostas) {
                PropostaModelo propostaModelo = new PropostaModelo(proposta.getNumProtocolo(), proposta.getData(),
                        proposta.getValorDesejado(), proposta.getValorDesejado());
                tblvPropostas.getItems().add(propostaModelo);
            }
        } catch (PessoaInexistenteException e) { //TODO: REMOVER EXCEÇÃO SEM SENTIDO!!!
            e.printStackTrace();
        }

        List<Emprestimo> eDevedores = new ArrayList<>(Fachada.getInstance().listarDevedores().values());
        this.atualizaTableViewsEmprestimo(tblvDevedores, eDevedores);
        List<Emprestimo> eDProtegidos = new ArrayList<>(Fachada.getInstance().listarDevedoresProtegidos().values());
        this.atualizaTableViewsEmprestimo(tblvDProtegidos, eDProtegidos);
        List<Emprestimo> eDAltoRisco = new ArrayList<>(Fachada.getInstance().listarDevedoresAltoRisco().values());
        this.atualizaTableViewsEmprestimo(tblvDAltoRisco, eDAltoRisco);

        Empregado empregado = (Empregado) SessionManager.getInstance().getPessoaSessao();
        this.atualizaListView(lstvComissoes, Fachada.getInstance().listarComissoesEmprestimo(empregado));
    }

    private void atualizaTableViewsEmprestimo(TableView<DevedorModelo> tableView, List<Emprestimo> emprestimoList) {
        for (Emprestimo emprestimo : emprestimoList) {
            DevedorModelo devedorModelo = new DevedorModelo(emprestimo.getValor(), emprestimo.getDataPagamento(),
                    emprestimo.getCliente(),emprestimo.getParcelas(),emprestimo.getConfiancaPagamento());
            tableView.getItems().add(devedorModelo);
        }
    }

    private void atualizaListView(ListView<String> listView, List<Emprestimo> emprestimoList) {
        listView.getItems().removeAll();
        for (Emprestimo emprestimo : emprestimoList) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(emprestimo.getEmpregado()).append("; ");
            stringBuilder.append("R$ ").append(emprestimo.getValor()).append("; ");
            stringBuilder.append("Confiança: ").append(emprestimo.getConfiancaPagamento()).append("%;");

            listView.getItems().add(stringBuilder.toString());
        }
    }

    private void initializeTableViews() {
        //Seta as properties da Tableview Propostas
        colDataProposta.setCellValueFactory(new PropertyValueFactory<>("data"));
        colValorDesejadoProposta.setCellValueFactory(new PropertyValueFactory<>("valorDesejado"));
        colParcelasDesejadasProposta.setCellValueFactory(new PropertyValueFactory<>("parcelasDesejadas"));
        colNumProtocoloProposta.setCellValueFactory(new PropertyValueFactory<>("numProtocolo"));

        //Seta as properties da TableView Devedores
        this.setCellValuesPropertiesDevedores(colValorDevidoDevedores, colDataPagamentoDevedores, colNomeClienteDevedores,
                colParcelasDevedores,colConfiancaPagamentoDevedores);

        //Seta as properties da TableView Devedores Protegidos
        this.setCellValuesPropertiesDevedores(colValorDProtegidos, colDataPagamentoDProtegidos, colNomeClienteDProtegidos,
                colParcelasDProtegidos, colConfiancaPagamentoProtegidos);

        //Seta as properties da Tableview Devedores Alto Risco
        this.setCellValuesPropertiesDevedores(colValorDevidoDAltoRisco, colDataPagamentoDAltoRisco, colNomeClienteDAltoRisco,
                colParcelasDAltoRisco, colConfiancaPagamentoAltoRisco);
    }

    private void setCellValuesPropertiesDevedores(TableColumn<DevedorModelo, Double> colValorDevido, TableColumn<DevedorModelo, String> colDataPagamento, TableColumn<DevedorModelo, String> colNomeCliente, TableColumn<DevedorModelo, Double> colParcelas, TableColumn<DevedorModelo, Float> colConfiancaPagamento) {
        colValorDevido.setCellValueFactory(new PropertyValueFactory<>("valorDevido"));
        colDataPagamento.setCellValueFactory(new PropertyValueFactory<>("dataPagamento"));
        colNomeCliente.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        colParcelas.setCellValueFactory(new PropertyValueFactory<>("parcelas"));
        colConfiancaPagamento.setCellValueFactory(new PropertyValueFactory<>("confiancaPagamento"));
    }

    private void gerarAlertaErro(String titulo, String subtitulo, String justificativa) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro de " + titulo);
        alerta.setHeaderText("Parece que tivemos um erro com " + subtitulo);
        alerta.setContentText(justificativa);
        alerta.showAndWait();
    }


    @FXML
    public void btnMeusDadosPressed() {
        GerenciadorTelas.getInstance().changeScreen("telaInformacoesPessoais");
    }

    @FXML
    public void tblvPropostasOnMouseClicked() {
        if (tblvPropostas.getSelectionModel().getSelectedItem() != null) {
            long numProtocolo = tblvPropostas.getSelectionModel().getSelectedItem().getNumProtocolo();
            try {
                SessionManager.getInstance().setPropostaSessao(Fachada.getInstance().buscarProposta(numProtocolo));
            } catch (PropostaInvalidaException e) {
                this.gerarAlertaErro("Propostas", "busca de propostas",e.getMessage());
            }
        }
    }

    @FXML
    public void btnAnalisePropostaPressed() {
        if (SessionManager.getInstance().getPropostaSessao() != null) {
            GerenciadorTelas.getInstance().changeScreen("telaAnaliseProposta");
        } else
            this.gerarAlertaErro("Propostas", "sua Proposta", "Parece que você não" +
                    " selecionou sua Proposta");
    }

    @FXML
    public void btnConfirmarDevedorPressed(ActionEvent event) {
        GerenciadorTelas.getInstance().changeScreen("telaDevedorDetalhe");
    }

    @FXML
    public void btnVoltarPressed(ActionEvent event) {
        GerenciadorTelas.getInstance().changeScreen("telaLogin");
    }

    @FXML
    public void onMouseEntered() {
        if (!initialized) {
            this.initialize();
            initialized = true;
        }
    }
}
