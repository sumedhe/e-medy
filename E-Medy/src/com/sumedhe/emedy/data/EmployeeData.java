package com.sumedhe.emedy.data;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sumedhe.emedy.model.Employee;
import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;

public class EmployeeData {

    public static void add(Employee employee) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("INSERT INTO employee(first_name, last_name, nic, dob, gender, address, phone, mobile, start_date, designation_id) values(?,?,?,?,?,?,?,?,?,?)");
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
            sqry.executeUpdate();
            employee.setEmployeeId(DB.execGetInt("SELECT MAX(employee_id) from employee"));
        } catch (DBException | SQLException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }

    }

    public static void update(Employee employee) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("UPDATE employee SET first_name = ?, last_name = ?, nic = ?, dob = ?, gender = ?, address = ?, phone = ?, mobile = ?, start_date = ?, designation_id = ? WHERE employee_id = ?");
            sqry.setString(1, employee.getFirstName());
            sqry.setString(2,  employee.getLastName());
            sqry.setString(3, employee.getNic());
            sqry.setDate(4, (Date)employee.getDob());
            sqry.setString(5, String.valueOf(employee.getGender()));
            sqry.setString(6, employee.getAddress());
            sqry.setString(7, employee.getPhone());
            sqry.setString(8, employee.getPhone());
            sqry.setString(9, employee.getMobile());
            sqry.setDate(9, (Date)employee.getStartDate());
            sqry.setInt(10, employee.getDesignationId());     
            sqry.setInt(11, employee.getEmployeeId());
            sqry.executeUpdate();
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

    public static Employee findById(int id) throws DBException {
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
