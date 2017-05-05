package emedy.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import emedy.model.Test;

public class TestData {

    public static void add(Test test) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("INSERT INTO test(name, fee) values(?, ?)");
            sqry.setString(1, test.getName());
            sqry.setDouble(2, test.getFee());
            sqry.executeUpdate();
            test.setTestId(DB.execGetInt("SELECT MAX(test_id) from test"));
        } catch (DBException | SQLException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }

    }

    public static void update(Test test) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("UPDATE test SET name = ?, fee = ? WHERE test_id = ?");
            sqry.setString(1, test.getName());
            sqry.setDouble(2, test.getFee());
            sqry.setInt(3, test.getTestId());
            sqry.executeUpdate();
        } catch (DBException | SQLException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static int delete(int testId) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("DELETE FROM test WHERE test_id = ?");
            sqry.setInt(1, testId);
            return sqry.executeUpdate();
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static Test findById(int id) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("SELECT * FROM test WHERE test_id = ?");
            sqry.setInt(1, id);
            ResultSet rs = sqry.executeQuery();
            rs.next();
            return toTest(rs);
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static List<Test> getList() throws DBException {
        List<Test> tests = new ArrayList<>();
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("SELECT * FROM test");
            ResultSet rs = sqry.executeQuery();
            while (rs.next()) {
                tests.add(toTest(rs));
            }
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
        return tests;
    }

    private static Test toTest(ResultSet rs) throws SQLException {
        Test t = new Test();
        t.setTestId(rs.getInt("test_id"));
        t.setName(rs.getString("name"));
        t.setFee(rs.getDouble("fee"));
        return t;
    }
}
