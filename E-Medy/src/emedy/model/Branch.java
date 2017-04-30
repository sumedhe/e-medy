package emedy.model;

public class Branch {
    int branch_id;
    String name;
    

    
    public Branch(String name) {
        this.name = name;
    }
    
    

    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
