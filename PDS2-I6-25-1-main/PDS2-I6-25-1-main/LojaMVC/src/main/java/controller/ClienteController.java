package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Cliente;
import model.ClienteDAO;
import util.AlertaUtil;

public class ClienteController {

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private Stage stageCliente;

    @FXML
    private Button btnSalvar;

    @FXML
    private TextField txtDataNascimento;
    @FXML
    private TextField txtEndereco;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtTelefone;

    @FXML
    private TableView<Cliente> tabelaClientes;

    @FXML
    private TableColumn<Cliente, String> colNome;

    @FXML
    private TableColumn<Cliente, String> colTelefone;

    @FXML
    private TableColumn<Cliente, String> colEndereco;

    @FXML
    private TableColumn<Cliente, String> colDataNascimento;

    @FXML
    void btnSalvarClick(ActionEvent event) {
        if (camposVazios()) {
            AlertaUtil.mostrarErro("Campos não preenchidos", "Você deve preencher todos os campos!");
            return;
        }

        try {
            Date dataFormatada = Date.valueOf(txtDataNascimento.getText());
            Cliente cliente = new Cliente(txtNome.getText(), txtTelefone.getText(), txtEndereco.getText(), dataFormatada);
            clienteDAO.inserirCliente(cliente);
            limparCampos();
            carregarClientesTabela();
        } catch (IllegalArgumentException e) {
            AlertaUtil.mostrarErro("Data inválida", "Formato da data deve ser YYYY-MM-DD.");
        }
    }

    private boolean camposVazios() {
        return txtNome.getText().isBlank()
            || txtTelefone.getText().isBlank()
            || txtEndereco.getText().isBlank()
            || txtDataNascimento.getText().isBlank();
    }

    private void limparCampos() {
        txtNome.clear();
        txtTelefone.clear();
        txtEndereco.clear();
        txtDataNascimento.clear();
    }

    public void setStage(Stage stageCliente) {
        this.stageCliente = stageCliente;
    }

    void ajustarElementosJanela() {
        configurarTabela();
        carregarClientesTabela();
    }

    private void configurarTabela() {
        colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        colTelefone.setCellValueFactory(cellData -> cellData.getValue().telefoneProperty());
        colEndereco.setCellValueFactory(cellData -> cellData.getValue().enderecoProperty());
        colDataNascimento.setCellValueFactory(cellData -> cellData.getValue().dataNascimentoProperty().asString());
    }

    public void carregarClientesTabela() {
        List<Cliente> clientes = clienteDAO.listarClientes();
        ObservableList<Cliente> listaClientes = FXCollections.observableArrayList(clientes);
        tabelaClientes.setItems(listaClientes);
    }
}
