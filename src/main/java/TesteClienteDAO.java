import dao.ClienteDAO;
import modelo.Cliente;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
public class TesteClienteDAO {
    public static void main(String[] args) {
        ClienteDAO dao = new ClienteDAO();
        try {
            // 1. Teste de Busca Segura por E-mail
            System.out.println("--- BUSCA POR E-MAIL ---");
            Optional<Cliente> c1 = dao.buscarPorEmail("ana.silva@mail.com");
            if (c1.isPresent()) {
                System.out.println("Cliente Encontrado: " + c1.get().getNome() + " (" + c1.get().getEmail() + ")");
            } else {
                System.out.println("Cliente não encontrado.");
            }
            // 2. Prova de Neutralização de SQL Injection
            System.out.println("\n--- TESTE DE SEGURANÇA (SQL INJECTION) ---");
            String emailMalicioso = "' OR '1'='1";
            Optional<Cliente> cAtaque = dao.buscarPorEmail(emailMalicioso);
            // O valor é buscado como texto literal, resultando em Optional vazio e impedindo o vazamento
            System.out.println("Resultado do Ataque: " + cAtaque.orElse(null)); // Imprime: null
            // 3. Teste de Listagem Geral
            System.out.println("\n--- LISTAGEM DE TODOS OS CLIENTES ---");
            List<Cliente> lista = dao.listarTodos();
            for (Cliente c : lista) {
                System.out.println("ID: " + c.getIdCliente() + " | Nome: " + c.getNome());
            }
        } catch (SQLException e) {
            System.err.println("Erro na operação de banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
