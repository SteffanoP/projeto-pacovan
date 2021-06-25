package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GerenciadorTelas {
    private static GerenciadorTelas instance;
    private Stage primaryStage;
    private Scene mainScene;

    private TelaLoginController telaLoginController;

    private GerenciadorTelas() {
        this.initialize();
    }

    public static GerenciadorTelas getInstance() {
        if (instance == null) {
            instance = new GerenciadorTelas();
        }
        return instance;
    }

    private void initialize() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        AnchorPane panePrincipal = null;
        try {
            panePrincipal = fxmlLoader.load(getClass().getResource("/gui/TelaCadastroEmpregados.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mainScene = new Scene(panePrincipal);
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
