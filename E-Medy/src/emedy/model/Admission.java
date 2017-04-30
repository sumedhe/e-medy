package emedy.model;

public class Admission {
    int admission_id;    
    int ward_id;
    int patient_id;
    String recommended_by;
    int confirmed_doctor_id;
    String custodian_name;
    String custodian_nic;
    String custodian_phone;
    double payment;

    
    
    public Admission(int ward_id, int patient_id, String recommended_by, int confirmed_doctor_id, String custodian_name, String custodian_nic, String custodian_phone, double payment) {
        this.ward_id = ward_id;
        this.patient_id = patient_id;
        this.recommended_by = recommended_by;
        this.confirmed_doctor_id = confirmed_doctor_id;
        this.custodian_name = custodian_name;
        this.custodian_nic = custodian_nic;
        this.custodian_phone = custodian_phone;
        this.payment = payment;
    }

    
    public int getAdmission_id() {
        return admission_id;
    }

    public void setAdmission_id(int admission_id) {
        this.admission_id = admission_id;
    }

    public int getWard_id() {
        return ward_id;
    }

    public void setWard_id(int ward_id) {
        this.ward_id = ward_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getRecommended_by() {
        return recommended_by;
    }

    public void setRecommended_by(String recommended_by) {
        this.recommended_by = recommended_by;
    }

    public int getConfirmed_doctor_id() {
        return confirmed_doctor_id;
    }

    public void setConfirmed_doctor_id(int confirmed_doctor_id) {
        this.confirmed_doctor_id = confirmed_doctor_id;
    }

    public String getCustodian_name() {
        return custodian_name;
    }

    public void setCustodian_name(String custodian_name) {
        this.custodian_name = custodian_name;
    }

    public String getCustodian_nic() {
        return custodian_nic;
    }

    public void setCustodian_nic(String custodian_nic) {
        this.custodian_nic = custodian_nic;
    }

    public String getCustodian_phone() {
        return custodian_phone;
    }

    public void setCustodian_phone(String custodian_phone) {
        this.custodian_phone = custodian_phone;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }
    
    
    
}
