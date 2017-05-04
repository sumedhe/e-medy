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

        try {


            Ward w = WardData.findById(5);
            w.setName("Sum");
            WardData.update(w);
            
//            Ward w = new Ward("main3", 100);
//            WardData.add(w);
//            Global.log(String.valueOf(w.getWardId()));
            
//            for (Ward w: WardData.getList()){
//                Global.log(w.getName());
//            }
//            
        } catch (Exception ex) {
            Logger.getLogger(EMedy.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
