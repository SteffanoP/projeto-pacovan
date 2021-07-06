package gui;

import gerenciamento.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import negocio.beans.Pessoa;
import negocio.beans.Empregado;

import java.awt.event.ActionEvent;

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
    public void btnVoltarPressed(javafx.event.ActionEvent event) {
        Empregado empregado = null;
        if(SessionManager.getInstance().getPessoaSessao() instanceof Empregado)
            empregado = (Empregado)SessionManager.getInstance().getPessoaSessao();
        if(empregado != null && empregado.getPrivilegio() == 5)
            GerenciadorTelas.getInstance().changeScreen("telaAdmin");
        else if (empregado != null && (empregado.getPrivilegio() >= 1 || empregado.getPrivilegio() <= 4))
            GerenciadorTelas.getInstance().changeScreen("telaEmpregado");
    }
}
