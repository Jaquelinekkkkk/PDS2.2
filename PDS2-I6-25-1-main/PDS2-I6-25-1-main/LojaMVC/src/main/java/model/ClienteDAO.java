package model;

import dal.ConexaoBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void inserirCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, telefone, endereco, data_nascimento) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getEndereco());
            stmt.setDate(4, cliente.getDataNascimento());

            stmt.executeUpdate();
            System.out.println(" Cliente inserido com sucesso!");

        } catch (SQLException e) {
            System.err.println(" Erro ao inserir cliente: " + e.getMessage());
        }
    }

    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setDataNascimento(rs.getDate("data_nascimento"));

                clientes.add(cliente);
            }

        } catch (SQLException e) {
            System.err.println(" Erro ao listar clientes: " + e.getMessage());
        }

        return clientes;
    }
}