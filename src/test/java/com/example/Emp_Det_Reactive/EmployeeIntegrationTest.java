package com.example.Emp_Det_Reactive;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.config.location=classpath:/application.properties")
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class EmployeeIntegrationTest {

	// actal port are random it is hitting the actual database
	@LocalServerPort
	private int port;

	private String baseUri;

	@BeforeEach
	public void setUp() {
		baseUri = "http://localhost:" + port + "/employee";
	}

	// if we test this case individually it contains 2 records
	// if we run the whole class it contains 3 records bcz it execute after create
	// method() and everytime it is reset the database.
	@Test
	public void getAll() throws JSONException, IOException {
		String expectedRequest = new String(Files.readAllBytes(Paths.get("src/test/resources/employeesResponse.json")));
		JSONObject expected = new JSONObject(expectedRequest);
		Response res = RestAssured.get(baseUri);
		String actualResponse = res.getBody().asString();
		JSONArray response = new JSONArray(actualResponse);
		JSONObject thiredObj = response.getJSONObject(2);
		assertEquals(3, thiredObj.getInt("empNo"));
		assertEquals(expected.getString("firstName"), thiredObj.getString("firstName"));
		assertEquals(expected.getString("lastName"), thiredObj.getString("lastName"));
	}

	@Test
	public void create() throws IOException, JSONException {
		String expectedRequest = new String(Files.readAllBytes(Paths.get("src/test/resources/employees.json")));
		String expectedResponse = new String(
				Files.readAllBytes(Paths.get("src/test/resources/employeesResponse.json")));
		JSONObject expectedResponse2 = new JSONObject(expectedResponse);

		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(expectedRequest)
				.post(baseUri);

		String responseBody = response.getBody().asString();
		JSONObject jsonResponse = new JSONObject(responseBody);

		response.then().assertThat().statusCode(200);

		assertEquals(expectedResponse2.getString("firstName"), jsonResponse.getString("firstName"));
		assertEquals(expectedResponse2.getString("empNo"), jsonResponse.getString("empNo"));
	}
}