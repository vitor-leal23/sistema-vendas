package dao;
import Connection.ConnectionFactory;
import modelo.Fornecedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FornecedorDAO {
    private Connection getConnection() throws SQLException {
        return ConnectionFactory.getInstancia().getConnection();
    }

    // TODO 2.1: mapeamento centralizado
    private Fornecedor mapearFornecedor(ResultSet rs) throws SQLException {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setIdFornecedor(rs.getInt("id_fornecedor"));
        fornecedor.setNome(rs.getString("nome"));
        fornecedor.setTelefone(rs.getString("telefone"));
        return fornecedor;
    }

    // TODO 2.2: inserir com captura de chave gerada
    public void inserir(Fornecedor fornecedor) throws SQLException {
        String sql = "INSERT INTO fornecedor (nome, telefone) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getTelefone());
            stmt.executeUpdate();

            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) {
                    fornecedor.setIdFornecedor(chaves.getInt(1));
                }
            }
        }
    }

    // TODO 2.3: busca por ID
    public Optional<Fornecedor> buscarPorId(int idFornecedor) throws SQLException {
        String sql = "SELECT id_fornecedor, nome, telefone FROM fornecedor WHERE id_fornecedor = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFornecedor);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? Optional.of(mapearFornecedor(rs)) : Optional.empty();
            }
        }
    }

    // TODO 2.4: listagem geral
    public List<Fornecedor> listarTodos() throws SQLException {
        List<Fornecedor> fornecedores = new ArrayList<>();
        String sql = "SELECT id_fornecedor, nome, telefone FROM fornecedor";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                fornecedores.add(mapearFornecedor(rs));
            }
        }
        return fornecedores;
    }

    // TODO 2.5: atualização
    public void atualizar(Fornecedor fornecedor) throws SQLException {
        String sql = "UPDATE fornecedor SET nome = ?, telefone = ? WHERE id_fornecedor = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getTelefone());
            stmt.setInt(3, fornecedor.getIdFornecedor());
            stmt.executeUpdate();
        }
    }

    // TODO 2.6: exclusão
    public void remover(int idFornecedor) throws SQLException {
        String sql = "DELETE FROM fornecedor WHERE id_fornecedor = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFornecedor);
            stmt.executeUpdate();
        }
    }

    // TODO 3.1: consulta com INNER JOIN, parametrizada
    public List<String> listarProdutosPorFornecedor(int idFornecedor) throws SQLException {
        List<String> nomesProdutos = new ArrayList<>();
        String sql = "SELECT p.nome FROM produto p " +
                "INNER JOIN fornecedor f ON p.id_fornecedor = f.id_fornecedor " +
                "WHERE f.id_fornecedor = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFornecedor);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    nomesProdutos.add(rs.getString("nome"));
                }
            }
        }
        return nomesProdutos;
    }
}