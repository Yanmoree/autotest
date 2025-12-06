package mandatoryComment.methods;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class lastAction {

    public static void lastAction(String documentId) {

        authReq adminAuth = new authReq();
        String tokenAdmin = adminAuth.Admin();

        String lastAction = JsonFileReader.getJsonFromResources("lastAction.json")
                .replace("{{documentId}}", documentId);

        Response APPROVE_WITH_SIGN = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenAdmin)
                .header("X-Checked-User-Id", "0d175944-7f05-4180-bb60-019317346710")
                .body(lastAction)
                .when()
                .post("https://api.kedodev.e-vo.kz/api/v2/document/action");

        APPROVE_WITH_SIGN.then()
                .statusCode(200);

    }

}
