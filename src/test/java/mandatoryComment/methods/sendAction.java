package mandatoryComment.methods;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class sendAction {

    @Step("Отправка документа с подписью")
    public static String sendWithCertificate(String actionBody) {

        // ЛОГИРУЮСЬ ПОД АДМИНОМ

        authReq adminAuth = new authReq();
        String tokenAdmin = adminAuth.Admin();


        // ОТПРАВЛЯЮ ЗАПРОС НА ОТПРАВКУ

        Response action = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenAdmin)
                .header("X-Checked-User-Id", "0d175944-7f05-4180-bb60-019317346710")
                .body(actionBody)
                .when()
                .post("https://api.kedodev.e-vo.kz/api/v1/document/feed/draft/action");

        action.then()
                .statusCode(200);

        String draftId = action.jsonPath().getString("draftId");

        // ПОДКЛЮЧАЮ СЕРТИФИКАТ

        String sendWithCertificate = JsonFileReader.getJsonFromResources("sendWithCertificate.json")
                .replace("{{draftId}}", draftId);

        Response send = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenAdmin)
                .header("X-Checked-User-Id", "0d175944-7f05-4180-bb60-019317346710")
                .body(sendWithCertificate)
                .when()
                .post("https://api.kedodev.e-vo.kz/api/v1/document/send");


        send.then()
                .statusCode(200);


        String documentFeedId = send.jsonPath().getString("id");

        Response getDocumnetId = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenAdmin)
                .header("X-Checked-User-Id", "0d175944-7f05-4180-bb60-019317346710")
                .queryParam("documentFeedId", documentFeedId)
                .when()
                .get("https://api.kedodev.e-vo.kz/api/v1/document");

        getDocumnetId.then()
                .statusCode(200);

        String documentId = getDocumnetId.jsonPath().getString("documentId");

        return documentId;

    }
}
