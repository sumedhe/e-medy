package emedy.model;

public class Blood_Group {
    int blood_group_id;
    String name;

    
    public Blood_Group(String name) {
        this.name = name;
    }

    
    
    public int getBlood_group_id() {
        return blood_group_id;
    }

    public void setBlood_group_id(int blood_group_id) {
        this.blood_group_id = blood_group_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
}
