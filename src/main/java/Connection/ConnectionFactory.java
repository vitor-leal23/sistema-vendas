package Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionFactory {
    // Constantes com as credenciais centralizadas do PostgreSQL
    private static final String URL = "jdbc:postgresql://localhost:5432/sistema_vendas";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "240322";

    // Atributo estático que armazenará a única instância da fábrica
    private static ConnectionFactory instancia;
    // Construtor privado: impede o uso de "new ConnectionFactory()" fora desta classe
    private ConnectionFactory() {
    }
    // Ponto de acesso global para obter a instância única
    public static ConnectionFactory getInstancia() {
        if (instancia == null) {
            instancia = new ConnectionFactory();
        }
        return instancia;
    }
    // Retorna uma nova conexão ativa com o banco PostgreSQL
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

}