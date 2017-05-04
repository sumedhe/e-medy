package emedy.model;

import java.util.Date;

public class Doctor extends Employee {
    int doctorId;
    int branchId;

    
    public Doctor(){
        super();
    }
    
    public Doctor(String firstName, String lastName, String nic, Date dob, char gender, String address, String phone, String mobile, Date startDate, int designationId, int branchId) {
        super(firstName, lastName, nic, dob, gender, address, phone, mobile, startDate, designationId);
        this.branchId = branchId;
    }
    

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    
    
    
}
