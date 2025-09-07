package database;

import org.testng.annotations.Test;
import utils.sql.SqlUtil;

public class MySqlTest {

    @Test
    public void verifyClanAccPresentInDb(){
        String query = "Select clan_acc from clan limit 1";
        SqlUtil.executeQuery(query);
    }
}
