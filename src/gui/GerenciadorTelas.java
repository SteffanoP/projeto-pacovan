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
    	Parent telaAtual = null;
    	
    	switch (tela) {
		case "telaCliente": {
	        try {
	        	telaAtual = fxmlLoader.load(getClass().getResource("/gui/TelaPrincipalCliente.fxml").openStream());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        break;
		}
		case "telaEmpregado": {
	        try {
	        	telaAtual = fxmlLoader.load(getClass().getResource("/gui/TelaPrincipalEmpregado.fxml").openStream());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        break;
		}
		case "telaAdmin": {	        
	        try {
	        	telaAtual = fxmlLoader.load(getClass().getResource("/gui/TelaPrincipalAdmin.fxml").openStream());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        break;
		}
		case "telaCadastrar":{
		    try {
		        telaAtual = fxmlLoader.load(getClass().getResource("/gui/TelaCadastrarCliente.fxml").openStream());
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    break;
		}
		case "telaCadastrarEmpregado": {
			try {
				telaAtual = fxmlLoader.load(getClass().getResource("/gui/TelaCadastroEmpregados.fxml").openStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
		case "telaLogin": {
            try {
                telaAtual = fxmlLoader.load(getClass().getResource("/gui/TelaLogin.fxml").openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        }
        case "telaBens": {
			try {
				telaAtual = fxmlLoader.load(getClass().getResource("/gui/TelaBENS.fxml").openStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
        }
        case "telaAnaliseProposta": {
        	try {
        		telaAtual = fxmlLoader.load(getClass().getResource("/gui/TelaAnaliseProposta.fxml").openStream());
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        	break;
        }
        case "telaDevedorDetalhe": {
        	try {
        		telaAtual = fxmlLoader.load(getClass().getResource("/gui/TelaDevedorDetalhe.fxml").openStream());
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        	break;
        }
		default:
			throw new IllegalArgumentException("Unexpected value: " + tela);
		}

    	this.mainScene = new Scene(telaAtual);
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
