package emedy.data;

import emedy.model.Ward;


public class WardData {
    public static int save(Ward ward){
        String qry = String.format("INSERT INTO ward(name, max_patients) "
                + "values('%s', %d)",
                ward.getName(),
                ward.getMaxPatients());
        try{
            DB.open();
            return DB.execUpdate(qry);
        } catch (Exception ex) {
            return 0;
        } finally {
            DB.close();            
        }
        
    }
    
}
