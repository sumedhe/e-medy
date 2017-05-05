package emedy;

import java.util.prefs.Preferences;

public class Prefs {
    static Preferences prefs;
    
    public static void load(){
        prefs = Preferences.userNodeForPackage(App.class);
    }
    
    public static void setUser(String username){
        prefs.put("User", username);
    }
    
    public static String getUser(){
        return prefs.get("User", null);
    }
    
    public static void setPass(String password){
        prefs.put("Pass", password);
    }
    
    public static String getPass(){
        return prefs.get("Pass", null);
    }
}
