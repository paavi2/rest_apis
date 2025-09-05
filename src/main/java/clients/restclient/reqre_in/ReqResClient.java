package clients.restclient.reqre_in;

import clients.restclient.RestClient;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ReqResClient extends RestClient {

    String baseUri = "https://reqres.in/";

    String POST_CREATE_USERS = "/api/users";

    String GET_LIST_USERS = "/api/users?page=2";

    String PUT_UPDATE = "/api/users/2";

    String PATCH = "/api/users/2";

    String DELETE = "/api/users/2";

    String put_req_body = "{\n" +
            "    \"name\": \"morpheus\",\n" +
            "    \"job\": \"leader\"\n" +
            "}";

    String post_req_body = "{\n" +
            "    \"name\": \"morpheus\",\n" +
            "    \"job\": \"leader\"\n" +
            "}";

    String patch_req_body = "{\n" +
            "    \"name\": \"morpheus\",\n" +
            "    \"job\": \"zion resident\"\n" +
            "}\n";


    public Response createUser(){

        return RestAssured
                .given()
                .header("x-api-key", "reqres-free-v1")  // ✅ custom header
                .spec(getRequestSpec(baseUri))
                .body(post_req_body)
                .when()
                .post(POST_CREATE_USERS);
    }

}
