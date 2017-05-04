package emedy.model;

public class Designation {
    int designationId;
    String name;
    double wage;

    
    public Designation(){
        
    }
    
    public Designation(String name, double wage) {
        this.name = name;
        this.wage = wage;
    }
    
    

    public int getDesignationId() {
        return designationId;
    }

    public void setDesignationId(int designationId) {
        this.designationId = designationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWage() {
        return wage;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }

    
    
    
}
