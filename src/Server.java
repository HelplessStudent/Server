import javax.swing.plaf.nimbus.State;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


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
                    System.out.println(l_name);
                    if (!l_name.isEmpty()) {
                        logg[0] = l_name;
                        break;
                    }
                }
                while (true)
                {
                    ps.println("Please enter you password");
                    String lpassword = br.readLine();
                    System.out.println(lpassword);
                    if (!lpassword.isEmpty()) {
                        logg[1] = lpassword;
                        break;
                    }
                }
                String[] check = new String[5];
                check = (String[]) Login(conn,logg);
                /*if (check[0].isEmpty()) {
                    ps.println(Arrays.toString(check));
                }*/
                if (check[2].equals("student"))
                {
                    login_student = 1;
                    ps.println("Boli ste prihlaseny ako student" + login_student);
                }
                else if(check[2].equals("teacher"))
                {
                    login_teacher = 1;
                    ps.println("Boli ste prihlaseny ako ucitel");
                }
            }
            if (login_student == 1) {
                //System.out.println("Error code here!");
                //ps.println("What do u want to do? 1. Make a Schedule, 2. Load your schedule");
                String student;
                student = br.readLine();
                //System.out.println("Message: " + student);
                Integer[] load = new Integer[3];
                String[] schedule;
                schedule = new String[4];
                if (student.equals("1")) //Make ur schedule and registration into it
                {
                    while (true) {
                        ps.println("Enter your ID");
                        String ID = br.readLine();
                        System.out.println("Tu je ID: " + ID);
                        if (!ID.isEmpty()) {
                            schedule[0] = ID;
                            break;
                        }
                    }
                    while (true) {
                        ps.println("Enter name of the teacher:");
                        String t_name = br.readLine();
                        System.out.println("Tu je meno ucitela" + t_name);
                        if (!t_name.isEmpty()) {
                            schedule[1] = t_name;
                            break;
                        }
                    }
                    while (true) {
                        ps.println("Enter your day: pondelok, utorok, piatok");
                        String day = br.readLine();
                        System.out.println("Tu je den, ktory ste si vybrali" + day);
                        if (!day.isEmpty()) {
                            schedule[2] = day;
                            break;
                        }
                    }
                    while (true) {
                        ps.println("Enter time: 10-12:00 (10 min max)");
                        String time = br.readLine();
                        System.out.println("Tu je vas cas" + time);
                        if (!time.isEmpty()) {
                            schedule[3] = time;
                            break;
                        }
                    }
                    MakeSchedule(conn, schedule, ps);
                }
                else if (student.equals("2")) {
                    while (true) {
                        ps.println("Enter ur ID under u created ur Schedule registration");
                        Integer id = Integer.valueOf(br.readLine());
                        System.out.println(id);
                        load[0] = id;
                        break;
                    }
                    String[] s_check = new String[5];
                    s_check = (String[]) S_LoadSchedule(conn,load);
                    System.out.println("Teacher's name: " + s_check[1] );
                    System.out.println("Day: " + s_check[2] );
                    System.out.println("Time: " + s_check[3] );

                    ps.println("Teacher's name: " + s_check[1] );
                    ps.println("\nDay: " + s_check[2] );
                    ps.println("Time: " + s_check[3] );
                }

            }
            if (login_teacher == 1){
                String readl = br.readLine();
                String[] t_schedule = new String [2];
                if (readl.equals("1"))
                {
                    while (true){
                        ps.println("Please enter your name: ");
                        String t_name = br.readLine();
                        if (!t_name.isEmpty()) {
                            t_schedule[0] = t_name;
                            break;
                        }
                    }
                    String[] t_check = new String[50];
                    t_check = (String[]) T_LoadSchedule(conn, t_schedule);

                    for (int i = 0; i <= 10; i++){
                        ps.println("Message: " + t_check[i]);
                    }
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
    //Fix, make to Login by ID, because we cannot have more people with same ID, but we can more people with same name
    public static Serializable Login(Connection conn, String[] lmore) throws Exception{
        String name = lmore[0];
        String paswd = lmore[1];
        String[] drive = new String[3];

        String query = "SELECT * from register where name ='"+name+"'";

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

        if (drive[0] != null && drive[1].equals(paswd))
        {
            return drive;
        }
        else{
            //System.out.println("Fuck off mate");
            return "Nope";
        }
    }

    public static void MakeSchedule(Connection conn, String[] consulting, PrintStream ps) throws Exception{
        String ID = consulting[0];
        String name = consulting[1];
        String day = consulting[2];
        String time = consulting[3];
        //Statement st = null;
        String SQL = "insert into t_consultation(id, name, day ,time ) VALUES ('" + ID + "','"+ name +"','" + day + "','" + time + "')";
        try {

            Statement st = conn.createStatement();
            st.executeUpdate(SQL);


        } catch (Exception e) {
            e.printStackTrace();
        }
        ps.println("Boli ste pridany na cakaciu listinu");
    }

    public static Serializable S_LoadSchedule(Connection conn, Integer[] load) throws Exception {
        Integer s_id = load[0]; //pripravil som si ID

        String[] drive = new String[4];
        //Integer[] drive1 = new Integer[3];

        String query = "SELECT * from t_consultation where id ='"+s_id+"'";

        try
        {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next())
            {
                int id = rs.getInt("id");
                drive[1] = rs.getString("name");
                drive[2] = rs.getString("day");
                drive[3] = rs.getString("time");
            }
        } catch (Exception e) {System.err.println("Got an exception! ");
            System.err.println(e.getMessage());}

        if (( s_id != null))
            return drive;
        else{
            //System.out.println("Fuck off mate");
            return "Nope";
        }
    }

    public static Serializable T_LoadSchedule(Connection conn, String[] load) throws IOException{
        String name = load[0];
        String[] drive = new String[50];

        String query = "SELECT * from t_consultation where name ='"+name+"'";

        try
        {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            int up = 0;
            while (rs.next())
            {
                int id = rs.getInt("id");
                String s_name = rs.getString("name");
                String s_day = rs.getString("day");
                String s_time = rs.getString("time");
                drive[up] =  ("id: "+id+"\n name: " + s_name+"\n day: "+s_day+" \n time: "+s_time);
                up++;
            }
        } catch (Exception e) {System.err.println("Got an exception! ");
            System.err.println(e.getMessage());}
        return drive;
    }

}


