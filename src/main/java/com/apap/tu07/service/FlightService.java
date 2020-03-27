package com.apap.tu07.service;

import java.util.List;
import java.util.Optional;

import com.apap.tu07.model.FlightModel;
import com.apap.tu07.model.PilotModel;

/**
 * FlightService
 */
public interface FlightService {
    
    void deleteByFlightNumber(String flightNumber);

    Optional<FlightModel> getFlightDetailByFlightNumber(String flightNumber);

	FlightModel addFlight(FlightModel flight);

	Optional<FlightModel> getFlightById(long flightId);

	void updateFlight(long flightId, FlightModel flight);
	
	List<FlightModel> getAllFlight();


}