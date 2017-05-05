package emedy.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import emedy.model.Doctor;
import emedy.model.Employee;

public class DoctorData {

    public static void add(Doctor doctor) throws DBException {
    	Employee e = (Employee)doctor;
    	EmployeeData.add(e);
    	
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("INSERT INTO doctor(branch_id, employee_id) values(?, ?)");
            sqry.setInt(1, doctor.getBranchId());
            sqry.setInt(2, e.getEmployeeId());
            sqry.executeUpdate();
            doctor.setDoctorId(DB.execGetInt("SELECT MAX(doctor_id) from doctor"));
        } catch (DBException | SQLException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }

    }

    public static void update(Doctor doctor) throws DBException {
    	EmployeeData.update((Employee)doctor);
    	
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("UPDATE doctor SET branch_id = ?, employee_id = ? WHERE doctor_id = ?");
            sqry.setInt(1, doctor.getBranchId());
            sqry.setInt(2, doctor.getEmployeeId());
            sqry.setInt(3, doctor.getDoctorId());
            sqry.executeUpdate();
        } catch (DBException | SQLException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static int delete(int doctorId) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("DELETE FROM doctor WHERE doctor_id = ?");
            sqry.setInt(1, doctorId);
            sqry.executeUpdate();
            return EmployeeData.delete(doctorId);
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static Doctor findById(int id) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("SELECT * FROM doctor INNER JOIN employee ON doctor.employee_id = employee.employee_id WHERE doctor_id = ?");
            sqry.setInt(1, id);
            ResultSet rs = sqry.executeQuery();
            rs.next();
            return toDoctor(rs);
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static List<Doctor> getList() throws DBException {
        List<Doctor> doctors = new ArrayList<>();
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("SELECT * FROM doctor INNER JOIN employee ON doctor.employee_id = employee.employee_id");
            ResultSet rs = sqry.executeQuery();
            while (rs.next()) {
                doctors.add(toDoctor(rs));
            }
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
        return doctors;
    }

    private static Doctor toDoctor(ResultSet rs) throws SQLException {
        Doctor d = new Doctor(EmployeeData.toEmployee(rs));
        d.setDoctorId(rs.getInt("doctor_id"));
        d.setBranchId(rs.getInt("branch_id"));
        d.setEmployeeId(rs.getInt("employee_id"));
        return d;
    }
}
