package com.sumedhe.emedy.service;

import com.sumedhe.emedy.config.Global;

public class DBException extends Exception {
    
    public DBException(String message) {
        super(message);
        Global.log("Error: " + message);
    }
    
}

