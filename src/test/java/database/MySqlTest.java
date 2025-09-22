package database;

import org.testng.Assert;
import org.testng.annotations.Test;
import utils.sql.SqlUtil;

public class MySqlTest {


    @Test
    public void verifyClanAccPresentInDb(){
        String query = "Select clan_acc from clan limit 1";
        Object[][] results = SqlUtil.executeQuery(query);
        System.out.println("Query executed successfully. Number of rows returned: " + results.length);
        Assert.assertEquals(results[0][0].toString(),"CL00001","Clan Id mismatch");
    }
}
