package model;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClienteDAO extends GenericDAO {

    //  Salvar novo cliente
    public void salvar(Cliente cliente) throws SQLException {
        String insert = "INSERT INTO cliente (nome, telefone, endereco, data_nascimento, login, senha) VALUES (?, ?, ?, ?, ?, ?)";
        save(insert,
            cliente.getNome(),
            cliente.getTelefone(),
            cliente.getEndereco(),
            cliente.getDataNascimento(),
            cliente.getLogin(),
            cliente.getSenha()
        );
    }

    //  Alterar cliente existente
    public void alterar(Cliente cliente) throws SQLException {
        String update = "UPDATE cliente SET nome = ?, telefone = ?, endereco = ?, data_nascimento = ?, login = ?, senha = ? WHERE id = ?";
        update(update,
            cliente.getNome(),
            cliente.getTelefone(),
            cliente.getEndereco(),
            cliente.getDataNascimento(),
            cliente.getLogin(),
            cliente.getSenha(),
            cliente.getId()
        );
    }

    //  Excluir cliente pelo ID
    public void excluir(int id) throws SQLException {
        String delete = "DELETE FROM cliente WHERE id = ?";
        delete(delete, id);
    }

    //  Listar todos os clientes
    public ObservableList<Cliente> listarClientes() throws SQLException {
        ObservableList<Cliente> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM cliente";

        PreparedStatement stmt = conectarDAO().prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Cliente cliente = new Cliente();
            cliente.setId(rs.getInt("id"));
            cliente.setNome(rs.getString("nome"));
            cliente.setTelefone(rs.getString("telefone"));
            cliente.setEndereco(rs.getString("endereco"));
            cliente.setDataNascimento(rs.getDate("data_nascimento"));
            cliente.setLogin(rs.getString("login"));
            cliente.setSenha(rs.getString("senha"));
            lista.add(cliente);
        }

        rs.close();
        stmt.close();
        conectarDAO().close();

        return lista;
    }

    // Buscar cliente por ID
    public Cliente selecionarCliente(int id) throws SQLException {
        Cliente cliente = null;
        String sql = "SELECT * FROM cliente WHERE id = ?";

        PreparedStatement stmt = conectarDAO().prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            cliente = new Cliente();
            cliente.setId(rs.getInt("id"));
            cliente.setNome(rs.getString("nome"));
            cliente.setTelefone(rs.getString("telefone"));
            cliente.setEndereco(rs.getString("endereco"));
            cliente.setDataNascimento(rs.getDate("data_nascimento"));
            cliente.setLogin(rs.getString("login"));
            cliente.setSenha(rs.getString("senha"));
        }

        rs.close();
        stmt.close();
        conectarDAO().close();

        return cliente;
    }

    //  Autenticar cliente por login e senha
    public Cliente autenticarCliente(String login, String senha) throws SQLException {
        Cliente cliente = null;
        String sql = "SELECT * FROM cliente WHERE login = ? AND senha = ?";

        PreparedStatement stmt = conectarDAO().prepareStatement(sql);
        stmt.setString(1, login);
        stmt.setString(2, senha);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            cliente = new Cliente();
            cliente.setId(rs.getInt("id"));
            cliente.setNome(rs.getString("nome"));
            cliente.setTelefone(rs.getString("telefone"));
            cliente.setEndereco(rs.getString("endereco"));
            cliente.setDataNascimento(rs.getDate("data_nascimento"));
            cliente.setLogin(rs.getString("login"));
            cliente.setSenha(rs.getString("senha"));
        }

        rs.close();
        stmt.close();
        conectarDAO().close();

        return cliente;
    }
}