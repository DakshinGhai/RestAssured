package files;

import static io.restassured.RestAssured.given;


import java.util.List;


import io.restassured.path.json.JsonPath;

import Deserialization.pojo.Api;
import Deserialization.pojo.GetCourse;


public class ClientCredentialsOauth {



    public static void main(String[] args) throws InterruptedException {

// TODO Auto-generated method stub

        String response =
                given().formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                        .formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
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

