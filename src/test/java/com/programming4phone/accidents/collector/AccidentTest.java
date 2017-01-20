package com.programming4phone.accidents.collector;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.programming4phone.accidents.collector.client.Accident;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AccidentTest {

	@Value("${local.server.port}")   
	int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void test() {

		ResponseEntity<List<Accident>> entity; 
		List<Accident> accidents;
		
		entity =
		        restTemplate.exchange("http://localhost:" + Integer.toString(port) + "/accidents",
		                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Accident>>() {});
		
		assertTrue(entity.getStatusCode().equals(HttpStatus.OK));
		assertTrue(entity.getHeaders().containsKey("Access-Control-Allow-Origin"));
		accidents = entity.getBody();
		assertNotNull(accidents);
		
		/*
		 * The next 2 web service invocations should pull from the cache instead of
		 * hitting the CMPD web site. Unfortunately this can only be verified by viewing
		 * the console log. 
		 */
		entity =
		        restTemplate.exchange("http://localhost:" + Integer.toString(port) + "/accidents",
		                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Accident>>() {});
		
		assertTrue(entity.getStatusCode().equals(HttpStatus.OK));
		assertTrue(entity.getHeaders().containsKey("Access-Control-Allow-Origin"));
		accidents = entity.getBody();
		assertNotNull(accidents);
		
		entity =
		        restTemplate.exchange("http://localhost:" + Integer.toString(port) + "/accidents",
		                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Accident>>() {});
		
		assertTrue(entity.getStatusCode().equals(HttpStatus.OK));
		assertTrue(entity.getHeaders().containsKey("Access-Control-Allow-Origin"));
		accidents = entity.getBody();
		assertNotNull(accidents);
		
	}

}
