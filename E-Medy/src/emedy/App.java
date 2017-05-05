package emedy;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import emedy.data.DBException;
import emedy.data.WardData;
import emedy.model.Ward;
import emedy.ui.Home;

public class App {
	public static void main(String args[]){
		// INITIALIZATION
        Prefs.load();
        

            Ward w;
			try {
				w = WardData.findById(5);
	            w.setName("Sumedhe");
	            WardData.update(w);
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            
//            Ward w = new Ward("main3", 100);
//            WardData.add(w);
//            Global.log(String.valueOf(w.getWardId()));
            
//            for (Ward w: WardData.getList()){
//                Global.log(w.getName());
//            }
//          
        
        try { 
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel"); 
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) { 
        	
        }
        
        // Show Home Window
        Home home = new Home();
        Global.setHome(home);
        home.setVisible(true);

	}

}
