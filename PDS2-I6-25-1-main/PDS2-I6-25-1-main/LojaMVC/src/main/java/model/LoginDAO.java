package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO extends GenericDAO {

    public Boolean bancoOnline() {
        Connection con = conectarDAO();
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                // silenciosamente ignorado
            }
            return true;
        } else {
            return false;
        }
    }

    public Usuario autenticar(String login, String senha) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ?";
        Usuario usuario = null;

        Connection con = conectarDAO();
        if (con != null) {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setFone(rs.getString("fone"));
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setPerfil(rs.getString("perfil"));
                usuario.setEmail(rs.getString("email"));
                usuario.setAniversario(rs.getDate("aniversario").toLocalDate());
            }

            rs.close();
            stmt.close();
            con.close();
        }

        return usuario;
    }

    // 🔐 Autentica clientes
    public Cliente autenticarCliente(String login, String senha) throws SQLException {
        Cliente cliente = null;
        String sql = "SELECT * FROM cliente WHERE login = ? AND senha = ?";

        Connection con = conectarDAO();
        if (con != null) {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, login.trim());
            stmt.setString(2, senha.trim());
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
            con.close();
        }

        return cliente;
    }
}