/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emedy;
/**
 *
 * @author sumedhe
 */
import emedy.data.DB;
import emedy.ui.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
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
            DB.executeQuery("");
        } catch (Exception ex) {
            System.out.println("xxxxxxxxxxxxxx");
        }
    }
    
}
