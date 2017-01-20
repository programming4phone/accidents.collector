package com.programming4phone.accidents.collector.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.programming4phone.accidents.charlotte.wsdl.CMPDAccidents;
import com.programming4phone.accidents.charlotte.wsdl.CMPDAccidentsResponse;

@Component
public class CmpdSoapClient extends WebServiceGatewaySupport{
	
	@Autowired
	private Jaxb2Marshaller jaxb2Marshaller;
	
	/**
	 * Invoke the CMPDAccidents SOAP API.
	 * @return CMPDAccidentsResponse
	 */
	public CMPDAccidentsResponse getAccidents() {

		getWebServiceTemplate().setMarshaller(jaxb2Marshaller);
		getWebServiceTemplate().setUnmarshaller(jaxb2Marshaller);
		
		CMPDAccidents request = new CMPDAccidents();
		
		CMPDAccidentsResponse response = (CMPDAccidentsResponse) getWebServiceTemplate()
				.marshalSendAndReceive(
						"http://maps.cmpd.org/datafeeds/gisservice.asmx",
						request,
						new SoapActionCallback("http://maps.cmpd.org/CMPDAccidents"));

		return response;
	}


}

