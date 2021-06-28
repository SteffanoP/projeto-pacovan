package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TelaPrincipalEmpregadoController {

    @FXML Button btnAnaliseProposta;
    @FXML Button btnConfirmarDevedor;

    public void btnAnalisePropostaPressed(ActionEvent event) {
        GerenciadorTelas.getInstance().changeScreen("telaAnaliseProposta");
    }

    public void btnConfirmarDevedorPressed(ActionEvent event) {
        GerenciadorTelas.getInstance().changeScreen("telaDevedorDetalhe");
    }

    public void btnVoltarPressed(ActionEvent event) {
        GerenciadorTelas.getInstance().changeScreen("telaLogin");
    }
}
