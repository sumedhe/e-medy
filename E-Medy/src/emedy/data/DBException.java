package emedy.data;

import emedy.Global;

public class DBException extends Exception {
    
    public DBException(String message) {
        super(message);
        Global.log("Error: " + message);
    }
    
}
