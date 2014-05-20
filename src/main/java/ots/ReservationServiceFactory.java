package ots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.lang.StringUtils;

/**
 * The class ReservationServiceFactory implements a factory that creates reservation services.
 */
public class ReservationServiceFactory {

	//private static Logger logger = Logger.getLogger(ReservationServiceFactory.class);
	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ots");

	/**
	 * Initializes the database with the available seats.
	 *
	 * @param seats the available seats
	 */
	public static void initDatabase(List<Seat> seats) {
		
		/*
		 * reformating seatlist to rowlist with seat representation
		 */
		Map<String, Row> rows = new HashMap<String, Row>();
		Map<String, List<Integer>> seatsInRow = new HashMap<String, List<Integer>>();
		for (Seat seat : seats) {
			Row r;
			String rowId = seat.getCategory()+"-"+seat.getSector()+"-"+seat.getRow();
			if(rows.containsKey(rowId)) {
				r = rows.get(rowId);
				r.increaseFree();
				seatsInRow.get(rowId).add(seat.getNumber());
			} else {
				r = new Row(seat.getCategory(), seat.getSector(), seat.getRow(), 1, "");
				rows.put(rowId, r);
				List<Integer> slist = new ArrayList<Integer>();
				slist.add(seat.getNumber());
				seatsInRow.put(rowId, slist);
			}
		}
	
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
	
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.createQuery("DELETE FROM Row").executeUpdate();
		for (String rowId : rows.keySet()) {
			Row r = rows.get(rowId);
			String state = generateState(seatsInRow.get(rowId));
			//logger.debug("ROW["+rowId+"] "+state+" (Row lenght: "+state.length()+") (Seats free: "+seatsInRow.get(rowId).size()+")");
			r.setState(state);
			r.setFree(seatsInRow.get(rowId).size());
			entityManager.persist(r);
		}
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	private static String generateState(List<Integer> list) {
		StringBuffer sb = new StringBuffer();
		int previous = 0;
		for (Integer integer : list) {
			if(integer.intValue() == previous+1) {
				sb.append("0");
			} else {
				int hole = integer.intValue() - previous;
				sb.append(StringUtils.repeat("-", hole-1));
				sb.append("0");
			}
			
			previous = integer.intValue();
		}
		
		return sb.toString();
	}
	
	
	/**
	 * Creates an instance of the reservation service.
	 *
	 * @return the created reservation service
	 */
	public static ReservationService getReservationService() {
		return new ReservationServiceImpl(entityManagerFactory.createEntityManager());
	}
}
