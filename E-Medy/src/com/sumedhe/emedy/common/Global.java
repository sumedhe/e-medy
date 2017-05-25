package com.sumedhe.emedy.common;

import com.sumedhe.emedy.home.HomeController;

public class Global {
	
	static HomeController home;
	
	public static HomeController getHome() {
		return home;
	}
	
	public static void setHome(HomeController home) {
		Global.home = home;
	}

    public static void log(String message){
        System.out.println(message);
    }

    
}

//public enum Gender{
//	1 : Male,
//	2 : Female
//}