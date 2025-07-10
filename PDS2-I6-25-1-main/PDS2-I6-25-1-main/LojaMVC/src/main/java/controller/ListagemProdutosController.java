package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Produto;
import util.AlertaUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import model.ProdutoDAO;

public class ListagemProdutosController implements Initializable {

    private Stage stage;
    ObservableList<Produto> lista;

    @FXML
    private TextField txtPesquisarProduto;

    @FXML
    private TableView<Produto> tabelaProdutos;

    @FXML
    private Button btnFecharProduto;

    public void setStage(Stage tela) {
        this.stage = tela;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            carregarProdutosTabela();
        } catch (SQLException e) {
            AlertaUtil.mostrarErro("Erro", "Erro ao carregar produtos");
        }
    }

    private void carregarProdutosTabela() throws SQLException {
        ProdutoDAO dao = new ProdutoDAO();
        lista = FXCollections.observableArrayList(dao.listarProdutos());

        if (!lista.isEmpty()) {
            tabelaProdutos.getColumns().clear();

            TableColumn<Produto, Number> colunaID = new TableColumn<>("ID");
            colunaID.setCellValueFactory(p -> p.getValue().idProperty());
            colunaID.setPrefWidth(50);

            TableColumn<Produto, String> colunaDescricao = new TableColumn<>("Descrição");
            colunaDescricao.setCellValueFactory(p -> p.getValue().descricaoProperty());

            TableColumn<Produto, String> colunaValor = new TableColumn<>("Valor");
            colunaValor.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValor().toString()));

            TableColumn<Produto, Number> colunaEstoque = new TableColumn<>("Estoque");
            colunaEstoque.setCellValueFactory(p -> p.getValue().quantidadeEstoqueProperty());

            tabelaProdutos.getColumns().addAll(colunaID, colunaDescricao, colunaValor, colunaEstoque);

            FilteredList<Produto> listaFiltrada = new FilteredList<>(lista, p -> true);

            txtPesquisarProduto.textProperty().addListener((obs, oldVal, newVal) -> {
                listaFiltrada.setPredicate(produto -> {
                    if (newVal == null || newVal.isEmpty()) {
                        return true;
                    }
                    String filtro = newVal.toLowerCase();
                    return produto.getDescricao().toLowerCase().contains(filtro)
                            || String.valueOf(produto.getId()).contains(filtro)
                            || produto.getValor().toString().contains(filtro)
                            || String.valueOf(produto.getQuantidadeEstoque()).contains(filtro);
                });
            });

            SortedList<Produto> listaOrdenada = new SortedList<>(listaFiltrada);
            listaOrdenada.comparatorProperty().bind(tabelaProdutos.comparatorProperty());
            tabelaProdutos.setItems(listaOrdenada);

        } else {
            AlertaUtil.mostrarErro("Nenhum produto encontrado", "Nenhum produto disponível para listar.");
        }
    }
 //   @FXML
//void tabelaProdutosClick(javafx.scene.input.MouseEvent event) {
 //   if (event.getClickCount() == 1) {
   //     Produto produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
     //   if (produtoSelecionado != null) {
        //    System.out.println("Produto selecionado: " + produtoSelecionado.getDescricao());
   //     }
   // }
//}

    @FXML
    void btnFecharProdutoClick() {
        if (stage != null) {
            stage.close();
        }
    }
}