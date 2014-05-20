package ots.seating;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import ots.ReservationServiceImpl;
import ots.Row;
import ots.Seat;
import ots.SeatMask;

public class DefaultSeatingStrategy implements SeatingStrategy {
	private EntityManager entityManager;
	private static Logger logger = Logger.getLogger(ReservationServiceImpl.class);
	private static final String getRowsQuery = "SELECT r FROM Row r WHERE r.free >= :free AND r.category = :category";
	
	public DefaultSeatingStrategy() {
		this.entityManager = Persistence.createEntityManagerFactory("ots").createEntityManager();
	}
	
	@Override
	public List<Row> getRows(String category, int numberOfSeats) {
		Query query = entityManager.createQuery(getRowsQuery);
		query.setLockMode(LockModeType.PESSIMISTIC_READ);
		query.setParameter("category", category);
		query.setParameter("free", numberOfSeats);
		return query.getResultList();
	}

	@Override
	public Seat[] makeReservation(String category, int numberOfSeats) {
		EntityTransaction transaction = null;
		try {
			
			transaction = entityManager.getTransaction();
			transaction.begin();
			List<Row> rows = getRows(category, numberOfSeats);
			if (!rows.isEmpty()) {
				Row r = rows.get(0);
				
				SeatMask sa = new SeatMask(r.getState());
				//String oldState = sa.toString();
				int[] seats = sa.reserve(numberOfSeats);
				if(seats.length == numberOfSeats) {
					r.decreaseFree(numberOfSeats);
				
					String newState = sa.toString();
					//logger.info("DEFAULT: Seats "+seats.length+"/"+numberOfSeats+" Category["+category+"/"+r.getCategory()+"] Sector["+r.getSector()+"] Number["+r.getNumber()+"] Free["+r.getFree()+"] State["+oldState+" -> "+newState+"]");
					r.setState(newState);
				} else {
					seats = new int[0];
				}
				transaction.commit();
				
				if(seats.length > 0) {
					Seat[] seat = new Seat[seats.length];
					for (int i = 0; i < seat.length; i++) {
						seat[i] = new Seat(r.getCategory(), r.getSector(), r.getNumber(), seats[i]);
					}
					return seat;
				}
			} else {
				transaction.commit();
			}
		} catch (RuntimeException ex) {
			transaction.rollback();
			logger.error("Got RuntimeException. rollback()", ex);
		}
		
		return null;
	}

}
