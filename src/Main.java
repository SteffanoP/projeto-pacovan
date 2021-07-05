import exceptions.PessoaCPFInvalidoException;
import exceptions.PessoaDuplicadoException;
import gui.GerenciadorTelas;
import javafx.application.Application;
import javafx.stage.Stage;
import negocio.Fachada;
import negocio.beans.Empregado;
import java.time.LocalDate;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        /*
        Empregado steffano = new Empregado();
        steffano.setNome("Steffano");
        steffano.setEmail("steffanoxpereira@gmail.com");
        steffano.setCpf("80679349081");
        steffano.setPrivilegio(5);
        steffano.setDataNascimento(LocalDate.of(2000,7,21));
        
        try {
            Fachada.getInstance().cadastrarPessoa(steffano,"senha");
        } catch (PessoaCPFInvalidoException | PessoaDuplicadoException e) {
            e.printStackTrace();
        }
        */
    	
        primaryStage.setScene(GerenciadorTelas.getInstance().getMainScene());
        primaryStage.setTitle("Sistema de Gerenciamento de Empréstimo Pessoal Alternativo");

        primaryStage.setWidth(1024);
        primaryStage.setHeight(768);

        GerenciadorTelas.getInstance().setPrimaryStage(primaryStage);

        primaryStage.show();
    }
}
