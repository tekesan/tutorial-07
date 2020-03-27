package com.apap.tu07.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;


@JsonIgnoreProperties (ignoreUnknown = true)
public class AirportDetail {

	private String status;
	
	@JsonProperty ("valid-until")
	private Date validUntil;
	
	public void setStatus (String status) {
		this.status = status;
	}
	
	public void setValidUntil (Date validUntil) {
		this.validUntil = validUntil;
	}

	public String getStatus() {
		return status;
	}

	public Date getValidUntil() {
		return validUntil;
	}

}
