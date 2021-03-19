import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

class ConnectDB {
    // Create variables for the connection.
    private final String connectionUrl = "jdbc:mysql://localhost:3306/";
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private String SQL;

    ConnectDB(){
        //connect to server localhost
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection(connectionUrl, "root", "");
            System.out.println("Yuhuuuu... berhasil membuat koneksi ke server :)");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Gagal membuat koneksi ke server :')");
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //connect to database `db_mobil`
        if(con != null){
            try {
                SQL = "USE db_mobil";
                stmt = con.createStatement();
                this.rs = stmt.executeQuery(SQL);
                System.out.println("Berhasil terkoneksi ke database db_mobil! :D");
            }
            catch (SQLException ex) {
                System.out.println("Database db_mobil tidak ditemukan, mari kita buat dulu! :)");
                try {
                    SQL = "CREATE DATABASE db_mobil";
                    stmt = con.createStatement();
                    stmt.executeUpdate(SQL);

                    SQL = "USE db_mobil";
                    stmt = con.createStatement();
                    this.rs = stmt.executeQuery(SQL);

                    SQL = "CREATE TABLE mobil( " +
                            "id int NOT NULL auto_increment, " +
                            "merk varchar(255) NOT NULL, " +
                            "plat varchar(255) NOT NULL, " +
                            "warna varchar(255) NOT NULL, " +
                            "jenis varchar(255) NOT NULL, " +
                            "PRIMARY KEY (id))";
                    stmt = con.createStatement();
                    stmt.executeUpdate(SQL);
                    System.out.println("Database db_mobil telah buat ^.^");
                }
                catch (SQLException ex1) {
                    System.out.println("Gagal membuat database db_mobil :')");
                }
            }
        }
    }

    public Connection getConection(){
            return this.con;
    }

    public ResultSet getDB(){
        return this.rs;
    }

    public void setData(String merk, String plat, String warna, String jenis){
        SQL = "INSERT INTO mobil (merk, plat, warna, jenis) VALUES(\"" + merk + "\", \"" + plat + "\", \"" + warna + "\", \"" + jenis + "\")";
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(SQL);
            System.out.println("Berhasil menambahkan data mobil ke database ^o^");
        } catch (SQLException ex) {
            System.out.println("Gagal menambahkan data mobil ke database @_@");
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet getData(){
        SQL = "SELECT * FROM mobil";
        try {
            stmt = con.createStatement();
            this.rs = stmt.executeQuery(SQL);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return this.rs;
    }
}