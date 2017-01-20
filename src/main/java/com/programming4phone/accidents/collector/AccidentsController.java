package com.programming4phone.accidents.collector;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.programming4phone.accidents.collector.client.Accident;
import com.programming4phone.accidents.collector.client.CmpdService;

@RestController
public class AccidentsController {

	@Autowired
	private CmpdService service;
	
	/**
	 * Retrieve traffic accident data from the CMPD SOAP API and return it in a condensed format. 
	 * The relevant HTTP headers to enable CORS are also returned, so that Javascript frameworks 
	 * (like Angular) can invoke this web service.
	 * @return @return ResponseEntity containing a List of Accident objects and the relevant Http Status code
	 */
	@RequestMapping(value={"/accidents"}, method=RequestMethod.GET,produces="application/json")
	public ResponseEntity<List<Accident>> getAccidents() {

		List<Accident>  accidents = service.getAccidentList("CharlotteAccidents");
		ResponseEntity<List<Accident>> responseEntity = new ResponseEntity<List<Accident>>(accidents, HttpStatus.OK);
	    return responseEntity;
	}
	
}
