package model;

import dal.ConexaoBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public void cadastrarProduto(Produto produto) throws SQLException {
        String sql = "INSERT INTO produto (descricao, valor, quantidade_estoque) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getDescricao());
            stmt.setBigDecimal(2, produto.getValor());
            stmt.setInt(3, produto.getQuantidadeEstoque());

            stmt.executeUpdate();
        }
    }

    public List<Produto> listarProdutos() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setValor(rs.getBigDecimal("valor"));
                produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                produtos.add(produto);
            }
        }

        return produtos;
    }

    public void excluirProduto(int produtoId) throws SQLException {
        String sql = "DELETE FROM produto WHERE id = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, produtoId);
            stmt.executeUpdate();
        }
    }

    public void removerDoEstoque(int produtoId) throws SQLException {
    try (Connection conn = ConexaoBD.conectar()) {

        // Diminui o estoque
        String sqlUpdate = "UPDATE produto SET quantidade_estoque = quantidade_estoque - 1 WHERE id = ?";
        try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {
            stmtUpdate.setInt(1, produtoId);
            stmtUpdate.executeUpdate();
        }

        // Verifica o estoque atual
        String checkSql = "SELECT quantidade_estoque FROM produto WHERE id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             ResultSet rs = checkStmt.executeQuery()) {

            checkStmt.setInt(1, produtoId);
            if (rs.next() && rs.getInt("quantidade_estoque") <= 0) {
                // Apaga produto se estoque zerado
                String deleteSql = "DELETE FROM produto WHERE id = ?";
                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                    deleteStmt.setInt(1, produtoId);
                    deleteStmt.executeUpdate();
                }
            }
        }
    }
}
}