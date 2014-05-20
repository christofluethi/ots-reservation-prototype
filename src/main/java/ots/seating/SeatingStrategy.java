package ots.seating;

import java.util.List;

import ots.Row;
import ots.Seat;

public interface SeatingStrategy {
	public List<Row> getRows(String category, int numberOfSeats);
	public Seat[] makeReservation(String category, int numberOfSeats);
}
