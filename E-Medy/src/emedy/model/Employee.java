package emedy.model;

import java.util.Date;

public class Employee {
    int employeeId;
    String firstName;
    String lastName;
    String nic;
    Date dob;
    char gender;
    String address;
    String phone;
    String mobile;
    Date startDate;
    int designationId;

    
    public Employee(){
        
    }
    
    public Employee(String firstName, String lastName, String nic, Date dob, char gender, String address, String phone, String mobile, Date startDate, int designationId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nic = nic;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.mobile = mobile;
        this.startDate = startDate;
        this.designationId = designationId;
    }
    

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDesignationId() {
        return designationId;
    }

    public void setDesignationId(int designationId) {
        this.designationId = designationId;
    }

    
    
    

    
}
