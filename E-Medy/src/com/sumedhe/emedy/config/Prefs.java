package com.sumedhe.emedy.config;

import java.util.prefs.Preferences;

import com.sumedhe.emedy.Main;

public class Prefs {
    static Preferences prefs;
    
    // Load preferences //
    public static void load(){
        prefs = Preferences.userNodeForPackage(Main.class);
    }
    
    
    // Username preference //
    public static void setUser(String username){
        prefs.put("User", username);
    }
    
    public static String getUser(){
        return prefs.get("User", null);
    }
    
    // Password Preference //
    public static void setPass(String password){
        prefs.put("Pass", password);
    }
    
    public static String getPass(){
        return prefs.get("Pass", null);
    }
}
