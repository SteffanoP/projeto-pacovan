package gui;

import exceptions.PessoaInexistenteException;
import gerenciamento.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import negocio.Fachada;
import negocio.beans.Cliente;
import negocio.beans.Pessoa;
import negocio.beans.Empregado;

public class TelaGeracaoRelatorioController {
    @FXML Label lblNomeEmpregado;
    @FXML Label lblNomeCliente;

    @FXML TextField txtUidCliente;
    @FXML TextField txtUidEmpregado;
    @FXML CheckBox cbxInfoPessoaisEmpregado;
    @FXML CheckBox cbxInfoPessoaisCliente;
    @FXML CheckBox cbxDevedores;
    @FXML CheckBox cbxReputacao;
    @FXML CheckBox cbxComissoes;
    @FXML CheckBox cbxLista;
    @FXML CheckBox cbxPropostas;
    @FXML CheckBox cbxExtrato;
    @FXML CheckBox cbxScore;
    @FXML CheckBox cbxBENS;

    @FXML Button btnLimparCliente;
    @FXML Button btnLimparEmpregado;
    @FXML Button btnGerarCliente;
    @FXML Button btnGerarEmpregado;
    @FXML Button btnVoltar;

    @FXML
    public void btnLimparEmpregadoPressed() {
        this.txtUidEmpregado.setText("");
        this.cbxInfoPessoaisCliente.selectedProperty().set(false);
        this.cbxDevedores.selectedProperty().set(false);
        this.cbxReputacao.selectedProperty().set(false);
        this.cbxComissoes.selectedProperty().set(false);
        this.cbxLista.selectedProperty().set(false);
    }

    @FXML
    public void btnGerarEmpregadoPressed() {
        try {
            Pessoa empregado = Fachada.getInstance().buscarPessoa(Long.parseLong(txtUidEmpregado.getText()));
            if (empregado instanceof Empregado) {
                //TODO: pegar instâncias das checkbox
                //TODO: Gerar o arquivo
            } else {
                this.gerarAlertaErroPessoa("Essa pessoa não é um empregado!");
            }
        } catch (PessoaInexistenteException | NumberFormatException e) {
            this.gerarAlertaErroPessoa("Parece que essa pessoa não existe");
        }

    }

    @FXML
    public void btnLimparClientePressed() {
        this.txtUidCliente.setText("");
        this.cbxInfoPessoaisEmpregado.selectedProperty().set(false);
        this.cbxPropostas.selectedProperty().set(false);
        this.cbxExtrato.selectedProperty().set(false);
        this.cbxScore.selectedProperty().set(false);
        this.cbxBENS.selectedProperty().set(false);
    }

    @FXML
    public void btnGerarClientePressed() {
        try {
            Pessoa empregado = Fachada.getInstance().buscarPessoa(Long.parseLong(txtUidEmpregado.getText()));
            if (empregado instanceof Cliente) {
                //TODO: pegar instâncias das checkbox
                //TODO: Gerar o arquivo
            } else {
                this.gerarAlertaErroPessoa("Essa pessoa não é um Cliente!");
            }
        } catch (PessoaInexistenteException | NumberFormatException e) {
            this.gerarAlertaErroPessoa("Parece que essa pessoa não existe");
        }
    }

    @FXML
    public void btnVoltarPressed() {
        Empregado empregado = null;
        if(SessionManager.getInstance().getPessoaSessao() instanceof Empregado)
            empregado = (Empregado)SessionManager.getInstance().getPessoaSessao();
        if(empregado != null && empregado.getPrivilegio() == 5)
            GerenciadorTelas.getInstance().changeScreen("telaAdmin");
        else if (empregado != null && (empregado.getPrivilegio() >= 1 || empregado.getPrivilegio() <= 4))
            GerenciadorTelas.getInstance().changeScreen("telaEmpregado");
    }

    private void gerarAlertaErroPessoa(String justificativa) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro");
        alerta.setHeaderText("Não conseguimos encontrar essa pessoa");
        alerta.setContentText(justificativa);
        alerta.showAndWait();
    }
}
