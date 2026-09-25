package dao;
import Connection.ConnectionFactory;
import modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class ClienteDAO {
    // Obtém uma conexão isolada a partir da fábrica centralizada Singleton
    private Connection getConnection() throws SQLException {
        return ConnectionFactory.getInstancia().getConnection();
    }

    // Converte a linha atual do ResultSet em um objeto Cliente populado
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(rs.getInt("id_cliente"));
        cliente.setNome(rs.getString("nome"));
        cliente.setEmail(rs.getString("email"));

        // Mapeia corretamente colunas que podem vir nulas do PostgreSQL
        cliente.setPontosFidelidade(rs.getObject("pontos_fidelidade", Integer.class));

        return cliente;
    }

    // Insere um novo cliente e recupera a chave primária (SERIAL) gerada pelo PostgreSQL
    public void inserir(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (nome, email, pontos_fidelidade) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setObject(3, cliente.getPontosFidelidade()); // Aceita Integer ou null diretamente
            stmt.executeUpdate(); // Executa a inserção

            // Recupera a chave gerada pelo PostgreSQL
            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) {
                    cliente.setIdCliente(chaves.getInt(1)); // Atribui o ID gerado ao objeto
                }
            }
        }
    }

    // Atualiza os dados de um cliente existente
    public void atualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET nome = ?, email = ?, pontos_fidelidade = ? WHERE id_cliente = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setObject(3, cliente.getPontosFidelidade()); // Aceita Integer ou null diretamente
            stmt.setInt(4, cliente.getIdCliente()); // Filtro WHERE
            stmt.executeUpdate();
        }
    }

    // Remove um cliente pelo identificador
    public void remover(int idCliente) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            stmt.executeUpdate();
        }
    }

    // Busca parametrizada retornando Optional
    public Optional<Cliente> buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT id_cliente, nome, email, pontos_fidelidade FROM cliente WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Preenche o primeiro placeholder (?) com o parâmetro recebido
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Retorna um Optional contendo o objeto mapeado
                    return Optional.of(mapearCliente(rs));
                }
            }
        }
        return Optional.empty(); // Retorna Optional vazio caso não encontre
    }

    // Busca por ID retornando Optional<Cliente>
    public Optional<Cliente> buscarPorId(int idCliente) throws SQLException {
        String sql = "SELECT id_cliente, nome, email, pontos_fidelidade FROM cliente WHERE id_cliente = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearCliente(rs));
                }
            }
        }
        return Optional.empty();
    }

    // Busca por nome parcial com LIKE parametrizado
    public List<Cliente> buscarPorNomeParcial(String trecho) throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id_cliente, nome, email, pontos_fidelidade FROM cliente WHERE nome LIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // O % vai no valor do parâmetro, mantendo o SQL seguro!
            stmt.setString(1, "%" + trecho + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    clientes.add(mapearCliente(rs));
                }
            }
        }
        return clientes;
    }

    // Listagem geral reaproveitando o método mapearCliente
    public List<Cliente> listarTodos() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id_cliente, nome, email, pontos_fidelidade FROM cliente";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                clientes.add(mapearCliente(rs)); // Adiciona cliente mapeado
            }
        }
        return clientes;
    }
}
