package com.trademe.motors.api.base;

import com.trademe.motors.api.constants.TradeMeAppConstants;
import io.restassured.RestAssured;
import io.restassured.config.ConnectionConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    @BeforeAll
    public static void setup()
    {
        RestAssured.baseURI = TradeMeAppConstants.GET_BASE_URI;
    }

    @AfterAll
    public static void tearDown()
    {
        RestAssured.config().connectionConfig(ConnectionConfig.connectionConfig().closeIdleConnectionsAfterEachResponse());
    }
}
