package files;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.File;

import static io.restassured.RestAssured.*;

public class BugTest {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String authToken = dotenv.get("JIRA_AUTH");
        RestAssured.baseURI = "https://dakshinghai-1.atlassian.net/";
        String createIssueResponse=given().header("Content-Type","application/json")
                .header("Authorization","Basic" + authToken)
                .body("{\n" +
                        "    \"fields\": {\n" +
                        "       \"project\":\n" +
                        "       {\n" +
                        "          \"key\": \"SCRUM\"\n" +
                        "       },\n" +
                        "       \"summary\": \"Website items are not working- automation Rest Assured\",\n" +
                        "       \"issuetype\": {\n" +
                        "          \"name\": \"Bug\"\n" +
                        "       }\n" +
                        "   }\n" +
                        "}")
                .log().all()
                .post("rest/api/3/issue").then().log().all().assertThat().statusCode(201)
                .extract().response().asString();
        JsonPath js = new JsonPath(createIssueResponse);
        String issueId= js.getString("id");
        System.out.println("Issue ID: " + issueId);
        //Add attachment
        given().pathParam("issueId", issueId)
                .header("Authorization","Basic "+authToken)
                .header("X-Atlassian-Token","no-check")
                .multiPart("file", new File("C:\\Users\\Dakshin Ghai\\Pictures\\Screenshots\\Screenshot 2024-11-09 235245.png")).log().all()
                .post("rest/api/3/issue/{issueId}/attachments").then().log().all().assertThat().statusCode(200);


    }

}
