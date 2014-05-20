package ots.seating;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import ots.Row;
import ots.Seat;

public class FillupSeatingStrategy implements SeatingStrategy {
	private EntityManager entityManager;

	public FillupSeatingStrategy() {
		this.entityManager = Persistence.createEntityManagerFactory("ots").createEntityManager();
	}

	@Override
	public List<Row> getRows(String category, int numberOfSeats) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public Seat[] makeReservation(String category, int numberOfSeats) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
