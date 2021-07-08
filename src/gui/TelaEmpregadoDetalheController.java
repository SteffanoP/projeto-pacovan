package gui;

import exceptions.PessoaInexistenteException;
import gerenciamento.SessionManager;
import gui.models.DevedorModelo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import negocio.Fachada;
import negocio.beans.Cliente;
import negocio.beans.Empregado;
import negocio.beans.Emprestimo;
import negocio.beans.Pessoa;

import java.util.List;

public class TelaEmpregadoDetalheController {
    @FXML TextField txtUID;
    @FXML TextField txtNome;
    @FXML TextField txtEmail;
    @FXML TextField txtEndereco;
    @FXML TextField txtTelefone;
    @FXML TextField txtCPF;
    @FXML TextField txtReputacao;
    @FXML TextField txtSalarioBase;

    @FXML TableView<DevedorModelo> tblvDevedores;
    @FXML TableColumn<DevedorModelo, Double> colValorDevido;
    @FXML TableColumn<DevedorModelo, String> colDataPagamento;
    @FXML TableColumn<DevedorModelo, String> colNomeCliente;
    @FXML TableColumn<DevedorModelo, Double> colParcelas;
    @FXML TableColumn<DevedorModelo, Float> colConfiancaPagamento;

    @FXML Button btnVoltar;

    @FXML
    private void initialize() {
        Empregado empregadoSessao = SessionManager.getInstance().getEmpregadoSessao();
        this.initializeInfoEmpregado(empregadoSessao);
        this.initializeTableViews();
        this.atualizaTableViewDevedores(tblvDevedores,Fachada.getInstance().listarComissoesEmprestimo(empregadoSessao));
    }

    private void initializeInfoEmpregado(Empregado empregado) {
        this.txtUID.setText(String.valueOf(empregado.getUid()));
        this.txtNome.setText(empregado.getNome());
        this.txtNome.editableProperty().set(false);
        this.txtEmail.setText(empregado.getEmail());
        this.txtEmail.editableProperty().set(false);
        this.txtEndereco.setText(empregado.getEndereco());
        this.txtEndereco.editableProperty().set(false);
        this.txtTelefone.setText(empregado.getTelefone());
        this.txtTelefone.editableProperty().set(false);
        this.txtCPF.setText(empregado.getCpf());
        this.txtReputacao.setText(String.valueOf(empregado.getReputacao()));
        this.txtReputacao.editableProperty().set(false);
        this.txtSalarioBase.setText(String.valueOf(empregado.getSalarioBase()));
        this.txtSalarioBase.editableProperty().set(false);
    }

    private void initializeTableViews() {
        //Inicializa a TableView do Devedor
        colValorDevido.setCellValueFactory(new PropertyValueFactory<>("valorDevido"));
        colDataPagamento.setCellValueFactory(new PropertyValueFactory<>("dataPagamento"));
        colNomeCliente.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        colParcelas.setCellValueFactory(new PropertyValueFactory<>("parcelas"));
        colConfiancaPagamento.setCellValueFactory(new PropertyValueFactory<>("confiancaPagamento"));
    }

    private void atualizaTableViewDevedores(TableView<DevedorModelo> tableView, List<Emprestimo> emprestimos) {
        tableView.getItems().removeAll();
        emprestimos.forEach(emprestimo -> {
            DevedorModelo devedorModelo = new DevedorModelo(emprestimo.getValor(), emprestimo.getDataPagamento(),
                    emprestimo.getCliente(), emprestimo.getParcelas(), emprestimo.getConfiancaPagamento(),
                    emprestimo.getCliente().getEmail());
            tableView.getItems().add(devedorModelo);
        });
    }

    @FXML
    private void tblvDevedoresOnMouseClicked(MouseEvent event) {
        if (tblvDevedores.getSelectionModel().getSelectedItem() != null) {
            String emailDevedor = tblvDevedores.getSelectionModel().getSelectedItem().getEmail();
            try {
                Pessoa devedor = Fachada.getInstance().buscarPessoa(emailDevedor);
                if (devedor instanceof Cliente) {
                    SessionManager.getInstance().setClienteSessao((Cliente) devedor);
                }
            } catch (PessoaInexistenteException e) {
                e.printStackTrace();
            }

            if (SessionManager.getInstance().getClienteSessao() != null &&
                    event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() >= 2)
                    GerenciadorTelas.getInstance().changeScreen("telaDevedorDetalhe");
        }
    }

    @FXML
    public void btnVoltarPressed() {
        SessionManager.getInstance().setEmpregadoSessao(null);
        GerenciadorTelas.getInstance().changeScreen("telaAdmin");
    }
}
