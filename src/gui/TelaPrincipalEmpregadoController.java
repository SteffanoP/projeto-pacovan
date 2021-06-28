package gui;

import gerenciamento.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import negocio.Fachada;

public class TelaPrincipalEmpregadoController {
    @FXML Label lblNomeUsuario;
    @FXML Button btnMeusDados;

    @FXML Button btnAnaliseProposta;
    @FXML Button btnConfirmarDevedor;

    private boolean initialized = false;

    private void initialize() {
        lblNomeUsuario.setText(SessionManager.getInstance().getPessoaSessao().getNome());
    }

    @FXML
    public void btnMeusDadosPressed() {
        GerenciadorTelas.getInstance().changeScreen("telaInformacoesPessoais");
    }

    public void btnAnalisePropostaPressed(ActionEvent event) {
        GerenciadorTelas.getInstance().changeScreen("telaAnaliseProposta");
    }

    public void btnConfirmarDevedorPressed(ActionEvent event) {
        GerenciadorTelas.getInstance().changeScreen("telaDevedorDetalhe");
    }

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
