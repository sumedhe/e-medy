package com.sumedhe.emedy.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sumedhe.emedy.config.Global;
import com.sumedhe.emedy.model.Branch;
import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;
import com.sumedhe.emedy.tool.Cache;

public class BranchData {

	static Cache<Branch> cache = new Cache<>();

	public static void updateCache() {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM branch");
			ResultSet rs = sqry.executeQuery();
			cache.editingMode(true);
			cache.clear();
			while (rs.next()) {
				Branch b = toBranch(rs);
				cache.put(b.getBranchId(), b);
			}
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			cache.editingMode(false);
			DB.close();
		}
	}

	public static void save(Branch branch) throws DBException {
		boolean isNew = branch.getBranchId() == 0;
		try {
			DB.open();
			PreparedStatement sqry;

			if (isNew) {
				sqry = DB.newQuery("INSERT INTO branch(name) values(?)");
			} else {
				sqry = DB.newQuery("UPDATE branch SET name = ? WHERE branch_id = ?");
			}

			sqry.setString(1, branch.getName());
			if (!isNew) {
				sqry.setInt(2, branch.getBranchId());
			}

			sqry.executeUpdate();
			if (isNew) {
				branch.setBranchId(DB.execGetInt("SELECT MAX(branch_id) from branch"));
			}

			cache.put(branch.getBranchId(), branch);

		} catch (DBException | SQLException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}
	}

	public static void delete(int branchId) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("DELETE FROM branch WHERE branch_id = ?");
			sqry.setInt(1, branchId);
			sqry.executeUpdate();
			cache.remove(branchId); // Remove from cache
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}
	}

	public static Branch getById(int id) {
		if (id == 0) {
			return null;
		}
		Branch b = cache.get(id);
		if (b == null) {
			try {
				DB.open();
				PreparedStatement sqry = DB.newQuery("SELECT * FROM branch WHERE branch_id = ?");
				sqry.setInt(1, id);
				ResultSet rs = sqry.executeQuery();
				rs.next();
				b = toBranch(rs);
				cache.put(b.getBranchId(), b);
			} catch (SQLException | DBException ex) {
				Global.logError(ex.getMessage());
			} finally {
				DB.close();
			}
		}
		return b;
	}

	public static List<Branch> getList() {

		return cache.getItemList();
	}

	private static Branch toBranch(ResultSet rs) throws SQLException {
		Branch b = new Branch();
		b.setBranchId(rs.getInt("branch_id"));
		b.setName(rs.getString("name"));
		return b;
	}

	public static Cache<Branch> getCache() {
		return cache;
	}

}
