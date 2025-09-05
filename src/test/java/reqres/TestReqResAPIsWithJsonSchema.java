package reqres;

import clients.restclient.reqre_in.ReqResClient;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class TestReqResAPIsWithJsonSchema {

    String schemaPath = "src/main/resources/json_schemas/reqres/";

    @Test
    public void verifyCreateUsers(){
        Response resp = new ReqResClient().createUser();
        System.out.println("Output body ::: "+resp.prettyPrint());
        resp.then()
                .body(matchesJsonSchema(new File(schemaPath+"create_users.json")))
                .assertThat()
                .statusCode(201);
    }
}
