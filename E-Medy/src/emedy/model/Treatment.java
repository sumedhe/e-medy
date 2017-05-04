package emedy.model;

public class Treatment {
    int treatmentId;
    String name;
    double fee;

    
    public Treatment(){
        
    }
    
    public Treatment(String name, double fee) {
        this.name = name;
        this.fee = fee;
    }
    

    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
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
