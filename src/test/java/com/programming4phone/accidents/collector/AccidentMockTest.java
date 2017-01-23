package com.programming4phone.accidents.collector;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;
import javax.xml.transform.stream.StreamSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.junit4.SpringRunner;

import com.programming4phone.accidents.charlotte.wsdl.CMPDAccidentsResponse;
import com.programming4phone.accidents.collector.client.Accident;
import com.programming4phone.accidents.collector.client.CmpdSoapClient;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AccidentMockTest {

	@Value("${local.server.port}")   
	int port;
	
	@MockBean
	CmpdSoapClient cmpdSoapClient;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	Jaxb2Marshaller jaxb2Marshaller;
	
	@Test
	public void test() {
		CMPDAccidentsResponse response = (CMPDAccidentsResponse) jaxb2Marshaller.unmarshal(new StreamSource(new File(".\\src\\test\\resources\\accidentsSuccess.xml")));
		when(cmpdSoapClient.getAccidents()).thenAnswer(i->response);
		
		ResponseEntity<List<Accident>> entity =
		        restTemplate.exchange("http://localhost:" + Integer.toString(port) + "/accidents",
		                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Accident>>() {});
		
		assertTrue(entity.getStatusCode().equals(HttpStatus.OK));
		assertTrue(entity.getHeaders().containsKey("Access-Control-Allow-Origin"));
		List<Accident> accidents = entity.getBody();
		assertNotNull(accidents);
		assertTrue(accidents.size()>0);
		assertTrue(accidents.stream().filter(a-> "R0122130801".equals(a.getAccidentId())).findFirst().isPresent());
		assertTrue(accidents.stream().filter(a-> "35.288957".equals(a.getLatitude())).findFirst().isPresent());
		assertTrue(accidents.stream().filter(a-> "-80.886855".equals(a.getLongitude())).findFirst().isPresent());
	}

}
