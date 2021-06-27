package gui;

import exceptions.PessoaInexistenteException;
import gerenciamento.SessaoUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import negocio.Fachada;
import negocio.beans.Cliente;
import negocio.beans.Empregado;
import negocio.beans.Pessoa;

public class TelaPrincipalAdminController {

    @FXML Button btnGerarRelatorio;
    @FXML Button btnCadastrarEmpregado;
    @FXML Button btnCatalogoBens;

    public void btnGerarRelatorioPressed(ActionEvent event) {
    }

    public void btnCadastrarEmpregadoPressed(ActionEvent event) {
        GerenciadorTelas.getInstance().changeScreen("telaCadastrarEmpregado");
    }

    public void btnCatalogoPressed(ActionEvent event) {
        GerenciadorTelas.getInstance().changeScreen("telaBens");
    }
}
