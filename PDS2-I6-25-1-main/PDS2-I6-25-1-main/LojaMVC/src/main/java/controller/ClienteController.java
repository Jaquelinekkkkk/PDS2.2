package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
    private Cliente clienteSelecionado;

    @FXML 
    private Button btnSalvar;
    
    @FXML 
    private Button btnExcluir;
    
    @FXML 
    private Button btnFechar;

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
    

    // Botão SALVAR (inserir ou alterar)
    @FXML
    void btnSalvarClick(ActionEvent event) {
        if (camposVazios()) {
            AlertaUtil.mostrarErro("Campos obrigatórios", "Preencha todos os campos antes de salvar.");
            return;
        }

        if (!dataFormatoValido(txtDataNascimento.getText())) {
            AlertaUtil.mostrarErro("Data inválida", "Use o formato correto: YYYY-MM-DD.");
            return;
        }

        try {
            Date nascimentoFormatado = Date.valueOf(txtDataNascimento.getText());

            if (clienteSelecionado == null) {
                // Novo cliente
                Cliente cliente = new Cliente(
                        txtNome.getText(),
                        txtTelefone.getText(),
                        txtEndereco.getText(),
                        nascimentoFormatado,
                        txtLogin.getText(),
                        txtSenha.getText()
                );

                clienteDAO.salvar(cliente);
                AlertaUtil.mostrarInformacao("Cliente cadastrado", "Cadastro realizado com sucesso!");
            } else {
                // Atualizar cliente
                clienteSelecionado.setNome(txtNome.getText());
                clienteSelecionado.setTelefone(txtTelefone.getText());
                clienteSelecionado.setEndereco(txtEndereco.getText());
                clienteSelecionado.setDataNascimento(nascimentoFormatado);
                clienteSelecionado.setLogin(txtLogin.getText());
                clienteSelecionado.setSenha(txtSenha.getText());

                clienteDAO.alterar(clienteSelecionado);
                AlertaUtil.mostrarInformacao("Cliente alterado", "Dados atualizados com sucesso!");
            }

            limparCampos();
            carregarClientesTabela();
            clienteSelecionado = null;

        } catch (SQLException e) {
            AlertaUtil.mostrarErro("Erro ao salvar", "Não foi possível inserir/alterar o cliente.");
            e.printStackTrace();
        }
    }

    // Botão EXCLUIR
    @FXML
    void btnExcluirClick(ActionEvent event) {
        Cliente selecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            Optional<ButtonType> resultado = AlertaUtil.mostrarConfirmacao("Confirmação",
                    "Tem certeza que deseja excluir este cliente?");
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                excluirCliente(selecionado.getId());
                limparCampos();
                clienteSelecionado = null;
            }
        } else {
            AlertaUtil.mostrarErro("Nenhum cliente selecionado", "Selecione um cliente na tabela.");
        }
    }

    // Botão FECHAR
    @FXML
    void btnFecharClick(ActionEvent event) {
        if (stageCliente != null) {
            stageCliente.close();
        } else {
            // Alternativa se stageCliente não foi setado manualmente
            Stage stage = (Stage) btnFechar.getScene().getWindow();
            stage.close();
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

    // Verifica se a data está no formato correto YYYY-MM-DD
    private boolean dataFormatoValido(String data) {
        return data.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    // Limpar os campos do formulário
    private void limparCampos() {
        txtNome.clear();
        txtTelefone.clear();
        txtEndereco.clear();
        txtDataNascimento.clear();
        txtLogin.clear();
        txtSenha.clear();
        clienteSelecionado = null;
    }

    // Vincular controller à stage (caso deseje controlar manualmente)
    public void setStage(Stage stageCliente) {
        this.stageCliente = stageCliente;
    }

    // Inicializar elementos da janela
    public void ajustarElementosJanela() {
        configurarTabela();
        carregarClientesTabela();

        tabelaClientes.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        preencherCamposParaEdicao(newSelection);
                    }
                }
        );
    }

    // Configurar colunas da tabela
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

    // Preencher campos para edição
    private void preencherCamposParaEdicao(Cliente cliente) {
        this.clienteSelecionado = cliente;
        txtNome.setText(cliente.getNome());
        txtTelefone.setText(cliente.getTelefone());
        txtEndereco.setText(cliente.getEndereco());
        txtDataNascimento.setText(cliente.getDataNascimento().toString());
        txtLogin.setText(cliente.getLogin());
        txtSenha.setText(cliente.getSenha());
    }

    // Buscar cliente específico
    public Cliente selecionarClientePorId(int id) {
        try {
            return clienteDAO.selecionarCliente(id);
        } catch (SQLException e) {
            AlertaUtil.mostrarErro("Erro de busca", "Não foi possível localizar o cliente.");
            e.printStackTrace();
            return null;
        }
    }

    // Remover cliente por ID
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