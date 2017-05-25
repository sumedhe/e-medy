package com.sumedhe.emedy.misc;

public class Branch {
    int branchId;
    String name;

    
    public Branch(){
        
    }
    
    public Branch(String name) {
        this.name = name;
    }
    

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
	@Override
	public String toString() {
		return this.name;
	}

    
    
}
