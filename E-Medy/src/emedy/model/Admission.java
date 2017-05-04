package emedy.model;

public class Admission {
    int admissionId;    
    int wardId;
    int patientId;
    String recommendedBy;
    int confirmedDoctorId;
    String custodianName;
    String custodianNIC;
    String custodianPhone;
    double payment;

    
    public Admission(){
        
    }
    
    public Admission(int ward_id, int patient_id, String recommended_by, int confirmed_doctor_id, String custodian_name, String custodian_nic, String custodian_phone, double payment) {
        this.wardId = ward_id;
        this.patientId = patient_id;
        this.recommendedBy = recommended_by;
        this.confirmedDoctorId = confirmed_doctor_id;
        this.custodianName = custodian_name;
        this.custodianNIC = custodian_nic;
        this.custodianPhone = custodian_phone;
        this.payment = payment;
    }
    

    public int getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(int admissionId) {
        this.admissionId = admissionId;
    }

    public int getWardId() {
        return wardId;
    }

    public void setWardId(int wardId) {
        this.wardId = wardId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getRecommendedBy() {
        return recommendedBy;
    }

    public void setRecommendedBy(String recommendedBy) {
        this.recommendedBy = recommendedBy;
    }

    public int getConfirmedDoctorId() {
        return confirmedDoctorId;
    }

    public void setConfirmedDoctorId(int confirmedDoctorId) {
        this.confirmedDoctorId = confirmedDoctorId;
    }

    public String getCustodianName() {
        return custodianName;
    }

    public void setCustodianName(String custodianName) {
        this.custodianName = custodianName;
    }

    public String getCustodianNIC() {
        return custodianNIC;
    }

    public void setCustodianNIC(String custodianNIC) {
        this.custodianNIC = custodianNIC;
    }

    public String getCustodianPhone() {
        return custodianPhone;
    }

    public void setCustodianPhone(String custodianPhone) {
        this.custodianPhone = custodianPhone;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    

    
    
    
}
