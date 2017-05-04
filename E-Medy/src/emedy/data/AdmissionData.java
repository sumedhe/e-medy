package emedy.data;

import emedy.model.Admission;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdmissionData {

    public static void add(Admission admission) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("INSERT INTO admission(patient_id, ward_id, recommended_by, confirmed_doctor_id, custodian_name, custodian_nic, custodian_phone, payment) values(?,?,?,?,?,?,?,?)");
            sqry.setInt(1, admission.getPatientId());
            sqry.setInt(2, admission.getWardId());
            sqry.setString(3, admission.getRecommendedBy());
            sqry.setInt(4, admission.getConfirmedDoctorId());
            sqry.setString(5, admission.getCustodianName());
            sqry.setString(6, admission.getCustodianName());
            sqry.setString(7, admission.getCustodianPhone());
            sqry.setDouble(8, admission.getPayment());
            sqry.executeUpdate();
            admission.setWardId(DB.execGetInt("SELECT MAX(admission_id) from admission"));
        } catch (DBException | SQLException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }

    }

    public static void update(Admission admission) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("UPDATE admission SET patient_id = ?, ward_id = ?, recommended_by = ?, confirmed_doctor_id = ?, custodian_name = ?, custodian_nic = ?, custodian_phone = ?, payment = ?");
            sqry.setInt(1, admission.getPatientId());
            sqry.setInt(2, admission.getWardId());
            sqry.setString(3, admission.getRecommendedBy());
            sqry.setInt(4, admission.getConfirmedDoctorId());
            sqry.setString(5, admission.getCustodianName());
            sqry.setString(6, admission.getCustodianNIC());
            sqry.setString(7, admission.getCustodianPhone());
            sqry.setDouble(8, admission.getPayment());
            sqry.executeUpdate();
        } catch (DBException | SQLException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static int delete(int admissionId) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("DELETE FROM admission WHERE admission_id = ?");
            sqry.setInt(1, admissionId);
            return sqry.executeUpdate();
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static Admission findById(int id) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("SELECT * FROM admission WHERE admission_id = ?");
            sqry.setInt(1, id);
            ResultSet rs = sqry.executeQuery();
            rs.next();
            return toAdmission(rs);
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static List<Admission> getList() throws DBException {
        List<Admission> admissions = new ArrayList<>();
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("SELECT * FROM admission");
            ResultSet rs = sqry.executeQuery();
            while (rs.next()) {
                admissions.add(toAdmission(rs));
            }
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
        return admissions;
    }

    private static Admission toAdmission(ResultSet rs) throws SQLException {
        Admission a = new Admission();
        a.setAdmissionId(rs.getInt("admission_id"));
        a.setPatientId(rs.getInt("patient_id"));
        a.setWardId(rs.getInt("ward_id"));
        a.setRecommendedBy(rs.getString("recommended_by"));
        a.setConfirmedDoctorId(rs.getInt("cofirmed_doctor_id"));
        a.setCustodianName(rs.getString("custodian_name"));
        a.setCustodianNIC(rs.getString("custodian_nic"));
        a.setCustodianPhone(rs.getString("custodian_phone"));
        a.setPayment(rs.getDouble("payment"));
        return a;
    }
}
