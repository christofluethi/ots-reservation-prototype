package ots;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;

/**
 * The class ReservationThread implements a thread that repeatedly makes reservations.
 */
public class ReservationThread extends Thread {

	public static final int MIN_SEATS_RESERVATION = 1;
	public static final int MAX_SEATS_RESERVATION = 10;
	private List<String> categories;
	private CyclicBarrier barrier;
	private int numberOfReservations;
	private ReservationService reservationService;
	private List<Reservation> reservations;
	private List<long[]> times;

	public ReservationThread(List<String> categories, CyclicBarrier barrier, int numberOfReservations) {
		this.categories = categories;
		this.barrier = barrier;
		this.numberOfReservations = numberOfReservations;
		reservationService = ReservationServiceFactory.getReservationService();
		reservations = new ArrayList<>();
		times = new ArrayList<>();
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public List<long[]> getTimes() {
		return times;
	}

	@Override
	public void run() {
		try {
			barrier.await();
		} catch (Exception e) {
		}
		Random random = new Random();
		for (int i = 0; i < numberOfReservations; i++) {
			String category = categories.get(random.nextInt(categories.size()));
			int numberOfSeats = MIN_SEATS_RESERVATION + random.nextInt(MAX_SEATS_RESERVATION - MIN_SEATS_RESERVATION + 1);
			long startTime = System.currentTimeMillis();
			Seat[] seats = reservationService.makeReservation(category, numberOfSeats);
			long endTime = System.currentTimeMillis();
			times.add(new long[]{startTime, endTime});
			reservations.add(new Reservation(category, numberOfSeats, seats));
		}
	}
}
