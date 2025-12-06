package mandatoryComment;

import io.qameta.allure.*;
import mandatoryComment.methods.*;
import org.assertj.core.error.ShouldHaveRootCauseInstance;
import org.testng.annotations.*;
import static org.testng.Assert.*;

@Epic("Рабоча с документами")
@Feature("Создание и согласование документа")
@Story("Прохождение маршрута документа с обязательным комментарием к шагу")

public class mandatoryTrue {

    @Test
    @Description("Тест создает документ, проходит этап согласования с подписанием и принимается получателем")

    public void create() {


        // СОЗДАЮ ЧЕРНОВИК С АВТОМАТИЧЕСКИМ НОМЕРОМ

        Allure.step("Создание черновика документа");
        createDraft createDraft = new createDraft();
        String actionBody = createDraft.autoNumberDraft();

        // ОТПРАВЛЯЮ ДОКУМЕНТ С ПОДПИСЬЮ

        Allure.step("Отправка документа с подписью");
        sendAction sendAction = new sendAction();
        String documentId = sendAction.sendWithCertificate(actionBody);
        Allure.parameter("Id документа", documentId);

        // СОГЛАСОВАНИЕ ДОКУМЕНТА

        Allure.step("Согласование документа пользователем kedo frontend");
        approveAction approveAction = new approveAction();
        approveAction.approve_with_sign(documentId);

        // ПОДПИСАНИЕ ДОКУМЕНТА

        Allure.step("Подписание документа пользователем kedo backend");
        signAction signAction = new signAction();
        signAction.signAction(documentId);

        // ПОЛУЧЕНИЕ ДОКУМЕНТА

        Allure.step("Получение документа");
        lastAction lastAction = new lastAction();
        lastAction.lastAction(documentId);
    }

}

