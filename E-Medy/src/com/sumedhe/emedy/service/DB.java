package com.sumedhe.emedy.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sumedhe.emedy.Prefs;
import com.sumedhe.emedy.common.Global;


public class DB {

    static Connection conn = null;
    static Statement stmt = null;

    public static void open() throws DBException {
        for (int i = 0; i < 3; i++) {
            DB.close(); // Close existing connections if opened
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String s = String.format("jdbc:mysql://localhost/emedy?user=%s&password=%s", Prefs.getUser(), Prefs.getPass());
                conn = DriverManager.getConnection(s);
                stmt = conn.createStatement();
                Global.log("Connection opened!");
                return;
            } catch (ClassNotFoundException ex) {
                throw new DBException("JDBC Driver not found!");
            } catch (SQLException ex) {
                Global.log("ERROR: " + ex.getMessage());
//                Global.getHome().showSetup();
                if (i == 2) {
                    throw new DBException("Access Denied!");
                }
            }

        }

    }

    public static PreparedStatement newQuery(String qry) throws DBException {
        try {
            PreparedStatement pstmt = conn.prepareStatement(qry);
            return pstmt;
        } catch (SQLException ex) {
            throw new DBException("Error in connection");
        }

    }

    public static void close() {
        try {
            if (conn != null) {
                conn.close();
                Global.log("Connection closed!");
            }
        } catch (SQLException ex) {
            Global.log("Error in closing connection");
        }
    }

    private static void checkConnection() throws DBException {
        if (conn == null) {
            throw new DBException(("Connection is closed!"));
        }
    }

    public static int execUpdate(String qry) throws Exception {
        try {
            checkConnection();
            return stmt.executeUpdate(qry);
        } catch (DBException | SQLException ex) {
//            JOptionPane.showMessageDialog(Global.getHome(), ex.getMessage(), "Database Error", JOptionPane.WARNING_MESSAGE);
            throw new DBException(ex.getMessage());
        }
    }

    public static ResultSet execQuery(String qry) throws Exception {
        try {
            checkConnection();
            return stmt.executeQuery(qry);
        } catch (DBException | SQLException ex) {
//            JOptionPane.showMessageDialog(Global.getHome(), ex.getMessage(), "Database Error", JOptionPane.WARNING_MESSAGE);
            throw new DBException(ex.getMessage());
        }
    }

    public static int execGetInt(String qry) throws DBException {
        try {
            ResultSet rs = execQuery(qry);
            rs.next();
            return rs.getInt(1);
        } catch (Exception ex) {
            throw new DBException(ex.getMessage());
        }
    }

}

