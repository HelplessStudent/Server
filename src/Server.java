import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import java.util.Arrays;

public class Server{
    public static int ID = 0;
    public static int login_student = 0;
    public static int login_teacher = 0;
    private static final int port = 888;
    //private static Object Server;

    public static void main(String[] args)
            throws Exception {
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

            //ps.println("Please type what action do u want to do: Register or Login");

            String line = br.readLine();
            System.out.println("Message: " + line);
            String[] regg;
            regg = new String[5];
            String[] logg;
            logg = new String[2];

            if (line.equals("Register") || line.equals("register")) {
                while (true) {
                    ps.println("Enter your ID");
                    String ID = br.readLine();
                    System.out.println(ID);
                    if (!ID.isEmpty()) {
                        regg[0] = ID;
                        break;
                    }
                }
                while (true) {
                    ps.println("Enter your Name");
                    String name = br.readLine();
                    System.out.println(name);
                    if (!name.isEmpty()) {
                        regg[1] = name;
                        break;
                    }
                }
                while (true) {
                    ps.println("Enter your Lastname");
                    String l_name = br.readLine();
                    System.out.println(l_name);
                    if (!l_name.isEmpty()) {
                        regg[2] = l_name;
                        break;
                    }
                }
                while (true) {
                    ps.println("Enter your password");
                    String password = br.readLine();
                    System.out.println(password);
                    if (!password.isEmpty()) {
                        regg[3] = password;
                        break;
                    }
                }
                while (true) {
                    ps.println("Are u teacher or student, please write down your role");
                    String role = br.readLine();
                    System.out.println(role);
                    if (!role.isEmpty()) {
                        regg[4] = role;
                        break;
                    }
                }
                Register(conn, regg, ps);
            }
            else if (line.equals("Login") || line.equals("login")){
                while (true)
                {
                    ps.println("Please enter your name");
                    String l_name = br.readLine();
                    System.out.println("lid");
                    if (!l_name.isEmpty()) {
                        logg[0] = l_name;
                        break;
                    }
                }
                while (true)
                {
                    ps.println("Please enter you password");
                    String lpassword = br.readLine();
                    System.out.println("lid");
                    if (!lpassword.isEmpty()) {
                        logg[1] = lpassword;
                        break;
                    }
                }
               String[] check;
                check = (String[]) Login(conn,logg);
                /*if (check[0].isEmpty()) {
                    ps.println(Arrays.toString(check));
                }*/
                if (check[2].equals("student"))
                {
                    login_student = 1;
                    ps.println("Boli ste prihlaseny ako student");
                }
                else if(check[2].equals("teacher"))
                {
                    login_teacher = 1;
                    ps.println("Boli ste prihlaseny ako ucitel");
                }
            }

        }
    }
    public static void Register(Connection conn, String[] more, PrintStream ps) throws Exception {

        String ID = more[0];
        String name = more[1];
        String lastname = more[2];
        String password = more[3];
        String role = more[4];
        Statement st = null;
        String SQL = "insert into register(id, name,lastname, password, role) VALUES ('" + ID + "', '" + name + "','" + lastname + "','" + password + "','" + role + "')";
        try {
            st = conn.createStatement();

            st.executeUpdate(SQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ps.println("Successfully written into DB!");
    }

    public static Serializable Login(Connection conn, String[] lmore) throws Exception{
        String name = lmore[0];
        String paswd = lmore[1];
        String[] drive = new String[3];

        String query = "SELECT * from register";

        try
        {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next())
            {
                int id = rs.getInt("id");
                drive[0] = rs.getString("name");
                drive[1] = rs.getString("password");
                drive[2] = rs.getString("role");
            }
        } catch (Exception e) {System.err.println("Got an exception! ");
            System.err.println(e.getMessage());}

        if (drive[0].equals(name) && drive[1].equals(paswd))
        {
            return drive;
        }
        else{
            //System.out.println("Fuck off mate");
            return "Nope";
        }
    }
}
