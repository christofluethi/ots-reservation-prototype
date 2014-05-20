package ots;

import java.util.Arrays;

public class Reservation {

	private String category;
	private int numberOfSeats;
	private Seat[] seats;

	public Reservation(String category, int numberOfSeats, Seat[] seats) {
		this.category = category;
		this.numberOfSeats = numberOfSeats;
		this.seats = seats;
	}

	public String getCategory() {
		return category;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public Seat[] getSeats() {
		return seats;
	}

	@Override
	public String toString() {
		return "Reservation{category=" + category + ", numberofSeats=" + numberOfSeats + ", seats=" + Arrays.toString(seats);
	}
}
