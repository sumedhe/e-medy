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
import emedy.ui.*;
import javax.swing.UIManager;
        
public class EMedy {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Set GTK+ as the default LookAndFeel
        try { 
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel"); 
        } catch (Exception ex) { 
            ex.printStackTrace(); 
        }
        
        // Show Home Window
        Home home = new Home();
        home.setVisible(true);
    }
    
}
