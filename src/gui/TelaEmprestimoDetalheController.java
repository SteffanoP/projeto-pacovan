package gui;

import gerenciamento.SessionManager;
import gui.models.MovimentacaoModelo;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import negocio.Fachada;
import negocio.beans.Bens;
import negocio.beans.Cliente;
import negocio.beans.Emprestimo;
import negocio.beans.Movimentacao;

import java.util.ArrayList;
import java.util.List;

public class TelaEmprestimoDetalheController {
    @FXML TextField txtNomeCliente;
    @FXML DatePicker dtData;
    @FXML TextField txtValor;
    @FXML TextField txtParcelas;
    @FXML DatePicker dtPrazo;

    @FXML TableView<MovimentacaoModelo> tblvExtrato;
    @FXML TableColumn<MovimentacaoModelo, Double> colParcelas;
    @FXML TableColumn<MovimentacaoModelo, String> colValorParcela;
    @FXML TableColumn<MovimentacaoModelo, String> colDataVencimento;
    @FXML TableColumn<MovimentacaoModelo, String> colDataPagamento;
    @FXML TableColumn<MovimentacaoModelo, String> colTipoPagamento;
    
    @FXML ListView<String> lstvGarantias;

    private List<Bens> listGarantiasTemp = new ArrayList<>();
    
    @FXML
    private void initialize() {
        this.initializeTableViews();
        Emprestimo emprestimoSessao = SessionManager.getInstance().getEmprestimoSessao();
        this.initializeDetalhesDoEmprestimo(emprestimoSessao);
    }

    private void initializeTableViews() {
        colParcelas.setCellValueFactory(new PropertyValueFactory<>("parcelas"));
        colValorParcela.setCellValueFactory(new PropertyValueFactory<>("valorParcela"));
        colDataPagamento.setCellValueFactory(new PropertyValueFactory<>("dataPagamento"));
        colDataVencimento.setCellValueFactory(new PropertyValueFactory<>("dataVencimento"));
        colTipoPagamento.setCellValueFactory(new PropertyValueFactory<>("tipoPagamento"));
    }

    private void initializeDetalhesDoEmprestimo(Emprestimo emprestimoSessao) {
    	this.txtNomeCliente.setText(emprestimoSessao.getCliente().getNome());
        this.dtData.getEditor().setText(emprestimoSessao.getDataToString());
        this.txtValor.setText(String.valueOf(emprestimoSessao.getValor()));
        this.txtParcelas.setText(String.valueOf(emprestimoSessao.getParcelas()));
        this.dtPrazo.getEditor().setText(emprestimoSessao.getPrazoData());
        
        List<Movimentacao> movimentacaoList = new ArrayList<>(Fachada.getInstance().listarMoveCliente(SessionManager.getInstance().getPessoaSessao().getUid()).values());
        this.atualizarTableViewExtrato(movimentacaoList);
        this.atualizarListViewGarantias();
    }
    
    private void atualizarTableViewExtrato(List<Movimentacao> extratoList) {
    	tblvExtrato.getItems().removeAll();
    	for (Movimentacao movimentacao: extratoList) {
    		if (movimentacao.getCliente().equals(SessionManager.getInstance().getClienteSessao())) {
    			MovimentacaoModelo movimentacaoModelo = new MovimentacaoModelo(movimentacao.getInstante(), 
    					movimentacao.getTipoMovimentacao(), movimentacao.getValor());
    			tblvExtrato.getItems().add(movimentacaoModelo);
    		}
    	}
    }
    
    private void atualizarListViewGarantias() {
        lstvGarantias.getItems().removeAll();
        listGarantiasTemp.forEach(bens -> lstvGarantias.getItems().add(bens.getNome()));
    }
    
    @FXML
    public void btnVoltarPressed() {
    	if (SessionManager.getInstance().getPessoaSessao() instanceof Cliente) GerenciadorTelas.getInstance().changeScreen("telaCliente");
    	else GerenciadorTelas.getInstance().changeScreen("telaDevedorDetalhe");
    }
}
