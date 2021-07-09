package gui;

import gui.models.DevedorModelo;
import gui.models.EmpregadoModelo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import negocio.Fachada;
import negocio.beans.Empregado;
import java.util.List;

import exceptions.PessoaInexistenteException;
import gerenciamento.SessionManager;
import negocio.beans.Pessoa;

public class TelaPrincipalAdminController {
	@FXML Label lblNomeUsuario;
    
    @FXML TableView<EmpregadoModelo> tblvEmpregados;
    @FXML TableColumn<EmpregadoModelo, String> colNomeEmpregados;
    @FXML TableColumn<EmpregadoModelo, Float> colReputacaoEmpregados;
    
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

    @FXML Button btnGerarRelatorio;
    @FXML Button btnCadastrarEmpregado;
    @FXML Button btnCatalogoBens;

    @FXML
    private void initialize() {
    	lblNomeUsuario.setText(SessionManager.getInstance().getPessoaSessao().getNome());
    	
        this.initializeTableViews();
        this.atualizarTableView(tblvEmpregados, Fachada.getInstance().listarEmpregados());
    }

    private void atualizarTableView(TableView<EmpregadoModelo> tableView, List<Empregado> empregadoList) {
        for (Empregado empregado : empregadoList) {
            EmpregadoModelo empregadoModelo = new EmpregadoModelo(empregado.getNome(), empregado.getReputacao(),
                    empregado.getEmail());
            tableView.getItems().add(empregadoModelo);
        }
    }

    public void btnGerarRelatorioPressed(ActionEvent event) {
        GerenciadorTelas.getInstance().changeScreen("telaRelatorio");
    }

    public void btnCadastrarEmpregadoPressed(ActionEvent event) {
        GerenciadorTelas.getInstance().changeScreen("telaCadastrarEmpregado");
    }

    public void btnCatalogoPressed(ActionEvent event) {
        GerenciadorTelas.getInstance().changeScreen("telaBensEmpresa");
    }
    
    @FXML
    public void tblvEmpregadosOnMouseClicked(MouseEvent event) {
    	if (tblvEmpregados.getSelectionModel().getSelectedItem() != null) {
    		String email = tblvEmpregados.getSelectionModel().getSelectedItem().getEmail();

	    	try {
                Pessoa empregado = Fachada.getInstance().buscarPessoa(email);
                if (empregado instanceof Empregado) {
                    SessionManager.getInstance().setEmpregadoSessao(
                            (Empregado) Fachada.getInstance().buscarPessoa(email)
                    );
                }
	            if (SessionManager.getInstance().getEmpregadoSessao() != null
	            		&& event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() >= 2)
                    GerenciadorTelas.getInstance().changeScreen("telaEmpregadoDetalhe");
	        } catch (PessoaInexistenteException e) {
				e.printStackTrace();
			}
    	}
    }
    
    private void initializeTableViews() {
    	//Initialize tableview Empregados
    	colNomeEmpregados.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colReputacaoEmpregados.setCellValueFactory(new PropertyValueFactory<>("reputacao"));
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
    
    @FXML
    public void btnSairPressed() {
    	SessionManager.getInstance().setPessoaSessao(null);
        GerenciadorTelas.getInstance().changeScreen("telaLogin");
    }
}
