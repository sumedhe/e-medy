package emedy.data;

import emedy.Global;

public class DBException extends Exception {
    
    public DBException(String message) {
        super(message);
        Global.Log("Error: " + message);
    }
    
}
