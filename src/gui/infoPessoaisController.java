package gui;

import exceptions.PessoaInexistenteException;
import gerenciamento.SessaoUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import negocio.Fachada;
import negocio.beans.Pessoa;



public class infoPessoaisController {
    @FXML TextField txtNome;
    @FXML TextField txtCpf;
    @FXML TextField txtTelefone;
    @FXML TextField txtEmail;
    @FXML TextField txtNascimento;
    @FXML TextField txtSenha;
    @FXML TextField txtEndereco;
    @FXML Button editarPressed;
    @FXML Button salvarPressed;


    @FXML
    public void editarPressed(ActionEvent event) {
        txtNome.setEditable(true);
        txtSenha.setEditable(true);
        txtEndereco.setEditable(true);
        txtTelefone.setEditable(true);
    }

    public void salvarPressed(ActionEvent event){

        SessaoUsuario.getInstance().getPessoaSessao(pessoa).setSenha(txtSenha.getText());
        SessaoUsuario.getInstance().getPessoaSessao(pessoa).setNome(txtNome.getText());
        SessaoUsuario.getInstance().getPessoaSessao(pessoa).setEndereco(txtEndereco.getText());
        SessaoUsuario.getInstance().getPessoaSessao(pessoa).setTelefone(txtTelefone.getText());

        txtNome.setEditable(false);
        txtSenha.setEditable(false);
        txtEndereco.setEditable(false);
        txtTelefone.setEditable(false);
    }


}
