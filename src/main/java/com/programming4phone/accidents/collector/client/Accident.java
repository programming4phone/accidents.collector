package com.programming4phone.accidents.collector.client;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import com.programming4phone.accidents.charlotte.wsdl.ACCIDENTS;


public class Accident implements Serializable {
	private static final long serialVersionUID = 80713066111763141L;
	private String accidentId;
	private String datetimeAdd;
	private String address;
	private String latitude;
	private String longitude;
	private String accidentDescription;
	public static final SimpleTimeZone edt;
	static 
	{
		// EST is GMT-5
		String[] ids = TimeZone.getAvailableIDs(-5 * 60 * 60 * 1000);
		edt = new SimpleTimeZone(-5 * 60 * 60 * 1000, ids[0]);

		  /* set up rules for Daylight Saving Time
		   * As of 2007, in the USA Daylight Saving Time
		   * begins at 2:00AM on the second Sunday of March
		   * and ends at 2:00AM on the first Sunday of November
		  */
		edt.setStartRule(Calendar.MARCH, 2, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
		edt.setEndRule(Calendar.NOVEMBER, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
	}
	
	public Accident(){}
	
	public Accident(ACCIDENTS cmpdAccident) {
		this.accidentId = cmpdAccident.getEVENTNO();
		GregorianCalendar tempCalendar = cmpdAccident.getDATETIMEADD().toGregorianCalendar();
		tempCalendar.setTimeZone(edt);
		this.datetimeAdd = tempCalendar.getTime().toString();
		this.address = cmpdAccident.getADDRESS();
		this.latitude = cmpdAccident.getLATITUDE().toPlainString();
		this.longitude = cmpdAccident.getLONGITUDE().toPlainString();
		this.accidentDescription = cmpdAccident.getEVENTDESC();
	}
	
	
	public String getAccidentId()
	{
		return accidentId;
	}
	public void setAccidentId(String accidentId) {
		this.accidentId = accidentId;
	}
	public String getDatetimeAdd() {
		return datetimeAdd;
	}
	public void setDatetimeAdd(String datetimeAdd) {
		this.datetimeAdd = datetimeAdd;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getAccidentDescription() {
		return accidentDescription;
	}

	public void setAccidentDescription(String accidentDescription) {
		this.accidentDescription = accidentDescription;
	}

	@Override
	public boolean equals(Object o){
		boolean result = false;
		
		if(o instanceof Accident){
			if(((Accident)o).getAccidentId().trim().equals(this.getAccidentId().trim())){
				result = true;
			}
		}
		
		return result;
	}

	@Override
	public int hashCode(){
		int result = 17;
		result = 31 * result + accidentId.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "Accident{" +
				"accidentId='" + accidentId + '\'' +
				", accidentDescrption='" + accidentDescription + '\'' +
				", datetimeAdd='" + datetimeAdd + '\'' +
				", address='" + address + '\'' +
				", latitude='" + latitude + '\'' +
				", longitude='" + longitude + '\'' +
				'}';
	}
}
