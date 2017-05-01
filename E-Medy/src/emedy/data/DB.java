package emedy.data;

import emedy.Global;
import emedy.Prefs;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DB {
    static Connection conn = null;
    
    
    private static void openConnection() throws Exception{
        for (int i = 0; i<3; i++) {

            try{
                Class.forName("com.mysql.jdbc.Driver");
                String s = String.format("jdbc:mysql://localhost/emedy?user=%s&password=%s",
                        Prefs.getUser(),
                        Prefs.getPass());
                conn = DriverManager.getConnection(s);
                System.out.println("Connection opened!");
                return;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(Global.getHome(), "JDBC Driver not found!", "Database Error", JOptionPane.WARNING_MESSAGE);
                throw new Exception("Can't connect to the server");
            } catch (SQLException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
                emedy.Global.getHome().showSetup();
                if (i == 2){
                    JOptionPane.showMessageDialog(Global.getHome(), "Access Denied!", "Database Error", JOptionPane.WARNING_MESSAGE);
                    throw ex;
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
            
        }
    }
    
    
    
    public static void executeQuery(String qry) throws Exception{
        try{
            openConnection();
            
        } catch (Exception ex){
            throw ex;
        } finally {
            closeConnection();
        }
        
    }

}
