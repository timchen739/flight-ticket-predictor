package com.flight.ticket.predictor;

import entity.ModelInput;
import entity.ModelOutput;
import interfaces.Predictor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import service.FlightTicketPredictor;

@SpringBootTest
class FlightTicketPredictorApplicationTests {

	private Predictor predictor;

	@BeforeEach
	void init() {
		predictor = new FlightTicketPredictor();
	}

//	@Test
//	void lowestPriceShouldNotBeZero() {
//		ModelInput input = new ModelInput.Builder().defaultInputOne();
//		ModelOutput output = predictor.predict(input);
//
//		Assertions.assertTrue(output.getLowestPrice() > 0);
//	}

}
