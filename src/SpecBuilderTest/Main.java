package SpecBuilderTest;

import SpecBuilderTest.pojo.AddPlace;
import SpecBuilderTest.pojo.Location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
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
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
                        .setContentType(ContentType.JSON).build();

        ResponseSpecification  resspec= new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        RequestSpecification res =given().spec(req)
                .body(ap);
        Response response = res.when().post("maps/api/place/add/json")
                .then().spec(resspec).extract().response();
        String responseString=response.asString();
        System.out.println(responseString);
        
    }
}
