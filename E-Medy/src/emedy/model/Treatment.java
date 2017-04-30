package emedy.model;

public class Treatment {
    int treatment_id;
    String name;
    double fee;

    
    public Treatment(String name, double fee) {
        this.name = name;
        this.fee = fee;
    }

    
    public int getTreatment_id() {
        return treatment_id;
    }

    public void setTreatment_id(int treatment_id) {
        this.treatment_id = treatment_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
    
    
}
