package mandatoryComment;

import io.qameta.allure.*;
import mandatoryComment.methods.*;
import org.assertj.core.error.ShouldHaveRootCauseInstance;
import org.testng.annotations.*;
import static org.testng.Assert.*;

@Epic("Рабоча с комментариями документов")
@Feature("Создание и согласование документа")
@Story("Прохождение маршрута документа с обязательным комментарием к шагу")

public class mandatoryTrue {

    @Test
    @Description("Тест проходит все этапы документа подкрепляя комментарий")

    public void create() {


        // СОЗДАЮ ЧЕРНОВИК С АВТОМАТИЧЕСКИМ НОМЕРОМ

        createDraft createDraft = new createDraft();
        String actionBody = createDraft.autoNumberDraft();

        // ОТПРАВЛЯЮ ДОКУМЕНТ С ПОДПИСЬЮ

        sendAction sendAction = new sendAction();
        String documentId = sendAction.sendWithCertificate(actionBody);

        // СОГЛАСОВАНИЕ ДОКУМЕНТА

        approveAction approveAction = new approveAction();
        approveAction.approve_with_sign(documentId);

        // ПОДПИСАНИЕ ДОКУМЕНТА

        signAction signAction = new signAction();
        signAction.signAction(documentId);

        // ПОЛУЧЕНИЕ ДОКУМЕНТА

        lastAction lastAction = new lastAction();
        lastAction.lastAction(documentId);
    }

}

