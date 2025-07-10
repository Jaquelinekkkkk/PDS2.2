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
    private Button btnExcluir;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtEndereco;

    @FXML
    private TextField txtDataNascimento;

    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField txtSenha;

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

    //  Botão SALVAR
    @FXML
    void btnSalvarClick(ActionEvent event) {
        if (camposVazios()) {
            AlertaUtil.mostrarErro("Campos obrigatórios", "Preencha todos os campos antes de salvar.");
            return;
        }

        try {
            Date nascimentoFormatado = Date.valueOf(txtDataNascimento.getText());
            Cliente cliente = new Cliente(
                txtNome.getText(),
                txtTelefone.getText(),
                txtEndereco.getText(),
                nascimentoFormatado,
                txtLogin.getText(),
                txtSenha.getText()
            );

            clienteDAO.salvar(cliente);
            limparCampos();
            carregarClientesTabela();
            AlertaUtil.mostrarInformacao("Cliente cadastrado", "Cadastro realizado com sucesso!");
        } catch (IllegalArgumentException e) {
            AlertaUtil.mostrarErro("Data inválida", "Use o formato correto: YYYY-MM-DD.");
        } catch (SQLException e) {
            AlertaUtil.mostrarErro("Erro ao salvar", "Não foi possível inserir o cliente.");
            e.printStackTrace();
        }
    }

    //  Botão EXCLUIR
    @FXML
    void btnExcluirClick(ActionEvent event) {
        Cliente selecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            excluirCliente(selecionado.getId());
        } else {
            AlertaUtil.mostrarErro("Nenhum cliente selecionado", "Selecione um cliente na tabela.");
        }
    }

    // Verificação de campos obrigatórios
    private boolean camposVazios() {
        return txtNome.getText().isBlank()
            || txtTelefone.getText().isBlank()
            || txtEndereco.getText().isBlank()
            || txtDataNascimento.getText().isBlank()
            || txtLogin.getText().isBlank()
            || txtSenha.getText().isBlank();
    }

    //  Limpar os campos do formulário
    private void limparCampos() {
        txtNome.clear();
        txtTelefone.clear();
        txtEndereco.clear();
        txtDataNascimento.clear();
        txtLogin.clear();
        txtSenha.clear();
    }

    //  Vincular controller à stage se necessário
    public void setStage(Stage stageCliente) {
        this.stageCliente = stageCliente;
    }

    //️ Inicializar elementos da janela
    public void ajustarElementosJanela() {
        configurarTabela();
        carregarClientesTabela();
    }

    //  Configurar colunas da tabela
    private void configurarTabela() {
        colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        colTelefone.setCellValueFactory(cellData -> cellData.getValue().telefoneProperty());
        colEndereco.setCellValueFactory(cellData -> cellData.getValue().enderecoProperty());
        colDataNascimento.setCellValueFactory(cellData -> cellData.getValue().dataNascimentoProperty().asString());
    }

    // Carregar clientes na tabela
    public void carregarClientesTabela() {
        try {
            List<Cliente> clientes = clienteDAO.listarClientes();
            ObservableList<Cliente> lista = FXCollections.observableArrayList(clientes);
            tabelaClientes.setItems(lista);
        } catch (SQLException e) {
            AlertaUtil.mostrarErro("Erro ao carregar tabela", "Não foi possível buscar os clientes.");
            e.printStackTrace();
        }
    }

    //  Buscar cliente específico (se usar em outra tela)
    public Cliente selecionarClientePorId(int id) {
        try {
            return clienteDAO.selecionarCliente(id);
        } catch (SQLException e) {
            AlertaUtil.mostrarErro("Erro de busca", "Não foi possível localizar o cliente.");
            e.printStackTrace();
            return null;
        }
    }

    //  Remover cliente por ID
    public void excluirCliente(int id) {
        try {
            clienteDAO.excluir(id);
            AlertaUtil.mostrarInformacao("Cliente excluído", "Remoção realizada com sucesso.");
            carregarClientesTabela();
        } catch (SQLException e) {
            AlertaUtil.mostrarErro("Erro ao excluir", "Não foi possível excluir o cliente.");
            e.printStackTrace();
        }
    }
}