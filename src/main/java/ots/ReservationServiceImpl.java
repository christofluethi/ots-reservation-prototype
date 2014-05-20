package ots;

import javax.persistence.EntityManager;

import ots.seating.DefaultSeatingStrategy;
import ots.seating.SeatingStrategy;

public class ReservationServiceImpl implements ReservationService {

	private volatile SeatingStrategy strategy;

	public ReservationServiceImpl(EntityManager entityManager) {
		this.strategy = new DefaultSeatingStrategy();
	}
		
	public Seat[] makeReservation(String category, int numberOfSeats) {
		/* 
		 * decide here if you should switch strategy 
		 * maybe check total free seats and switch strategy if we are below ~40
		 */

		return strategy.makeReservation(category, numberOfSeats);
	}
}
