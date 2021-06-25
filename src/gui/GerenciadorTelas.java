package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GerenciadorTelas {
    private static GerenciadorTelas instance;
    private Stage primaryStage;
    private Scene mainScene;

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
        Parent telaLogin = null;
        
        try {
        	telaLogin = fxmlLoader.load(getClass().getResource("/gui/TelaLogin.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mainScene = new Scene(telaLogin);
    }
    
    public void changeScreen(String tela) {
    	FXMLLoader fxmlLoader = new FXMLLoader();
    	
    	switch (tela) {
		case "telaCliente": {
	        Parent telaCliente = null;
	        
	        try {
	        	telaCliente = fxmlLoader.load(getClass().getResource("/gui/TelaPrincipalCliente.fxml").openStream());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        this.mainScene = new Scene(telaCliente);
	        break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + tela);
		}

        primaryStage.setScene(mainScene);
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
