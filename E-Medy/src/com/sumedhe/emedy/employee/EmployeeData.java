package com.sumedhe.emedy.employee;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;

public class EmployeeData {

	public static void save(Employee employee) throws DBException{
        boolean isNew = employee.getEmployeeId() == 0;
		try {
            DB.open();
            PreparedStatement sqry;

            if (isNew){
            	sqry = DB.newQuery("INSERT INTO employee(first_name, last_name, nic, dob, gender, address, phone, mobile, start_date, designation_id) values(?,?,?,?,?,?,?,?,?,?)");
            } else {
            	sqry = DB.newQuery("UPDATE employee SET first_name = ?, last_name = ?, nic = ?, dob = ?, gender = ?, address = ?, phone = ?, mobile = ?, start_date = ?, designation_id = ? WHERE employee_id = ?");
            }
            
            sqry.setString(1, employee.getFirstName());
            sqry.setString(2, employee.getLastName());
            sqry.setString(3, employee.getNic());
            sqry.setDate(4, (Date)employee.getDob());
            sqry.setString(5, String.valueOf(employee.getGender()));
            sqry.setString(6,  employee.getAddress());
            sqry.setString(7, employee.getPhone());
            sqry.setString(8, employee.getMobile());
            sqry.setDate(9, (Date)employee.getStartDate());
            sqry.setInt(10, employee.getDesignationId());          
            if (!isNew) { sqry.setInt(11, employee.getEmployeeId()); }
            
            sqry.executeUpdate();
            if (isNew) { employee.setEmployeeId(DB.execGetInt("SELECT MAX(employee_id) from employee")); }

		} catch (DBException | SQLException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
	}


    public static int delete(int employeeId) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("DELETE FROM employee WHERE employee_id = ?");
            sqry.setInt(1, employeeId);
            return sqry.executeUpdate();
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static Employee getById(int id) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("SELECT * FROM employee WHERE employee_id = ?");
            sqry.setInt(1, id);
            ResultSet rs = sqry.executeQuery();
            rs.next();
            return toEmployee(rs);
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static List<Employee> getList() throws DBException {
        List<Employee> employees = new ArrayList<>();
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("SELECT * FROM employee");
            ResultSet rs = sqry.executeQuery();
            while (rs.next()) {
                employees.add(toEmployee(rs));
            }
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
        return employees;
    }

    public static Employee toEmployee(ResultSet rs) throws SQLException {
        Employee e = new Employee();
        e.setEmployeeId(rs.getInt("employee_id"));
        e.setFirstName(rs.getString("first_name"));
        e.setLastName(rs.getString("last_name"));
        e.setNic(rs.getString("nic"));
        e.setDob(rs.getDate("dob"));
        e.setGender(rs.getString("gender").charAt(0));
        e.setAddress(rs.getString("address"));
        e.setPhone(rs.getString("phone"));
        e.setMobile(rs.getString("mobile"));
        e.setStartDate(rs.getDate("start_date"));
        e.setDesignationId(rs.getInt("designation_id"));
        return e;
    }
}
