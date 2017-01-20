package com.programming4phone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.programming4phone.accidents.charlotte.wsdl.ACCIDENTS;
import com.programming4phone.accidents.charlotte.wsdl.ArrayOfACCIDENTS;
import com.programming4phone.accidents.charlotte.wsdl.CMPDAccidents;
import com.programming4phone.accidents.charlotte.wsdl.CMPDAccidentsResponse;

@SpringBootApplication
@EnableCaching
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean 
	public Jaxb2Marshaller jaxb2Marshaller() { 
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller(); 
 		marshaller.setClassesToBeBound(ACCIDENTS.class, ArrayOfACCIDENTS.class, CMPDAccidents.class, CMPDAccidentsResponse.class); 
		return marshaller; 
	} 
}
