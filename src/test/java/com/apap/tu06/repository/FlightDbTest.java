package com.apap.tu06.repository;

import static org.junit.Assert.assertThat;
import java.sql.Date;
import java.util.Optional;

import com.apap.tu07.model.FlightModel;
import com.apap.tu07.model.PilotModel;

import com.apap.tu07.repository.FlightDb;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FlightDbTest {
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private FlightDb flightDb;


	@Test
	public void whenFindByFlightNumber_thenReturnFlight() {
		//given
		PilotModel pilotModel = new PilotModel();
		pilotModel.setLicenseNumber ("1234");
		pilotModel.setName ("Anto");
		pilotModel.setFlyHour(50);
		entityManager.persist(pilotModel);
		entityManager.flush();
		FlightModel flightModel = new FlightModel();
		flightModel.setFlightNumber("I765");
		flightModel.setOrigin("Jakarta");
		flightModel.setDestination("Bali");
		flightModel.setTime(new Date(new java.util.Date().getTime()));
		flightModel.setPilot(pilotModel);
		entityManager.persist(flightModel);
		entityManager.flush();
		
		//when
		Optional<FlightModel> found = flightDb.findByFlightNumber(flightModel.getFlightNumber());
		
		//Then
		assertThat(found.get(), Matchers.notNullValue()); //Check if not null
		assertThat(found.get(), Matchers.equalTo(flightModel)); //Check if same
		
	}
}
