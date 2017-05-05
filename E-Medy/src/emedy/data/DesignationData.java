package emedy.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import emedy.model.Designation;

public class DesignationData {
    public static void add(Designation designation) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("INSERT INTO designation(name, wage) values(?, ?)");
            sqry.setString(1, designation.getName());
            sqry.setDouble(2, designation.getWage());
            sqry.executeUpdate();
            designation.setDesignationId(DB.execGetInt("SELECT MAX(designation_id) from designation"));
        } catch (DBException | SQLException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }

    }

    public static void update(Designation designation) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("UPDATE designation SET name = ?, wage = ? WHERE designation_id = ?");
            sqry.setString(1, designation.getName());
            sqry.setDouble(2, designation.getWage());
            sqry.setInt(3, designation.getDesignationId());
            sqry.executeUpdate();
        } catch (DBException | SQLException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static int delete(int designationId) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("DELETE FROM designation WHERE designation_id = ?");
            sqry.setInt(1, designationId);
            return sqry.executeUpdate();
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static Designation findById(int id) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("SELECT * FROM designation WHERE designation_id = ?");
            sqry.setInt(1, id);
            ResultSet rs = sqry.executeQuery();
            rs.next();
            return toDesignation(rs);
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static List<Designation> getList() throws DBException {
        List<Designation> designations = new ArrayList<>();
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("SELECT * FROM designation");
            ResultSet rs = sqry.executeQuery();
            while (rs.next()) {
                designations.add(toDesignation(rs));
            }
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
        return designations;
    }

    private static Designation toDesignation(ResultSet rs) throws SQLException {
        Designation w = new Designation();
        w.setDesignationId(rs.getInt("designation_id"));
        w.setName(rs.getString("name"));
        w.setWage(rs.getInt("wage"));
        return w;
    }
}
