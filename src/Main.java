import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sistema de Gerenciamento de Empréstimo Pessoal Alternativo");

        primaryStage.setWidth(1024);
        primaryStage.setHeight(768);

        primaryStage.show();
    }
}
