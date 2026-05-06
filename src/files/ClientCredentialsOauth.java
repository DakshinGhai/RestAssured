package files;

import static io.restassured.RestAssured.given;


import java.util.List;


import io.restassured.path.json.JsonPath;

import Deserialization.pojo.Api;
import Deserialization.pojo.GetCourse;
import io.github.cdimascio.dotenv.Dotenv;

public class ClientCredentialsOauth {



    public static void main(String[] args) throws InterruptedException {


        Dotenv dotenv = Dotenv.load();

        String clientId = dotenv.get("CLIENT_ID");
        String clientSecret = dotenv.get("CLIENT_SECRET");
        String response =
                given().formParams("client_id", clientId)
                        .formParams("client_secret", clientSecret)
                        .formParams("grant_type", "client_credentials")
                        .formParams("scope", "trust")
                        .when().log().all()
                        .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

        System.out.println(response);

        JsonPath jsonPath = new JsonPath(response);

        String accessToken = jsonPath.getString("access_token");

        System.out.println(accessToken);

        GetCourse gc=    given()
                .queryParams("access_token", accessToken)
                .when()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                .as(GetCourse.class);

        System.out.println(gc.getLinkedIn());
        System.out.println(gc.getInstructor());

        List<Api> apiCourses = gc.getCourses().getApi();
        for(int i=0; i<apiCourses.size();i++){
            if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
                System.out.println(apiCourses.get(i).getPrice());
                break;
            }
        }
    }




}

