package emedy;

import emedy.ui.Home;

public class Global {
    static Home home;

    public static Home getHome() {
        return home;
    }

    public static void setHome(Home home) {
        Global.home = home;
    }
    
    
    public static void Log(String message){
        System.out.println(message);
    }
    
}
