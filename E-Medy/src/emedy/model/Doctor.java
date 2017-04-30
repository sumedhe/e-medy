package emedy.model;

import java.util.Date;

public class Doctor extends Employee {
    int doctor_id;
    int branch_id;

    
    
    public Doctor(String first_name, String last_name, String nic, Date dob, char Gender, String address, String phone, String mobile, Date start_date, int designation_id, int branch_id) {
        super(first_name, last_name, nic, dob, Gender, address, phone, mobile, start_date, designation_id);
        this.branch_id = branch_id;
    }

    
    
    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }
    
}
