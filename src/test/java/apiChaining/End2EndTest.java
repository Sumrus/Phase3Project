package apiChaining;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class End2EndTest {
	
	Response response;
	
	String BaseURI = "http://18.205.244.116:8088/employees/";

	
	
	@Test
	public void test1() {
		
		
		response = GetMethodAllEmpl();
		Assert.assertEquals(response.getStatusCode(), 200);	
		System.out.println("Status Code: " + response.getStatusCode());
		
		response = CreateEmpl("Ankit", "Dhayni", "100000", "adhayni@gmail.com");
		Assert.assertEquals(response.getStatusCode(), 201);
		JsonPath Jpath = response.jsonPath(); 
		
		int emp_id = Jpath.get("id"); 
		
		System.out.println("id: "+ " " + emp_id); 
		
		response = UpdateEmpl(emp_id, "Sumit", "Rusia", "1000000", "sumit.rusia3@gmail.com");
		Assert.assertEquals(response.getStatusCode(), 200);	
		System.out.println("Status Code: " + response.getStatusCode());
		
		
		Jpath = response.jsonPath();
		Assert.assertEquals(Jpath.get("firstName"), "Sumit");
		
		response = DeleteEmpl(emp_id);
		Assert.assertEquals(response.getStatusCode(), 200);	
		System.out.println("Status Code: " + response.getStatusCode());
		Assert.assertEquals(response.getBody().asString(), "");
		System.out.println("Response Body: " + response.getBody().asString());
		
		response =  GetDeletedEmpl(emp_id);
		Assert.assertEquals(response.getStatusCode(), 400);	
		System.out.println("Status Code: " + response.getStatusCode());
		Assert.assertEquals(response.jsonPath().get("message"), "Entity Not Found");
		System.out.println("Response Body: " + response.getBody().asString());
		
			
		}


	public Response GetMethodAllEmpl() {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI = BaseURI;
		
		//Creating Request
		
		RequestSpecification request = RestAssured.given();
		
		//To capture the response need to create obj of it
		
		Response response = request.get();
		
		return response;
		
		
	}
	
	public Response CreateEmpl(String firstName, String lastName, String salary, String email) {
		// TODO Auto-generated method stub
		 
		RestAssured.baseURI = BaseURI;
		
		//
		
		RequestSpecification request = RestAssured.given();
		
		//Creating Map
		
		Map<String,Object> MapObj = new HashMap<String,Object>();
				
				MapObj.put("firstName", firstName);
				MapObj.put("lastName", lastName);
				MapObj.put("salary", salary);
				MapObj.put("email", email);
				
				// Need to mention type of data 
				
		Response response = request.contentType(ContentType.JSON)
									.accept(ContentType.JSON)
									.body(MapObj)
									.post();
				return response;		
							
		
	}
	
	 public Response UpdateEmpl(int emp_id, String firstName, String lastName, String salary, String email) {
		// TODO Auto-generated method stub
		 
		 RestAssured.baseURI = BaseURI;
			
		RequestSpecification request = RestAssured.given();

		Map<String,Object> MapObj = new HashMap<String,Object>();
				
				MapObj.put("firstName", firstName);
				MapObj.put("lastName", lastName);
				MapObj.put("salary", salary);
				MapObj.put("email", email);
				
				// Need to mention type of data 
				
		Response response = request.contentType(ContentType.JSON)
									.accept(ContentType.JSON)
									.body(MapObj)
									.put("/"+ emp_id);
				return response;		
		
		
	}
	
	public Response DeleteEmpl(int emp_id) {
		// TODO Auto-generated method stub
		
		 RestAssured.baseURI = BaseURI;
			
		 RequestSpecification request = RestAssured.given();
		 
		 Response response = request.delete("/" + emp_id);
		 
		 return response;	
		
	}
	
	public Response GetDeletedEmpl(int emp_id) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI = BaseURI;
		
		//Creating Request
		
		RequestSpecification request = RestAssured.given();
		
		//To capture the response need to create obj of it
		
		Response response = request.get("/" + emp_id);
		
		return response;
		
		
	}
	

}
