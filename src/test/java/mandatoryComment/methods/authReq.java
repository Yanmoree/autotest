package mandatoryComment;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class authReq {

    @Test

    public String Admin() {

        Response authAdmin = RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("client_id", "kedo")
                .formParam("username", "ya_starostin")
                .formParam("password", "AA123123aa!@")
                .formParam("grant_type", "password")
                .when()
                .post("https://keycloak-dev.e-vo.kz/realms/docrobot/protocol/openid-connect/token");

        authAdmin.then()
                .statusCode(200);

        String tokenAdmin = authAdmin.jsonPath().getString("access_token");
        return tokenAdmin;

    }

    @Test

    public void kedoFrontend() {

        Response authFrontend = RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("client_id", "kedo")
                .formParam("username", "kedofrontend")
                .formParam("password", "AA123123aa!@")
                .formParam("grant_type", "password")
                .when()
                .post("https://keycloak-dev.e-vo.kz/realms/docrobot/protocol/openid-connect/token");

        authFrontend.then()
                .statusCode(200);

        String tokenFrontend = authFrontend.jsonPath().getString("access_token");
        System.out.println("TokenFrontend: " + tokenFrontend);

    }

    @Test

    public void kedoBackend() {

        Response authBackend = RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("client_id", "kedo")
                .formParam("username", "kedobackend")
                .formParam("password", "AA123123aa!@")
                .formParam("grant_type", "password")
                .when()
                .post("https://keycloak-dev.e-vo.kz/realms/docrobot/protocol/openid-connect/token");

        authBackend.then()
                .statusCode(200);

        String tokenBackend = authBackend.jsonPath().getString("access_token");
        System.out.println("TokenFrontend: " + tokenBackend);

    }

}
