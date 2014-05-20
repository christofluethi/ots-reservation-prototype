package ots;

public interface ReservationService {

	/**
	 * Makes a seat reservation. The reserved seats have to be in the same sector and preferably adjacent.
	 *
	 * @param category the seat category
	 * @param numberOfseats the number of seats to be reserved
	 * @return the reserved seats if successful, null otherwise
	 */
	public Seat[] makeReservation(String category, int numberOfseats);
}
