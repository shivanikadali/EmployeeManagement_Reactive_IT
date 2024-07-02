package com.example.Emp_Det_Reactive;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@SpringBootTest
class EmpDetReactiveApplicationTests {

	String baseuri = "http://localhost:9292/employee";

	@Test
	public void getAll() throws JSONException, IOException {
		String expectedRequest = new String(Files.readAllBytes(Paths.get("src/test/resources/employeesResponse.json")));
		Response res = RestAssured.get(baseuri);
		String actualResponse = res.getBody().asString();
		JSONObject expected = new JSONObject(expectedRequest);
		JSONArray response = new JSONArray(actualResponse);
		JSONObject firstObj = response.getJSONObject(0);
		assertEquals(1, firstObj.getInt("empNo"));
		assertEquals(expected.getString("firstName"), firstObj.getString("firstName"));
		assertEquals(expected.getString("lastName"), firstObj.getString("lastName"));

	}

	@Test
	public void create() throws IOException, JSONException {

		String expectedRequest = new String(Files.readAllBytes(Paths.get("src/test/resources/employees.json")));
		String expectedResponse = new String(
				Files.readAllBytes(Paths.get("src/test/resources/employeesResponse.json")));

		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(expectedRequest)
				.post(baseuri);

		String responseBody = response.getBody().asString();
		JSONObject jsonResponse = new JSONObject(responseBody);
		JSONObject expectedResponse2 = new JSONObject(expectedResponse);

		response.then()
				.assertThat()
				.statusCode(200);
		assertEquals(expectedResponse2.getString("firstName"), jsonResponse.getString("firstName"));
		assertEquals(expectedResponse2.getString("empNo"), jsonResponse.getString("empNo"));
	}
}
