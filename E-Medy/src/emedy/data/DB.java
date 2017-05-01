package emedy.data;

import emedy.Global;
import emedy.Prefs;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class DB {
    static Connection conn = null;
    static Statement stmt = null;
    
    
    private static void openConnection() throws DBException{
        for (int i = 0; i<3; i++) {

            try{
                Class.forName("com.mysql.jdbc.Driver");
                String s = String.format("jdbc:mysql://localhost/test?user=%s&password=%s", Prefs.getUser(), Prefs.getPass());
                conn = DriverManager.getConnection(s);
                stmt = conn.createStatement();
                Global.Log("Connection opened!");
                return;
            } catch (ClassNotFoundException ex) {
                throw new DBException("JDBC Driver not found!");
            } catch (SQLException ex) {
                Global.Log("ERROR: " + ex.getMessage());
                emedy.Global.getHome().showSetup();
                if (i == 2){
                    throw new DBException("Access Denied!");
                }
            }
                        
        }
                
    }
    
    private static void closeConnection(){
        try{
            if (conn != null){
                conn.close();
                System.out.println("Connection closed!");
            }
        } catch (SQLException ex) {
            Global.Log("Error in closing connection");
        }
    }
   
    
    private static ResultSet execute(String qry, boolean gets) throws Exception{
        try{
            openConnection();
            if (gets){
                stmt.executeUpdate(qry);
                return null;
            } else {
                return stmt.executeQuery(qry);
            }
        } catch (DBException ex){
            JOptionPane.showMessageDialog(Global.getHome(), ex.getMessage(), "Database Error", JOptionPane.WARNING_MESSAGE);
            throw ex;
        } catch (SQLException ex){
            throw new DBException("ERROR: " + ex.getMessage());
        } finally {
            closeConnection();
        }
    }
    
    
    
    
    public static ResultSet executeUpdate(String qry) throws Exception{
        return execute(qry, true);
    }
    
    public static void executeQuery(String qry) throws Exception{
        execute(qry, false);
    }
    
}
