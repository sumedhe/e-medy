package com.sumedhe.emedy.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sumedhe.emedy.config.Global;
import com.sumedhe.emedy.model.Test;
import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;
import com.sumedhe.emedy.tool.Cache;

public class TestData {
	
	static Cache<Test> cache = new Cache<>();
	

	// Updating the cache //
	public static void updateCache() {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM test");
			ResultSet rs = sqry.executeQuery();
			cache.editingMode(true);
			cache.clear();
			while (rs.next()) {
				Test d = toTest(rs);
				cache.put(d.getTestId(), d);
			}
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			cache.editingMode(false);
			DB.close();
		}
	}
	
	// Saving data //
	public static void save(Test test) throws DBException {
		boolean isNew = test.getTestId() == 0;
		try {
			DB.open();
			PreparedStatement sqry;

			if (isNew) {
				sqry = DB.newQuery("INSERT INTO test(name, fee) values(?, ?)");
			} else {
				sqry = DB.newQuery("UPDATE test SET name = ?, fee = ? WHERE test_id = ?");
			}

			sqry.setString(1, test.getName());
			sqry.setDouble(2, test.getFee());
			if (!isNew) {
				sqry.setInt(3, test.getTestId());
			}

			sqry.executeUpdate();
			if (isNew) {
				test.setTestId(DB.execGetInt("SELECT MAX(test_id) from test"));
			}
			
			cache.put(test.getTestId(), test);

		} catch (DBException | SQLException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}
	}


	// Deleting an item //
	public static void delete(int testId) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("DELETE FROM test WHERE test_id = ?");
			sqry.setInt(1, testId);
			sqry.executeUpdate();
			cache.remove(testId); // Remove from cache
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}
	}

	public static Test getById(int id) {
		if (id == 0) {
			return null;
		}
		Test t = cache.get(id);
		if (t == null){
			try {
				DB.open();
				PreparedStatement sqry = DB.newQuery("SELECT * FROM test WHERE test_id = ?");
				sqry.setInt(1, id);
				ResultSet rs = sqry.executeQuery();
				rs.next();
				t = toTest(rs);
				cache.put(t.getTestId(), t);
			} catch (SQLException | DBException ex) {
				Global.logError(ex.getMessage());
			} finally {
				DB.close();
			}
			
		}
		return t;
	}

	// Get the list of items //
	public static List<Test> getList()  {

		return cache.getItemList();
	}

	private static Test toTest(ResultSet rs) throws SQLException {
		Test t = new Test();
		t.setTestId(rs.getInt("test_id"));
		t.setName(rs.getString("name"));
		t.setFee(rs.getDouble("fee"));
		return t;
	}

	public static Cache<Test> getCache(){
		return cache;
	}

}
