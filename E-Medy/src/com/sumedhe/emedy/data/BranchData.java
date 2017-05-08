package com.sumedhe.emedy.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sumedhe.emedy.model.Branch;
import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;

public class BranchData {

    public static void add(Branch branch) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("INSERT INTO branch(name) values(?)");
            sqry.setString(1, branch.getName());
            sqry.executeUpdate();
            branch.setBranchId(DB.execGetInt("SELECT MAX(branch_id) from branch"));
        } catch (DBException | SQLException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }

    }

    public static void update(Branch branch) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("UPDATE branch SET name = ? WHERE branch_id = ?");
            sqry.setString(1, branch.getName());
            sqry.setInt(2, branch.getBranchId());
            sqry.executeUpdate();
        } catch (DBException | SQLException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static int delete(int branchId) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("DELETE FROM branch WHERE branch_id = ?");
            sqry.setInt(1, branchId);
            return sqry.executeUpdate();
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static Branch findById(int id) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("SELECT * FROM branch WHERE branch_id = ?");
            sqry.setInt(1, id);
            ResultSet rs = sqry.executeQuery();
            rs.next();
            return toBranch(rs);
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static List<Branch> getList() throws DBException {
        List<Branch> branches = new ArrayList<>();
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("SELECT * FROM ward");
            ResultSet rs = sqry.executeQuery();
            while (rs.next()) {
                branches.add(toBranch(rs));
            }
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
        return branches;
    }

    private static Branch toBranch(ResultSet rs) throws SQLException {
        Branch b = new Branch();
        b.setBranchId(rs.getInt("branch_id"));
        b.setName(rs.getString("name"));
        return b;
    }
}
