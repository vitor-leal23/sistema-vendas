package dao;
import Connection.ConnectionFactory;
import modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class ClienteDAO {
    // Obtém uma conexão isolada a partir da fábrica centralizada Singleton
    private Connection getConnection() throws SQLException {
        return ConnectionFactory.getInstancia().getConnection();
    }
    public Cliente buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT id_cliente, nome, email FROM cliente WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Preenche o primeiro placeholder (?) com o parâmetro recebido
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("id_cliente"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setEmail(rs.getString("email"));
                    return cliente; // Retorna o objeto instanciado e populado
                }
            }
        }
        return null; // Retorna null caso nenhum cliente seja encontrado
    }
    public List<Cliente> listarTodos() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id_cliente, nome, email FROM cliente";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                clientes.add(cliente); // Adiciona cada cliente na coleção
            }
        }
        return clientes;
    }
}