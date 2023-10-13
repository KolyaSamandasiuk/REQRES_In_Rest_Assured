package api.clients;

import api.dto.CreateListResponse;
import api.dto.ListsDataResponse;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;

public class ListTestRestClient extends AbstractBaseRestClient {

    public ListTestRestClient(String url) {
        super(url);
    }

    public CreateListResponse createList(Map<String, String> listKeyValue, String boardId) {
        return createList(listKeyValue, boardId, HTTP_OK)
                .as(CreateListResponse.class);
    }

    public Response createList(Map<String, String> listKeyValue, String boardId, int statusCode) {
        return given()
                .spec(requestSpec)
                .queryParams(listKeyValue)
                .when()
                .post("/1/boards/{id}/lists", boardId)
                .then()
                .statusCode(statusCode)
                .extract()
                .response();
    }

    public List<ListsDataResponse> getLists(String boardId) {
        return given()
                .spec(requestSpec)
                .get("/1/boards/{id}/lists", boardId)
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .as(new TypeRef<List<ListsDataResponse>>() {
                });
    }
}
