package emedy;


import emedy.data.DB;
import emedy.ui.*;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
        
public class EMedy {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Set GTK+ as the default LookAndFeel
        try { 
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel"); 
//            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); 
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) { 
        }
        
        Prefs.load();

        // Show Home Window
        Home home = new Home();
        Global.setHome(home);
        home.setVisible(true);
        
//        try {
//            DB.executeUpdate("INSERT INTO mytable(name) values('sumedhe3')");
//        } catch (Exception ex) {
//            
//        }
    }
    
}
