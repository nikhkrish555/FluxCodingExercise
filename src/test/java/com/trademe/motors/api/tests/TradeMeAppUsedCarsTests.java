package com.trademe.motors.api.tests;

import com.trademe.motors.api.base.BaseTest;
import com.trademe.motors.api.constants.TradeMeAppConstants;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static io.restassured.RestAssured.given;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TradeMe_App_Used_Cars_Tests extends BaseTest {

    private Response getUsedMotorApiResponse() {
        return given()
                .accept(ContentType.JSON)
                .auth()
                .oauth(TradeMeAppConstants.GET_OAUTH_CONSUMER_KEY, TradeMeAppConstants.GET_OAUTH_CONSUMER_SECRET, TradeMeAppConstants.GET_OAUTH_TOKEN, TradeMeAppConstants.GET_OAUTH_TOKEN_SECRET)
                .when()
                .get(TradeMeAppConstants.GET_USED_MOTORS_V1)
                .then()
                .log()
                .ifValidationFails()
                .statusCode(200)
                .extract()
                .response();
    }

    private List<Map<String, String>> getCategories() {
        Response response = getUsedMotorApiResponse();
        return response.jsonPath().getList("FoundCategories");
    }

    @Test
    void When_Used_Cars_Api_Is_Invoked_Then_Return_Named_Brands_Of_Used_Cars() {
        List<Map<String, String>> categories = getCategories();
        System.out.println(String.format("%d brands of used cars are available:", categories.size()));
        for (Map<String, String> category : categories) System.out.println(category.get("Name"));
    }

    @Test
    void When_Used_Cars_Api_Is_Invoked_Then_Return_Number_Of_Kia_Cars_If_Exists() {
        boolean carExists = false;
        List<Map<String, String>> categories = getCategories();
        for (Map<String, String> category : categories)
            if (carExists = category.get("Name").equalsIgnoreCase("kia")) {
                System.out.println(String.format("Kia car exists and current number of Kia cars listed: %s", category.get("Count")));
                break;
            }
        if (!carExists)
            System.out.println("Kia car does not exist in the used cars list.");
    }

    @Test
    void When_Used_Cars_Api_Is_Invoked_Verify_Hispano_Suiza_Brand_Does_Not_Exist() {
        String brand = "Hispano Suiza";
        List<Map<String, String>> categories = getCategories();
        boolean carExists = false;
        for (Map<String, String> category : categories)
            if (carExists = category.get("Name").equalsIgnoreCase(brand))
                break;
        assertThat(carExists).as("Expected {%s} to be not present in the list, however it is present.", brand).isFalse();
    }
}
