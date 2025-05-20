package principal;

import controller.LoginController;
import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Loja MVC");
        URL url = new File("src/main/java/view/Login.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        Stage telaLogin = new Stage();
        LoginController lc = loader.getController();
        lc.setStage(telaLogin);
        telaLogin.setOnShown(event -> {
            lc.abrirJanela();
        });
        Scene scene = new Scene (root);
        scene.getStylesheets().add(getClass().getResource(""
                + "/css/login.css").toExternalForm());
        telaLogin.setScene(scene);
        telaLogin.show();
    }

    public static void main(String[] args) {
        launch();
    }

}