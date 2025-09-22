package reqres;

import com.github.javafaker.Faker;
import org.testng.annotations.DataProvider;

public class UserDataProvider {


    @DataProvider(name = "users")
    public Object[][] users(){
        Faker faker = new Faker();
        return new Object[][]{
            {faker.name().firstName()},
            {faker.name().firstName()}
        };
    }
}
