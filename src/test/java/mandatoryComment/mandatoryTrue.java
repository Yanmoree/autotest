package mandatoryComment;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class mandatoryTrue {

    @Test

    public void create() {

        // ПОЛУЧАЮ ТОКЕН ПОД АДМИНОМ

        authReq adminAuth = new authReq();
        String tokenAdmin = adminAuth.Admin();

        // ЗАПРАШИВАЮ НОМЕР ДЛЯ ДОКУМЕНТА

        Response numberGeneration = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenAdmin)
                .header("X-Checked-User-Id", "0d175944-7f05-4180-bb60-019317346710")
                .queryParam("documentTemplateId", "4e2e70ab-1765-4a02-a5ed-8b4a12aa4756")
                .when()
                .get("https://api.kedodev.e-vo.kz/api/v1/document/template/number-gen");

        numberGeneration.then()
                .statusCode(200);

        // ЧИТАЮ ФАЙЛ JSON И ПОЛУЧАЮ ЕГО ЗНАЧЕНИЯ

        String documentNumber = numberGeneration.jsonPath().getString("documentNumber");

        String draftBody = JsonFileReader.getJsonFromResources("draftBody.json")
                .replace("{{documentNumber}}", documentNumber);

        // СОЗДАЮ ЧЕРНОВИК И ПОЛУЧАЮ ЕГО АЙДИШНИК

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

        // ЧИТАЮ ФАЙЛ JSON И ПОЛУЧАЮ ЕГО ЗНАЧЕНИЯ

        String actionBody = JsonFileReader.getJsonFromResources("actionBody.json")
                .replace("{{draftId}}", draftId)
                .replace("{{documentNumber}}", documentNumber);

        // СОХРАНЯЮ ЧЕРНОВИК

        Response action = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenAdmin)
                .header("X-Checked-User-Id", "0d175944-7f05-4180-bb60-019317346710")
                .body(actionBody)
                .when()
                .post("https://api.kedodev.e-vo.kz/api/v1/document/feed/draft/action");

        action.then()
                .statusCode(200);

        // ЧИТАЮ СЕРТИФИКАТ

        String sendWithCertificate = JsonFileReader.getJsonFromResources("sendWithCertificate.json")
                .replace("{{draftId}}", draftId);

        // ОТПРАВЛЯЮ ДОКУМЕНТ С ПОДПИСЬЮ

        Response send = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenAdmin)
                .header("X-Checked-User-Id", "0d175944-7f05-4180-bb60-019317346710")
                .body(sendWithCertificate)
                .when()
                .post("https://api.kedodev.e-vo.kz/api/v1/document/send");


        send.then()
                .statusCode(200)

    }

}

