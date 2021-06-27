import gui.GerenciadorTelas;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(GerenciadorTelas.getInstance().getMainScene());
        primaryStage.setTitle("Sistema de Gerenciamento de Empréstimo Pessoal Alternativo");

        primaryStage.setWidth(600);
        primaryStage.setHeight(400);

        GerenciadorTelas.getInstance().setPrimaryStage(primaryStage);

        primaryStage.show();
    }
}
