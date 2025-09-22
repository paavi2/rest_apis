package reqres;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestUserCreation {


    String baseUri = "https://reqres.in/";
    String CREATE_USER ="/api/users";
    String req_body = "{\n" + "    \"name\": \"<username>\",\n" + "    \"job\": \"leader\"\n" + "}";


    @Test(dataProviderClass = UserDataProvider.class, dataProvider = "users")
    public void testUsers(String name){
        req_body = req_body.replace("<username>",name);
        Response response = RestAssured
                .given()
                .baseUri(baseUri)
                .body(req_body)
                .header("x-api-key", "reqres-free-v1")  // ✅ custom header
                .when()
                .post(CREATE_USER)
                .then()
                .body(matchesJsonSchema(new File("userresponse.json")))
                .assertThat()
                .statusCode(201)
                .extract().response();
        Assert.assertNotNull(response.jsonPath().get("data[0].id[0].name"));
    }
}
