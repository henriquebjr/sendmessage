package com.henriquebjr.sendmessage;

import com.henriquebjr.sendmessage.api.v1.dto.TenantDTO;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;

@QuarkusTest
public class TenantResourceIT {

    @Test
    public void testListTenants() {
        /*
        given()
                .header("Authorization", "Basic YWRtaW46MTIz")
                .when().get("/tenants")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("items", hasSize(1))
                .body("items[0].active", equalTo(true));

         */

        /*
        TenantListDTO result = given()
                .header("Authorization", "Basic YWRtaW46MTIz")
                .when().get("/tenants")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(TenantListDTO.class);

         */

        /*
        List<TenantDTO> result = given()
                .header("Authorization", "Basic YWRtaW46MTIz")
                .when().get("/tenants")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(List.class);

         */

        given()
                .header("Authorization", "Basic YWRtaW46MTIz")
                .when().get("/tenants")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

}
