package emedy.model;

public class Test {
    int test_id;
    String name;
    double fee;

    
    public Test(String name, double fee) {
        this.name = name;
        this.fee = fee;
    }

    
    public int getTest_id() {
        return test_id;
    }

    public void setTest_id(int test_id) {
        this.test_id = test_id;
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
