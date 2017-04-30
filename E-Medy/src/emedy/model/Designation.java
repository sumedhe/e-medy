package emedy.model;

public class Designation {
    int designation_id;
    String name;
    double wage;

    
    
    public Designation(String name, double wage) {
        this.name = name;
        this.wage = wage;
    }

    
    
    public int getDesignation_id() {
        return designation_id;
    }

    public void setDesignation_id(int designation_id) {
        this.designation_id = designation_id;
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
