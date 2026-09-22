package app;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Main extends Application {
    @Override
    public void start(Stage palco) throws Exception {

        Parent raiz = FXMLLoader.load(getClass().getResource("/view/produto.fxml"));
        Scene cena = new Scene(raiz, 400, 320);
        palco.setTitle("Sistema de Gestão de Vendas - Cadastro de Produto");
        palco.setScene(cena);
        palco.setMinWidth(400);
        palco.setMinHeight(320);
        palco.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}