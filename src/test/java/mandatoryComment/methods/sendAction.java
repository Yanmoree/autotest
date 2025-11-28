package mandatoryComment;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class sendAction {

    @Test

    public String sendWithCertificate() {

        // ЛОГИРУЮСЬ ПОД АДМИНОМ

        authReq adminAuth = new authReq();
        String tokenAdmin = adminAuth.Admin();

        // ПОЛУЧАЮ ТЕЛО ОТПРАВКИ

        createDraft body = new createDraft();
        String actionBody = body.autoNumberDraft();

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

        // ПОДКЛЮЧАЮ СЕРТИФИКАТ

        String sendWithCertificate = JsonFileReader.getJsonFromResources("sendWithCertificate.json")

        Response send = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenAdmin)
                .header("X-Checked-User-Id", "0d175944-7f05-4180-bb60-019317346710")
                .body(sendWithCertificate)
                .when()
                .post("https://api.kedodev.e-vo.kz/api/v1/document/send");


        send.then()
                .statusCode(200);

    }

}
