package emedy.model;

public class Ward {
    int wardId;
    String name;
    int maxPatients;

    
    public Ward(String name, int maxPatients) {
        this.name = name;
        this.maxPatients = maxPatients;
    }

    
    public int getWardId() {
        return wardId;
    }

    public void setWardId(int wardId) {
        this.wardId = wardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxPatients() {
        return maxPatients;
    }

    public void setMaxPatients(int maxPatients) {
        this.maxPatients = maxPatients;
    }

    
    
    
    
    
}
