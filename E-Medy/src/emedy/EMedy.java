package emedy;


import emedy.data.AdmissionData;
import emedy.data.DB;
import emedy.data.WardData;
import emedy.model.Admission;
import emedy.model.Ward;
import emedy.ui.*;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
//        DB.openConnection();
        try {
//            DB.open();
//            DB.execQuery("SELECT * FROM mytable;");
//            DB.execUpdate("INSERT INTO mytable(name) values('sumedhe4')");
//            DB.close();

            Ward w = new Ward("main", 100);
            WardData.save(w);
            
        } catch (Exception ex) {
            Logger.getLogger(EMedy.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
