package com.sumedhe.emedy.model;

public class Test {
    int testId;
    String name;
    double fee;

    
    public Test(){
        
    }
    
    public Test(String name, double fee) {
        this.name = name;
        this.fee = fee;
    }
    

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
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

