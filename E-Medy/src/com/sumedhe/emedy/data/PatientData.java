package com.sumedhe.emedy.data;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sumedhe.emedy.model.Patient;
import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;


public class PatientData {
	
	public static void save(Patient patient) throws DBException{
		if (patient.getPatientId() == 0) {
			add(patient);
		} else {
			update(patient);
		}
	}
	
    public static void add(Patient patient) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("INSERT INTO patient(first_name, last_name, nic, dob, gender, address, phone, mobile, blood_group_id, consultant_id, registered_on) values(?,?,?,?,?,?,?,?,?,?,?)");
            sqry.setString(1, patient.getFirstName());
            sqry.setString(2, patient.getLastName());
            sqry.setString(3, patient.getNic());
            sqry.setDate(4, (Date)patient.getDob());
            sqry.setString(5, String.valueOf(patient.getGender()));
            sqry.setString(6, patient.getAddress());
            sqry.setString(7, patient.getPhone());
            sqry.setString(8, patient.getMobile());
            sqry.setInt(9, patient.getBloodGroupId());
            sqry.setInt(10, patient.getConsultantId());
            sqry.setDate(11, (Date)patient.getRegisteredOn());
            sqry.executeUpdate();
            patient.setPatientId(DB.execGetInt("SELECT MAX(patient_id) from patient"));
        } catch (DBException | SQLException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }

    }

    public static void update(Patient patient) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("UPDATE patient SET first_name = ?, last_name = ?, nic = ?, dob = ?, gender = ?, address = ?, phone = ?, mobile = ?, blood_group = ?, consultant_id = ?, registered_on = ? WHERE patient_id = ?");
            sqry.setString(1, patient.getFirstName());
            sqry.setString(2, patient.getLastName());
            sqry.setString(3, patient.getNic());
            sqry.setDate(4, (Date)patient.getDob());
            sqry.setString(5, String.valueOf(patient.getGender()));
            sqry.setString(6, patient.getAddress());
            sqry.setString(7, patient.getPhone());
            sqry.setString(8, patient.getMobile());
            sqry.setInt(9, patient.getBloodGroupId());
            sqry.setInt(10, patient.getConsultantId());
            sqry.setDate(11, (Date)patient.getRegisteredOn());
            sqry.setInt(12, patient.getPatientId());
            sqry.executeUpdate();
        } catch (DBException | SQLException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static int delete(int patientId) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("DELETE FROM patient WHERE patient_id = ?");
            sqry.setInt(1, patientId);
            return sqry.executeUpdate();
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static Patient findById(int id) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("SELECT * FROM patient WHERE patient_id = ?");
            sqry.setInt(1, id);
            ResultSet rs = sqry.executeQuery();
            rs.next();
            return toPatient(rs);
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static List<Patient> getList() throws DBException {
        List<Patient> patients = new ArrayList<>();
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("SELECT * FROM patient");
            ResultSet rs = sqry.executeQuery();
            while (rs.next()) {
                patients.add(toPatient(rs));
            }
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
        return patients;
    }

    private static Patient toPatient(ResultSet rs) throws SQLException {
        Patient p = new Patient();
        p.setPatientId(rs.getInt("patient_id"));
        p.setFirstName(rs.getString("first_name"));
        p.setLastName(rs.getString("last_name"));
        p.setNic(rs.getString("nic"));
        p.setDob(rs.getDate("dob"));
        p.setGender(rs.getString("gender").charAt(0));
        p.setAddress(rs.getString("address"));
        p.setPhone(rs.getString("phone"));
        p.setMobile(rs.getString("mobile"));
        p.setBloodGroupId(rs.getInt("blood_group_id"));
        p.setConsultantId(rs.getInt("consultant_id"));
        p.setRegisteredOn(rs.getDate("registered_on"));
        return p;
    }
}
