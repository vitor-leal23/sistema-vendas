import dao.ClienteDAO;
import modelo.Cliente;
import java.sql.SQLException;
import java.util.List;
public class TesteClienteDAO {
    public static void main(String[] args) {
        ClienteDAO dao = new ClienteDAO();
        try {
            // 1. Teste de Busca Segura por E-mail
            System.out.println("--- BUSCA POR E-MAIL ---");
            Cliente c1 = dao.buscarPorEmail("ana.silva@mail.com");
            if (c1 != null) {
                System.out.println("Cliente Encontrado: " + c1.getNome() + " (" + c1.getEmail() +
                        ")");
            } else {
                System.out.println("Cliente não encontrado.");
            }
            // 2. Prova de Neutralização de SQL Injection
            System.out.println("
                            --- TESTE DE SEGURANÇA (SQL INJECTION) ---");
                    String emailMalicioso = "' OR '1'='1";
            Cliente cAtaque = dao.buscarPorEmail(emailMalicioso);
            // O valor é buscado como texto literal, resultando em null e impedindo o vazamento
            System.out.println("Resultado do Ataque: " + cAtaque); // Imprime: null
            // 3. Teste de Listagem Geral
            System.out.println("
                            --- LISTAGEM DE TODOS OS CLIENTES ---");
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
