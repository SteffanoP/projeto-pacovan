package gui;

import exceptions.BensDuplicadoException;
import exceptions.PessoaInexistenteException;
import gerenciamento.SessionManager;
import gui.models.BensModelo;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import negocio.Fachada;
import negocio.beans.Bens;
import negocio.beans.CategoriaBens;
import negocio.beans.Cliente;

import java.util.ArrayList;
import java.util.List;

public class TelaBENSController {
    @FXML Button btnVoltarTelaCliente;

    @FXML TableView<BensModelo> tblvBens;
    @FXML TableColumn<BensModelo, String> colNomeBens;
    @FXML TableColumn<BensModelo, String> colDescricaoBens;
    @FXML TableColumn<BensModelo, Double> colValorBens;
    @FXML TableColumn<BensModelo, String> colDataCadastro;

    @FXML TextField txtNomeBens;
    @FXML TextField txtTempoUso;
    @FXML TextArea txtDescricao;
    @FXML TextField txtCliente;
    @FXML TextField txtValor;
    @FXML SplitMenuButton splCategoriaBens;
    @FXML MenuItem mnuiFungivel;
    @FXML MenuItem mnuiInfugivel;
    @FXML MenuItem mnuiImovel;
    @FXML Button btnInserirBens;

    CategoriaBens categoriaSelecionadaCadastro = null;

    @FXML
    private void initialize() {
        this.initializeTableViews();
        try {
            List<Bens> bensList = new ArrayList<>(Fachada.getInstance().listarBensCliente(SessionManager.getInstance().getPessoaSessao().getUid()).values());
            this.atualizarTableViewBens(bensList);
        } catch (PessoaInexistenteException e) {
            e.printStackTrace();
        }

        //Inicializa Cadastro de Bens
        txtCliente.setText(SessionManager.getInstance().getPessoaSessao().getNome());

        mnuiFungivel.setOnAction((event -> {
            categoriaSelecionadaCadastro = CategoriaBens.MOVEL_FUNGIVEL;
            splCategoriaBens.setText(categoriaSelecionadaCadastro.getNome());
        }));
        mnuiInfugivel.setOnAction((event -> {
            categoriaSelecionadaCadastro = CategoriaBens.MOVEL_INFUNGIVEL;
            splCategoriaBens.setText(categoriaSelecionadaCadastro.getNome());
        }));
        mnuiImovel.setOnAction((event -> {
            categoriaSelecionadaCadastro = CategoriaBens.IMOVEL;
            splCategoriaBens.setText(categoriaSelecionadaCadastro.getNome());
        }));
    }
    
    @FXML
    public void tblvBensOnMouseClicked() {
    	// TODO: selecionar BENS da sessão
    }
    
    @FXML
    public void btnRemoverBens() {
    	// TODO: remover BENS selecionado
    }

    @FXML
    public void btnInserirBens() {
        if (!this.isTextFieldsInserirBensBlank()) {
            Bens bens = new Bens();
            try {
                bens.setNome(txtNomeBens.getText());
                bens.setTempoDeUso(Integer.parseInt(txtTempoUso.getText()));
                bens.setDescricao(txtDescricao.getText());
                bens.setCliente((Cliente) SessionManager.getInstance().getPessoaSessao());
                bens.setValor(Double.parseDouble(txtValor.getText()));
                bens.setCategoria(categoriaSelecionadaCadastro);
                Fachada.getInstance().inserirBens(bens);
                try {
                    List<Bens> bensList = new
                            ArrayList<>(Fachada.getInstance().listarBensCliente(bens.getCliente().getUid()).values());
                    this.atualizarTableViewBens(bensList);
                } catch (PessoaInexistenteException e) {
                    e.printStackTrace();
                }
            } catch (NumberFormatException e) {
                this.gerarAlertaErroCadastro("Há campos com valores inválidos!");
            } catch (BensDuplicadoException e) {
                this.gerarAlertaErroCadastro("O BENS cadastro já está cadastrado no sistema!");
            }

        } else {
            this.gerarAlertaErroCadastro("Verifique se você preencheu todos os campos.");
        }
    }

    @FXML
    public void btnVoltarTelaClientePressed() {
        GerenciadorTelas.getInstance().changeScreen("telaCliente");
    }

    private void atualizarTableViewBens(List<Bens> bensList) {
        //Bens
        tblvBens.getItems().removeAll();
        for (Bens bens : bensList) {
            BensModelo bensModelo = new BensModelo(bens.getDataCadastro(), bens.getValor(), bens.getNome(),
                    bens.getDescricao(), bens.getTempoDeUso());
            tblvBens.getItems().add(bensModelo);
        }
    }

    private void initializeTableViews() {
        //Bens
        colDataCadastro.setCellValueFactory(new PropertyValueFactory<>("dataCadastro"));
        colValorBens.setCellValueFactory(new  PropertyValueFactory<>("valor"));
        colNomeBens.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDescricaoBens.setCellValueFactory(new PropertyValueFactory<>("descricao"));
    }

    private boolean isTextFieldsInserirBensBlank() {
        return txtNomeBens.getText().isBlank() || txtTempoUso.getText().isBlank() || txtDescricao.getText().isBlank() ||
                txtCliente.getText().isBlank() || txtValor.getText().isBlank(); //TODO: Inserir CategoriaBens
    }

    private void gerarAlertaErroCadastro(String justificativa) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro de cadastro");
        alerta.setHeaderText("Parece que tivemos um erro com seu cadastro");
        alerta.setContentText(justificativa);
        alerta.showAndWait();
    }
}
