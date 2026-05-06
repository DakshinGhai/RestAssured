package Serialization;

import Serialization.pojo.AddPlace;
import Serialization.pojo.Location;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
public class Main {
    public static void main(String[] args) {

        RestAssured.baseURI= "https://rahulshettyacademy.com";

        AddPlace ap = new AddPlace();
        ap.setAccuracy(50);
        ap.setAddress("29, side layout");
        ap.setLanguage("French-IN");
        ap.setPhone_number("(+91) 989 321 1352");
        ap.setWebsite("https://rahulshettyacademy.com");
        ap.setName("Frontline house");
        List<String> mylist = new ArrayList<String>();
        mylist.add("shoe park");
        mylist.add("shop");
        ap.setTypes(mylist);
        Location l = new Location();
        l.setLat(-37.3432);
        l.setLng(-122.456);
        ap.setLocation(l);

        Response res =given().log().all().queryParam("key", "qaclick123")
                .body(ap)
                .when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response();
        String responseString=res.asString();
        System.out.println(responseString);
        
    }
}
