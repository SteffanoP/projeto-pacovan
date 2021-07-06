package gui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GerenciadorTelas {
    private static GerenciadorTelas instance;
    private Stage primaryStage;
    private Scene mainScene;
    
    private Parent telaLogin, telaCliente, telaEmpregado, telaAdmin, telaCadastrarCliente, telaCadastrarEmpregado,
	telaFeedbackProposta, telaBensEmpresa, telaAnaliseProposta, telaDevedorDetalhe, telaInformacoesPessoais, 
	telaBENS, telaCriarProposta, telaEmprestimoDetalhe;

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
    	loadScreens();
    	
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
            telaAtual = getTelaLogin();
            break;
     	}
     	case "telaFeedbackProposta": {
     		try {
     			telaAtual = fxmlLoader.load(getClass().getResource("/gui/TelaFeedbackProposta.fxml").openStream());
			} catch (IOException e) {
     			e.printStackTrace();
			}
     	}
	    case "telaBensEmpresa": {
	        	try {
	        		telaAtual = fxmlLoader.load(getClass().getResource("/gui/TelaBensEmpresa.fxml").openStream());
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
		case "telaInformacoesPessoais": {
            try {
                telaAtual = fxmlLoader.load(getClass().getResource("/gui/TelaInformacoesPessoais.fxml").openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
		 }
		case "telaBENS": {
            try {
                telaAtual = fxmlLoader.load(getClass().getResource("/gui/TelaBENS.fxml").openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
		 }
		case "telaCriarProposta": {
            try {
                telaAtual = fxmlLoader.load(getClass().getResource("/gui/TelaCriarProposta.fxml").openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
		}
		case "telaEmprestimoDetalhe": {
            try {
                telaAtual = fxmlLoader.load(getClass().getResource("/gui/TelaEmpregadoDetalhe.fxml").openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
		}
		case "telaRelatorio": {
			try {
				telaAtual = fxmlLoader.load(getClass().getResource("/gui/TelaGeracaoRelatorio.fxml").openStream());
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
    
    public void loadScreens() {
    	FXMLLoader fxmlLoader = new FXMLLoader();
    	Parent telaLogin, telaCliente, telaEmpregado, telaAdmin, telaCadastrarCliente, telaCadastrarEmpregado,
    	telaFeedbackProposta, telaBensEmpresa, telaAnaliseProposta, telaDevedorDetalhe, telaInformacoesPessoais, 
    	telaBENS, telaCriarProposta, telaEmprestimoDetalhe;
    	
    	try {
			telaLogin = fxmlLoader.load(getClass().getResource("/gui/TelaLogin.fxml").openStream());
			/*telaCliente = fxmlLoader.load(getClass().getResource("/gui/TelaPrincipalCliente.fxml").openStream());
			telaEmpregado = fxmlLoader.load(getClass().getResource("/gui/TelaPrincipalEmpregado.fxml").openStream());
			telaAdmin = fxmlLoader.load(getClass().getResource("/gui/TelaPrincipalAdmin.fxml").openStream());
			telaCadastrarCliente = fxmlLoader.load(getClass().getResource("/gui/TelaCadastrarCliente.fxml").openStream());
			telaCadastrarEmpregado = fxmlLoader.load(getClass().getResource("/gui/TelaCadastroEmpregado.fxml").openStream());
			telaFeedbackProposta = fxmlLoader.load(getClass().getResource("/gui/TelaCliente.fxml").openStream());
			telaBensEmpresa = fxmlLoader.load(getClass().getResource("/gui/TelaBensEmpresa.fxml").openStream());
			telaAnaliseProposta = fxmlLoader.load(getClass().getResource("/gui/TelaAnaliseProposta.fxml").openStream());
			telaDevedorDetalhe = fxmlLoader.load(getClass().getResource("/gui/TelaDevedorDetalhe.fxml").openStream());
			telaInformacoesPessoais = fxmlLoader.load(getClass().getResource("/gui/TelaInformacoesPessoais.fxml").openStream());
			telaBENS = fxmlLoader.load(getClass().getResource("/gui/TelaBENS.fxml").openStream());
			telaCriarProposta = fxmlLoader.load(getClass().getResource("/gui/TelaCriarProposta.fxml").openStream());
			telaEmprestimoDetalhe = fxmlLoader.load(getClass().getResource("/gui/TelaEmprestimoDetalhe.fxml").openStream());*/
			
			setTelaLogin(telaLogin);
			/*setTelaCliente(telaCliente);
			setTelaEmpregado(telaEmpregado);
			setTelaAdmin(telaAdmin);
			setTelaCadastrarCliente(telaCadastrarCliente);
			setTelaCadastrarEmpregado(telaCadastrarEmpregado);
			setTelaFeedbackProposta(telaFeedbackProposta);
			setTelaBensEmpresa(telaBensEmpresa);
			setTelaAnaliseProposta(telaAnaliseProposta);
			setTelaDevedorDetalhe(telaDevedorDetalhe);
			setTelaInformacoesPessoais(telaInformacoesPessoais);
			setTelaBENS(telaBENS);
			setTelaCriarProposta(telaCriarProposta);
			setTelaEmprestimoDetalhe(telaEmprestimoDetalhe);*/		
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public Parent getTelaLogin() {
		return telaLogin;
	}

	public void setTelaLogin(Parent telaLogin) {
		this.telaLogin = telaLogin;
	}

	public Parent getTelaCliente() {
		return telaCliente;
	}

	public void setTelaCliente(Parent telaCliente) {
		this.telaCliente = telaCliente;
	}

	public Parent getTelaEmpregado() {
		return telaEmpregado;
	}

	public void setTelaEmpregado(Parent telaEmpregado) {
		this.telaEmpregado = telaEmpregado;
	}

	public Parent getTelaAdmin() {
		return telaAdmin;
	}

	public void setTelaAdmin(Parent telaAdmin) {
		this.telaAdmin = telaAdmin;
	}

	public Parent getTelaCadastrarCliente() {
		return telaCadastrarCliente;
	}

	public void setTelaCadastrarCliente(Parent telaCadastrarCliente) {
		this.telaCadastrarCliente = telaCadastrarCliente;
	}

	public Parent getTelaCadastrarEmpregado() {
		return telaCadastrarEmpregado;
	}

	public void setTelaCadastrarEmpregado(Parent telaCadastrarEmpregado) {
		this.telaCadastrarEmpregado = telaCadastrarEmpregado;
	}

	public Parent getTelaFeedbackProposta() {
		return telaFeedbackProposta;
	}

	public void setTelaFeedbackProposta(Parent telaFeedbackProposta) {
		this.telaFeedbackProposta = telaFeedbackProposta;
	}

	public Parent getTelaBensEmpresa() {
		return telaBensEmpresa;
	}

	public void setTelaBensEmpresa(Parent telaBensEmpresa) {
		this.telaBensEmpresa = telaBensEmpresa;
	}

	public Parent getTelaAnaliseProposta() {
		return telaAnaliseProposta;
	}

	public void setTelaAnaliseProposta(Parent telaAnaliseProposta) {
		this.telaAnaliseProposta = telaAnaliseProposta;
	}

	public Parent getTelaDevedorDetalhe() {
		return telaDevedorDetalhe;
	}

	public void setTelaDevedorDetalhe(Parent telaDevedorDetalhe) {
		this.telaDevedorDetalhe = telaDevedorDetalhe;
	}

	public Parent getTelaInformacoesPessoais() {
		return telaInformacoesPessoais;
	}

	public void setTelaInformacoesPessoais(Parent telaInformacoesPessoais) {
		this.telaInformacoesPessoais = telaInformacoesPessoais;
	}

	public Parent getTelaBENS() {
		return telaBENS;
	}

	public void setTelaBENS(Parent telaBENS) {
		this.telaBENS = telaBENS;
	}

	public Parent getTelaCriarProposta() {
		return telaCriarProposta;
	}

	public void setTelaCriarProposta(Parent telaCriarProposta) {
		this.telaCriarProposta = telaCriarProposta;
	}

	public Parent getTelaEmprestimoDetalhe() {
		return telaEmprestimoDetalhe;
	}

	public void setTelaEmprestimoDetalhe(Parent telaEmprestimoDetalhe) {
		this.telaEmprestimoDetalhe = telaEmprestimoDetalhe;
	}
    
    
}
