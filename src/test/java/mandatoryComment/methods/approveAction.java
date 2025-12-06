package mandatoryComment.methods;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class approveAction {


    @Step("Согласование документа")
    public static void approve_with_sign(String documentId) {


        // ЛОГИРОВАНИЕ ПОД СОГЛАСОВАНТОМ

        authReq frontendAuth = new authReq();
        String tokenFrontend = frontendAuth.kedoFrontend();

        String approveAction = JsonFileReader.getJsonFromResources("approveAction.json")
                .replace("{{documentId}}", documentId);

        Response APPROVE_WITH_SIGN = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenFrontend)
                .header("X-Checked-User-Id", "167eb5d3-8560-4adb-9530-133ab33a5c65")
                .body(approveAction)
                .when()
                .post("https://api.kedodev.e-vo.kz/api/v2/document/action");

        APPROVE_WITH_SIGN.then()
                .statusCode(200);

    }

}
