package emedy.model;

public class Ward {
    int ward_id;
    String name;
    int max_patients;

    
    public Ward(String name, int max_patients) {
        this.name = name;
        this.max_patients = max_patients;
    }
    

    public int getWard_id() {
        return ward_id;
    }

    public void setWard_id(int ward_id) {
        this.ward_id = ward_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMax_patients() {
        return max_patients;
    }

    public void setMax_patients(int max_patients) {
        this.max_patients = max_patients;
    }
    
    
    
    
}
