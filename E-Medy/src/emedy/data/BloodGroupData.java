package emedy.data;

import emedy.model.BloodGroup;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BloodGroupData {

    public static void add(BloodGroup bloodgroup) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("INSERT INTO blood_group(name) values(?)");
            sqry.setString(1, bloodgroup.getName());
            sqry.executeUpdate();
            bloodgroup.setBloodGroupId(DB.execGetInt("SELECT MAX(blood_group_id) from blood_group"));
        } catch (DBException | SQLException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }

    }

    public static void update(BloodGroup bloodgroup) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("UPDATE blood_group SET name = ? WHERE blood_group_id = ?");
            sqry.setString(1, bloodgroup.getName());
            sqry.executeUpdate();
        } catch (DBException | SQLException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static int delete(int bloodGroupId) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("DELETE FROM blood_group WHERE blood_group_id = ?");
            sqry.setInt(1, bloodGroupId);
            return sqry.executeUpdate();
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static BloodGroup findById(int id) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("SELECT * FROM blood_group WHERE blood_group_id = ?");
            sqry.setInt(1, id);
            ResultSet rs = sqry.executeQuery();
            rs.next();
            return toBloodGroup(rs);
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static List<BloodGroup> getList() throws DBException {
        List<BloodGroup> bloodGroups = new ArrayList<>();
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("SELECT * FROM blood_group");
            ResultSet rs = sqry.executeQuery();
            while (rs.next()) {
                bloodGroups.add(toBloodGroup(rs));
            }
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
        return bloodGroups;
    }

    private static BloodGroup toBloodGroup(ResultSet rs) throws SQLException {
        BloodGroup bg = new BloodGroup();
        bg.setBloodGroupId(rs.getInt("blood_group_id"));
        bg.setName(rs.getString("name"));
        return bg;
    }
}
