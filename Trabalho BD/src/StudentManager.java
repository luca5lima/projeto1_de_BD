import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.ResultSet;

public class StudentManager {
    public Connection conexao = null;
    public PreparedStatement pstm = null;
    public Student student = new Student(0,"","");
    public void menu() {
        int op, id;
        String nome, email;
        Scanner scan = new Scanner(System.in);
        System.out.println("Ola usuario! \n");
        do {
            System.out.println("------------------------------------------------------------------");
            System.out.println("Digite a opção desejada: \n1- inserir aluno\n2- atualizar dados\n3- remover estudante\n4- listar estudantes\n0- sair");
            op = scan.nextInt();

            switch (op) {
                case 1:
                    System.out.println("Digite a matricula do estudante: ");
                    id = scan.nextInt();
                    scan.nextLine();

                    System.out.println("Digite o nome do estudante: ");
                    nome = scan.nextLine();
                    scan.nextLine();

                    System.out.println("Digite o email do estudante: ");
                    email = scan.nextLine();

                    student=new Student(id, nome, email);
                    addStudent(student);
                    System.out.println("Estudante inserido!");
                    break;
                case 2:
                    System.out.println("Digite a matricula do estudante que deseja atualizar: ");
                    int idAux = scan.nextInt();
                    System.out.println("Digite os novos dados do estudante!\n\n ");
                    System.out.println("Nome: ");
                    nome = scan.nextLine();
                    System.out.println("Email: ");
                    email = scan.nextLine();
                    student = new Student(idAux, nome, email);
                    updateStudent(student);
                    System.out.println("Estudante atualizado!");
                    break;
                case 3:
                    System.out.println("Digite a matricula que deseja remover: ");
                    student.setId(scan.nextInt());
                    delete(student);
                    System.out.println("Estudante deletado!");
                    break;
                case 4:
                    System.out.println("LISTA DE ESTUDANTES: \n");
                    for (Student e : getStudents()) {
                        System.out.println("Matricula: " + e.getId());
                        System.out.println("Nome: " + e.getNome());
                        System.out.println("Email: " + e.getEmail());
                        System.out.println("\n");
                    }
                    break;
                case 0:
                    System.out.println("ENCERRANDO PROGRAMA...");
                    break;
                default:
                    System.out.println("OPCAO INVALIDA!");
                    break;
            }

                System.out.println("Pressione Enter para continuar...");
                scan.nextLine();
                scan.nextLine(); // Pausa até que o usuário pressione Enter
                System.out.println();

        } while (op != 0);
    }

    public void addStudent(Student student){
        String sql = "INSERT INTO student (id,name,email) VALUES (?,?,?)";
        try {
            conexao = Conexao.conector();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, student.getId());
            pstm.setString(2, student.getNome());
            pstm.setString(3, student.getEmail());


            pstm.execute();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conexao != null) {
                    conexao.close();
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
    public void updateStudent(Student student){
        String sql = "UPDATE student SET name=?,email=? WHERE id=?";

        try {
            conexao = Conexao.conector();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, student.getNome());
            pstm.setString(2, student.getEmail());
            pstm.setInt(3, student.getId());

            pstm.execute();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conexao != null) {
                    conexao.close();
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
    public void delete(Student student) {
        String sql = "DELETE FROM student WHERE id=?";
        Connection conexao = null;
        PreparedStatement pstm = null;

        try {
            conexao = Conexao.conector();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, student.getId());

            pstm.execute();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conexao != null) {
                    conexao.close();
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public List<Student> getStudents(){
        String sql = "SELECT * FROM student";
        List<Student> student = new ArrayList<Student>();
        ResultSet rs=null;
        try {
            conexao = Conexao.conector();
            pstm = conexao.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Student est=new Student(0,"","");
                est.setId(rs.getInt("id"));
                est.setNome(rs.getString("name"));
                est.setEmail(rs.getString("email"));

                student.add(est);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conexao != null) {
                    conexao.close();
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return student;
    }
}
