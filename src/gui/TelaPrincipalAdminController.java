package gui;

import exceptions.PessoaInexistenteException;
import gui.models.EmpregadoModelo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import negocio.Fachada;
import negocio.beans.Cliente;
import negocio.beans.Empregado;
import negocio.beans.Pessoa;

import java.util.List;

public class TelaPrincipalAdminController {
    @FXML TableView<EmpregadoModelo> tblvEmpregados;
    @FXML TableColumn<EmpregadoModelo, String> colNomeEmpregados;
    @FXML TableColumn<EmpregadoModelo, Float> colReputacaoEmpregados;

    @FXML Button btnGerarRelatorio;
    @FXML Button btnCadastrarEmpregado;
    @FXML Button btnCatalogoBens;

    private boolean initialized = false;

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
    }

    public void btnCadastrarEmpregadoPressed(ActionEvent event) {
        GerenciadorTelas.getInstance().changeScreen("telaCadastrarEmpregado");
    }

    public void btnCatalogoPressed(ActionEvent event) {
        GerenciadorTelas.getInstance().changeScreen("telaBensEmpresa");
    }

    public void btnVoltarPressed(ActionEvent event) {
        GerenciadorTelas.getInstance().changeScreen("telaLogin");
    }

    public void onMouseEntered() {
        if (!initialized) {
            this.initialize();
            initialized = true;
        }
    }
}
