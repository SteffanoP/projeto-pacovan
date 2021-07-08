package gui;

import gui.models.EmpregadoModelo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import negocio.Fachada;
import negocio.beans.Cliente;
import negocio.beans.Empregado;
import java.util.List;

import exceptions.PessoaInexistenteException;
import gerenciamento.SessionManager;

public class TelaPrincipalAdminController {
    @FXML TableView<EmpregadoModelo> tblvEmpregados;
    @FXML TableColumn<EmpregadoModelo, String> colNomeEmpregados;
    @FXML TableColumn<EmpregadoModelo, Float> colReputacaoEmpregados;

    @FXML Button btnGerarRelatorio;
    @FXML Button btnCadastrarEmpregado;
    @FXML Button btnCatalogoBens;

    @FXML
    private void initialize() {
        this.initializeTableView();
        this.atualizarTableView(tblvEmpregados, Fachada.getInstance().listarEmpregados());
    }

    private void atualizarTableView(TableView<EmpregadoModelo> tableView, List<Empregado> empregadoList) {
        for (Empregado empregado : empregadoList) {
            EmpregadoModelo empregadoModelo = new EmpregadoModelo(empregado.getNome(), empregado.getReputacao());
            tableView.getItems().add(empregadoModelo);
        }
    }

    private void initializeTableView() {
        //Initialize tableview
        colNomeEmpregados.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colReputacaoEmpregados.setCellValueFactory(new PropertyValueFactory<>("reputacao"));
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
    public void tblvDevedoresOnMouseClicked(MouseEvent event) {
    	if (tblvEmpregados.getSelectionModel().getSelectedItem() != null) {
    		String email = tblvEmpregados.getSelectionModel().getSelectedItem().getEmail();
    	
	    	try {
					SessionManager.getInstance().setPessoaSessao(Fachada.getInstance().buscarPessoa(email)); 
	            if (SessionManager.getInstance().getPessoaSessao() != null 
	            		&& SessionManager.getInstance().getPessoaSessao() instanceof Empregado 
	            		&& event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() >= 2)
	            		GerenciadorTelas.getInstance().changeScreen("telaEmpregadoDetalhe");
	        }catch (PessoaInexistenteException e) {
				e.printStackTrace();
			}
    	}
    }
    
    @FXML
    public void btnSairPressed() {
    	SessionManager.getInstance().setPessoaSessao(null);
        GerenciadorTelas.getInstance().changeScreen("telaLogin");
    }
}
