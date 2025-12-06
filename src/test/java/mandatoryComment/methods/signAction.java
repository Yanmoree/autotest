package mandatoryComment.methods;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class signAction {


    @Step("Подписание документа")
    public static void signAction(String documentId) {

        authReq backendAuth = new authReq();
        String tokenBackend = backendAuth.kedoBackend();

        String signAction = JsonFileReader.getJsonFromResources("signAction.json")
                .replace("{{documentId}}", documentId);

        Response APPROVE_WITH_SIGN = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenBackend)
                .header("X-Checked-User-Id", "c47438cd-e06f-4a11-bdba-b5fbd2f1cc6e")
                .body(signAction)
                .when()
                .post("https://api.kedodev.e-vo.kz/api/v2/document/action");

        APPROVE_WITH_SIGN.then()
                .statusCode(200);

    }

}
