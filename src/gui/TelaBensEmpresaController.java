package gui;

import gui.models.BensModelo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import negocio.Fachada;
import negocio.beans.Bens;
import negocio.beans.CategoriaBens;

import java.util.ArrayList;
import java.util.List;

public class TelaBensEmpresaController {

    @FXML TableView<BensModelo> tblvMoveisFungiveis;
    @FXML TableColumn<BensModelo, String> colDtCadastroFungiveis;
    @FXML TableColumn<BensModelo, Double> colValorFungiveis;
    @FXML TableColumn<BensModelo, String> colNomeFungiveis;
    @FXML TableColumn<BensModelo, String> colDescricaoFungiveis;
    @FXML TableColumn<BensModelo, Integer> colTempoUsoFungiveis;

    @FXML TableView<BensModelo> tblvMoveisInfungiveis;
    @FXML TableColumn<BensModelo, String> colDtCadastroInfungiveis;
    @FXML TableColumn<BensModelo, Double> colValorInfungiveis;
    @FXML TableColumn<BensModelo, String> colNomeInfungiveis;
    @FXML TableColumn<BensModelo, String> colDescricaoInfungiveis;
    @FXML TableColumn<BensModelo, Integer> colTempoUsoInfungiveis;

    @FXML TableView<BensModelo> tblvImoveis;
    @FXML TableColumn<BensModelo, String> colDtCadastroImoveis;
    @FXML TableColumn<BensModelo, Double> colValorImoveis;
    @FXML TableColumn<BensModelo, String> colNomeImoveis;
    @FXML TableColumn<BensModelo, String> colDescricaoImoveis;
    @FXML TableColumn<BensModelo, Integer> colTempoUsoImoveis;

    @FXML
    private void initialize() {
        this.initializeTableViews();
        List<Bens> bensFungiveisList = new
                ArrayList<>(Fachada.getInstance().listarBensEmpresaCategoria(CategoriaBens.MOVEL_FUNGIVEL).values());
        this.atualizaTableViewBens(tblvMoveisFungiveis, bensFungiveisList);
        List<Bens> bensInfungiveisList = new
                ArrayList<>(Fachada.getInstance().listarBensEmpresaCategoria(CategoriaBens.MOVEL_INFUNGIVEL).values());
        this.atualizaTableViewBens(tblvMoveisInfungiveis, bensInfungiveisList);
        List<Bens> bensImoveisList = new
                ArrayList<>(Fachada.getInstance().listarBensEmpresaCategoria(CategoriaBens.IMOVEL).values());
        this.atualizaTableViewBens(tblvImoveis, bensImoveisList);
    }

    private void atualizaTableViewBens(TableView<BensModelo> tableView, List<Bens> bensList) {
        //TODO: Pensar em mover essa conversão para o local apropriado
        for (Bens bens : bensList) {
            BensModelo bensModelo = new BensModelo(bens.getDataCadastro(), bens.getValor(), bens.getNome(),
                    bens.getDescricao(), bens.getTempoDeUso());
            tableView.getItems().add(bensModelo);
        }
    }

    private void initializeTableViews() {
        //Seta as properties da TableView Móveis Fungíveis
        this.setCellValuesProperties(colDtCadastroFungiveis, colValorFungiveis, colNomeFungiveis, colDescricaoFungiveis,
                colTempoUsoFungiveis);

        //Seta as properties da TableView Móveis Infungíveis
        this.setCellValuesProperties(colDtCadastroInfungiveis, colValorInfungiveis, colNomeInfungiveis,
                colDescricaoInfungiveis, colTempoUsoInfungiveis);

        //Seta as properties da TableView Imóveis
        this.setCellValuesProperties(colDtCadastroImoveis, colValorImoveis, colNomeImoveis, colDescricaoImoveis,
                colTempoUsoImoveis);
    }

    private void setCellValuesProperties(TableColumn<BensModelo, String> colDtCadastroInfungiveis, TableColumn<BensModelo, Double> colValorInfungiveis, TableColumn<BensModelo, String> colNomeInfungiveis, TableColumn<BensModelo, String> colDescricaoInfungiveis, TableColumn<BensModelo, Integer> colTempoUsoInfungiveis) {
        colDtCadastroInfungiveis.setCellValueFactory(new PropertyValueFactory<>("dataCadastro"));
        colValorInfungiveis.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colNomeInfungiveis.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDescricaoInfungiveis.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colTempoUsoInfungiveis.setCellValueFactory(new PropertyValueFactory<>("tempoDeUso"));
    }
    
    @FXML
    public void btnVoltarPressed(ActionEvent event) {
        GerenciadorTelas.getInstance().changeScreen("telaAdmin");
    }

}
