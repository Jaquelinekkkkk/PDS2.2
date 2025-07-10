package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Produto;
import model.ProdutoDAO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class ProdutoController {

    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    @FXML
    private TextField txtDescricao;
    @FXML
    private TextField txtValor;
    @FXML
    private TextField txtEstoque;
    @FXML
    private VBox vboxProdutos;

    @FXML
    public void cadastrarProdutoClick() {
        try {
            String descricao = txtDescricao.getText();
            BigDecimal valor = new BigDecimal(txtValor.getText());
            int estoque = Integer.parseInt(txtEstoque.getText());

            Produto produto = new Produto(descricao, valor, estoque);
            produtoDAO.cadastrarProduto(produto);
            listarProdutos();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao cadastrar produto.");
            alert.show();
        }
    }

    @FXML
    public void listarProdutos() {
        vboxProdutos.getChildren().clear();

        try {
            List<Produto> produtos = produtoDAO.listarProdutos();
            for (Produto p : produtos) {
                Label lbl = new Label(
                    "🛒 " + p.getDescricao() + " | 💰 R$" + p.getValor() + " | 📦 " + p.getQuantidadeEstoque()
                );
                vboxProdutos.getChildren().add(lbl);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao listar produtos.");
            alert.show();
        }
    }
}