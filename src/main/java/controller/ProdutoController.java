package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ProdutoController implements Initializable {
    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoPreco;
    @FXML
    private TextField campoEstoque;
    @FXML
    private Button botaoSalvar;


    @Override
    public void initialize(URL local, ResourceBundle rb) {
        botaoSalvar.disableProperty().bind(
                campoNome.textProperty().isEmpty()
        );
        botaoSalvar.disableProperty().bind(
                campoPreco.textProperty().isEmpty()
        );
        botaoSalvar.disableProperty().bind(
                campoEstoque.textProperty().isEmpty()
        );

        campoPreco.textProperty().addListener((obs, antigo, novo) -> {
            if (!novo.matches("\\d*\\.?\\d*")) {
                campoPreco.setText(antigo);
            }
        });
        campoEstoque.textProperty().addListener((obs, antigo, novo) -> {
            if (!novo.matches("\\d*")) {
                campoEstoque.setText(antigo);
            }
        });

    }


    @FXML
    private void aoSalvar() {
        String nome = campoNome.getText();
        String preco = campoPreco.getText();
        String estoque = campoEstoque.getText();

        System.out.print("=== DADOS DO PRODUTO INFORMADOS ===");
        System.out.print("Nome: " + nome + " | Preço: " + preco + " | Estoque: " + estoque);
        System.out.print("-----------------------------------");

        limparCampos();

    }
    private void limparCampos() {
        campoNome.clear();
        campoPreco.clear();
        campoEstoque.clear();
        campoNome.requestFocus();
    }



}
