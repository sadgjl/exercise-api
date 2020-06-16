package stepDefinitions;

import Model.Customer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class StepDefinitions {
	private String apiPath = "http://v2eok.mocklab.io/customer";
	private String apiPathCustomer = "/a";
	private Customer customer;
	private Response response;

	@Given("Api service")
	public void api_service() {
		RestAssured.baseURI = apiPath + apiPathCustomer;
	}

	@When("User call service  with GET request")
	public void user_call_service_with_GET_request() {
		//create http GET request
		RequestSpecification httpRequest = RestAssured.given();
		response = httpRequest.request(Method.GET);
	}

	@Then("The API call got success with status code {int}")
	public void the_API_call_got_success_with_status_code(Integer expectedStatusCode) {
		// check that status code is 200, otherwise everything below doesn't make sense
		int expectedCode = expectedStatusCode;
		Assert.assertEquals(expectedCode, response.getStatusCode());

		// get JSON body from response
		String responseBody = response.getBody().asString();

		//System.out.println("Response Body is =>  " + responseBody);

		//convert JSON object to POJO class
		customer = new Gson().fromJson(responseBody, Customer.class);
		//System.out.println(customer);
	}

	@Then("id [int] greater than {int}")
	public void id_int_greater_than(Integer id) {
		//validate customer ID, should be > 0
		Assert.assertTrue( customer.getId() > id,
				"Id is less than zero");
	}

	@Then("name [String] more than {int} alpha characters")
	public void name_String_more_than_alpha_characters(Integer expectedMaxCharacters) {
		//get customer Name
		String name = customer.getName();

		//name should contain letters only
		boolean isNameLettersOnly = name.matches("^[a-zA-Z]+$");

		Assert.assertTrue(isNameLettersOnly && !(name.length() > expectedMaxCharacters),
				"Name is not alpha characters only or more than 10 characters");
	}

	@Then("last [String] more than {int} alpha characters")
	public void last_String_more_than_alpha_characters(Integer expectedMaxCharacters) {
		//get customer lastName
		String lastName = customer.getLast();

		//lastName should contain letters only
		boolean isLastNameLettersOnly = lastName.matches("^[a-zA-Z]+$");

		Assert.assertTrue(isLastNameLettersOnly && !(lastName.length() > expectedMaxCharacters),
				"Name is not alpha charachters only or more than 10 characters");
	}

	@Then("age [int] must be integer and {int} > age < {int}")
	public void age_int_must_be_integer_and_age(Integer lowerBoundaryAgeExpected, Integer upperBoundaryAgeExpected) {
		//get customer age
		int ageActual = customer.getAge();

		Assert.assertTrue((lowerBoundaryAgeExpected < ageActual) && (ageActual < upperBoundaryAgeExpected),
				"Age must be between 0 and 120");
	}

	@Then("gender [String] must only be {string} or {string}")
	public void gender_String_must_only_be_or(String genderExpectedM, String genderExpectedF) {
		//get customer gender
		String genderActual = customer.getGender();

		Assert.assertTrue(genderExpectedF.equalsIgnoreCase(genderActual) || genderExpectedM.equalsIgnoreCase(genderActual),
				"Gender must only be M or F");
	}

	@Then("response time less than {int} ms")
	public void response_time_less_than_ms(Integer resTime) {
		//check response time in ms
		Long resTimeLong = Long.valueOf(resTime);
		Assert.assertTrue(response.timeIn(TimeUnit.MILLISECONDS) < resTimeLong,
				"response time is below " + resTimeLong);
	}
}
