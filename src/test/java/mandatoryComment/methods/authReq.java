package mandatoryComment.methods;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class authReq {

    @Step("Авторизация под пользователем: Старостин Ярослав")
    public static String Admin() {

        String username = "ya_starostin";
        String password = "AA123123aa!@";

        Response authAdmin = RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("client_id", "kedo")
                .formParam("username", username)
                .formParam("password", password)
                .formParam("grant_type", "password")
                .when()
                .post("https://keycloak-dev.e-vo.kz/realms/docrobot/protocol/openid-connect/token");

        authAdmin.then()
                .statusCode(200);

        String tokenAdmin = authAdmin.jsonPath().getString("access_token");
        return tokenAdmin;

    }

    @Step("Авторизация под пользователем: kedo Frontend")
    public static String kedoFrontend() {

        String username = "kedofrontendauto";
        String password = "AA123123aa!@";

        Response authFrontend = RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("client_id", "kedo")
                .formParam("username", username)
                .formParam("password", password)
                .formParam("grant_type", "password")
                .when()
                .post("https://keycloak-dev.e-vo.kz/realms/docrobot/protocol/openid-connect/token");

        authFrontend.then()
                .statusCode(200);

        String tokenFrontend = authFrontend.jsonPath().getString("access_token");
        return tokenFrontend;

    }

    @Step("Авторизация под пользователем: kedo Backend")
    public static String kedoBackend() {

        String username = "kedoBackend";
        String password = "AA123123aa!@";

        Response authBackend = RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("client_id", "kedo")
                .formParam("username", username)
                .formParam("password", password)
                .formParam("grant_type", "password")
                .when()
                .post("https://keycloak-dev.e-vo.kz/realms/docrobot/protocol/openid-connect/token");

        authBackend.then()
                .statusCode(200);

        String tokenBackend = authBackend.jsonPath().getString("access_token");
        return tokenBackend;

    }

}
