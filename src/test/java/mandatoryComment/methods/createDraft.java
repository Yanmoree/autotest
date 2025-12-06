package mandatoryComment.methods;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class createDraft {


    public static String autoNumberDraft() {

        // ЛОГИН ПОД АДМИНОМ

        authReq adminAuth = new authReq();
        String tokenAdmin = adminAuth.Admin();

        // ЗАПРОС НА ПОЛУЧЕНИЕ НОМЕРА ДОКУМЕНТА

        Response numberGeneration = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenAdmin)
                .header("X-Checked-User-Id", "0d175944-7f05-4180-bb60-019317346710")
                .queryParam("documentTemplateId", "25282059-95ba-4177-b235-4df29c2f9a3f")
                .when()
                .get("https://api.kedodev.e-vo.kz/api/v1/document/template/number-gen");

        numberGeneration.then()
                .statusCode(200);

        // ПОЛУЧЕНИЕ НОМЕРА ДОКУМЕНТА

        String documentNumber = numberGeneration.jsonPath().getString("documentNumber");

        // ЗАМЕНЯЮ НОМЕР ДОКУМЕНТА В JSON

        String draftBody = JsonFileReader.getJsonFromResources("draftBody.json")
                .replace("{{documentNumber}}", documentNumber);

        // СОЗДАНИЕ ЧЕРНОВИКА

        Response createDraft = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenAdmin)
                .header("X-Checked-User-Id", "0d175944-7f05-4180-bb60-019317346710")
                .body(draftBody)
                .when()
                .post("https://api.kedodev.e-vo.kz/api/v1/document/feed/draft");

        createDraft.then()
                .statusCode(200);

        String draftId = createDraft.jsonPath().getString("draftId");

        // ЗАМЕНЯЮ АЙДИШНИК В JSON

        String actionBody = JsonFileReader.getJsonFromResources("actionBody.json")
                .replace("{{draftId}}", draftId);

        return actionBody;

    }

}
