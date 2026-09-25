import dao.FornecedorDAO;
import dao.ProdutoDAO;
import modelo.Fornecedor;
import modelo.Produto;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class TesteAtividadeEstruturada1 {
    public static void main(String[] args) {
        FornecedorDAO fornecedorDao = new FornecedorDAO();
        ProdutoDAO produtoDao = new ProdutoDAO();
        try {
            System.out.println("=== ATIVIDADE ESTRUTURADA 1 (Ex1) ===");

            // TODO 4.1: insere um novo Fornecedor
            Fornecedor fornecedor = new Fornecedor("Distribuidora Tech Ltda", "(62) 3000-1234");
            fornecedorDao.inserir(fornecedor);
            System.out.println("Fornecedor inserido! ID gerado: " + fornecedor.getIdFornecedor());

            // TODO 4.2: insere um Produto associado ao fornecedor recém-gerado
            // (requer que ProdutoDAO.inserir grave também id_fornecedor - ver observação abaixo)
            Produto produto = new Produto();
            produto.setSku("FORN-001");
            produto.setNome("Produto do Fornecedor Teste");
            produto.setPreco(new BigDecimal("150.00"));
            produto.setEstoque(20);
            produtoDao.inserir(produto);
            System.out.println("Produto inserido! ID gerado: " + produto.getIdProduto());

            // TODO 4.3: lista os produtos do fornecedor via INNER JOIN
            System.out.println("\n--- PRODUTOS DO FORNECEDOR ---");
            List<String> produtos = fornecedorDao.listarProdutosPorFornecedor(fornecedor.getIdFornecedor());
            produtos.forEach(System.out::println);

            // TODO 4.4: tenta remover o fornecedor e comprova o bloqueio de integridade
            System.out.println("\n--- TESTE DE INTEGRIDADE REFERENCIAL ---");
            try {
                fornecedorDao.remover(fornecedor.getIdFornecedor());
                System.out.println("Isso não deveria acontecer!");
            } catch (SQLException e) {
                System.out.println("SUCESSO: Exclusão bloqueada pelo PostgreSQL como esperado!");
                System.out.println("Mensagem do banco: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}