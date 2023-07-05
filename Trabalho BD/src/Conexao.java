import java.sql.*;

public class Conexao {
    //metodo responsavel por estabelecer a cpnecção com o banco
    public static Connection conector() {
        java.sql.Connection conexao = null;
        // alinha abaixo chama o driver
        String driver = "com.mysql.cj.jdbc.Driver";
        // armazenando informações referente ao banco
        String url = "jdbc:mysql://localhost:3306/school";
        String user = "root";
        String password = "nel01102003";
        // estabelecendo a conexão com o banco
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            // linha de apoio para saber o problema
            System.out.println(e);
            return null;
        }
    }
}