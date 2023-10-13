package api.post;

import api.BaseTest;
import api.clients.BoardRestTestClient;
import api.clients.CardTestRestClient;
import api.clients.ChecklistTestRestClient;
import api.clients.ListTestRestClient;
import api.dto.CreateChecklistResponse;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static api.Const.BASE_URL;

public class CreateChecklistOnBoardTest extends BaseTest {

    public String ID_BOARD;
    public String ID_LIST;
    public String ID_CARD;

    private BoardRestTestClient boardClient = new BoardRestTestClient(BASE_URL);
    private ListTestRestClient listClient = new ListTestRestClient(BASE_URL);
    private CardTestRestClient cardClient = new CardTestRestClient(BASE_URL);
    private ChecklistTestRestClient checklistClient = new ChecklistTestRestClient(BASE_URL);

    @BeforeMethod
    public void stepsCreation() {
        ID_BOARD = boardClient.createNewBoard(Map.of("name", "My test board")).getId();
        ID_LIST = listClient.createList("My test list", ID_BOARD).getId();
        ID_CARD = cardClient.createCard(ID_LIST).getId();
    }

    @Test
    @Description(" AS2-9 [POST] Positive : Create a checklist /checklist")
    public void createChecklist() {

        CreateChecklistResponse checklistResponse = checklistClient.createChecklist(ID_CARD);

        Assert.assertNotNull(checklistResponse, "Checklist creation response is null");
        Assert.assertNotNull(checklistResponse.getId(), "Checklist ID is null");
        Assert.assertNotNull(checklistResponse.getName(), "Checklist name is null");

    }

    @AfterMethod
    public void delete() {
        boardClient.deleteBoardIfExist(ID_BOARD);
    }
}
