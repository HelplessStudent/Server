import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;

public class Server{
    public static int ID = 0;
    public static int login_student = 0;
    public static int login_teacher = 0;
    private static final int port = 888;
    //private static Object Server;

    public static void main(String[] args)
            throws Exception, SQLException
    {
        //Loading JDBC driver
        //DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);

        String url = "jdbc:mysql://localhost:3306/test";

        Connection conn = DriverManager.getConnection(url, "root", "");
        ServerSocket server = new ServerSocket(port);

        while (true) {
            Socket s = server.accept();
            System.out.println("Connection established");
            //sending nudes
            PrintStream ps
                    = new PrintStream(s.getOutputStream());
            //accepting nudes and reading and doing dance
            BufferedReader br
                    = new BufferedReader(
                    new InputStreamReader(
                            s.getInputStream()));

            String line = br.readLine();
            System.out.println("Message: " + line);
            String[] data;
            data = new String[5];

            if (line.equals("Register")) {
                while (true) {
                    ps.println("Enter your ID");
                    String ID = br.readLine();
                    System.out.println(ID);
                    if (!ID.isEmpty()) {
                        data[0] = ID;
                        break;
                    }
                    String line2 = br.readLine();
                    System.out.println(line2);
                }
                while (true) {
                    ps.println("Enter your Name");
                    String name = br.readLine();
                    System.out.println(name);
                    if (!name.isEmpty()) {
                        data[1] = name;
                        break;
                    }
                }
                while (true) {
                    ps.println("Enter your Lastname");
                    String l_name = br.readLine();
                    System.out.println(l_name);
                    if (!l_name.isEmpty()) {
                        data[2] = l_name;
                        break;
                    }
                }
                while (true) {
                    ps.println("Enter your password");
                    String password = br.readLine();
                    System.out.println(password);
                    if (!password.isEmpty()) {
                        data[3] = password;
                        break;
                    }
                }
                while (true) {
                    ps.println("Are u teacher or student, please write down your role");
                    String role = br.readLine();
                    System.out.println(role);
                    if (!role.isEmpty()) {
                        data[4] = role;
                        break;
                    }
                }
                Register(conn, data, ps);
                //skorej sa bude robit registracia


            }

        }
    }
    public static void Register(Connection conn, String[] more, PrintStream ps) throws Exception {

        String ID = more[0];
        String name = more[1];
        String lastname = more[2];
        String password = more[3];
        String role = more[4];
        Statement stm = null;
        String SQL = "insert into register(id, name,lastname, password, role) VALUES ('" + ID + "', '" + name + "','" + lastname + "','" + password + "','" + role + "')";
        try {
            stm = conn.createStatement();

            stm.executeUpdate(SQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ps.println("Successfully written into DB!");
    }
}
