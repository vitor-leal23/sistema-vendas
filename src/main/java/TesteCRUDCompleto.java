import dao.ClienteDAO;
import modelo.Cliente;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TesteCRUDCompleto {
    public static void main(String[] args) {
        ClienteDAO dao = new ClienteDAO();
        try {
            // 1. CREATE: Inserir novo cliente
            System.out.println("--- 1. INSERINDO NOVO CLIENTE ---");
            Cliente novo = new Cliente();
            novo.setNome("Fernanda Lima");
            novo.setEmail("fernanda.lima@mail.com");
            novo.setPontosFidelidade(50);
            dao.inserir(novo);
            System.out.println("Cliente inserido com sucesso! ID gerado: " + novo.getIdCliente());

            // 2. READ: Buscar por ID recém-gerado
            System.out.println("\n--- 2. BUSCANDO POR ID ---");
            Optional<Cliente> opt = dao.buscarPorId(novo.getIdCliente());
            opt.ifPresent(c -> System.out.println("Encontrado: " + c.getNome() + " | Email: " + c.getEmail()));

            // 3. UPDATE: Alterar dados do cliente
            System.out.println("\n--- 3. ATUALIZANDO CLIENTE ---");
            novo.setNome("Fernanda Lima Silva");
            novo.setPontosFidelidade(100);
            dao.atualizar(novo);
            System.out.println("Dados atualizados com sucesso!");

            // 4. READ: Busca por trecho de nome
            System.out.println("\n--- 4. BUSCA PARCIAL POR NOME ---");
            List<Cliente> lista = dao.buscarPorNomeParcial("Lima");
            for (Cliente c : lista) {
                System.out.println("ID: " + c.getIdCliente() + " | Nome: " + c.getNome());
            }

            // 5. DELETE: Remover o registro de teste
            System.out.println("\n--- 5. REMOVENDO CLIENTE ---");
            dao.remover(novo.getIdCliente());
            System.out.println("Cliente ID " + novo.getIdCliente() + " removido com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro na operação CRUD: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
