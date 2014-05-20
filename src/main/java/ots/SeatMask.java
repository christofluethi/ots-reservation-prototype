package ots;

/**
 * Simple textual representation of seat status
 * 
 * - is unavailable (seat does not exist)
 * 0 seat is available
 * 1 seat is reserved
 */
public class SeatMask {
	private char[] mask;

	public SeatMask(String mask) {
		this.mask = mask.toCharArray();
	}

	/** 
	 * reserve a given number of seats in the row
	 * 
	 * @param nr Number of seats to reserve
	 * @return the Seat numbers which are reserved or an empty list if no seats are reserved
	 */
	public int[] reserve(int nr) {
		if(nr > 0) {

			int start = indexOfNextFreeSeat(0);
			int end = scanForLastSeatNr(start, nr);
			int count = 0;

			if(end >= 0) {
				int[] seats = new int[nr];

				for (int i = start; i < end; i++) {
					if(set(i)) {
						seats[count++] = i+1;
					}
				}

				return seats;
			}
		}
		
		return new int[0];
	}


	/**
	 * Tries to get the correct end for the seating.
	 * 
	 * @param start 
	 * @param nr
	 * @return
	 */
	private int scanForLastSeatNr(int start, int nr) {
		int outstanding = nr;

		int end = start;
		while(outstanding > 0 && end >= 0) {
			if(end >= this.mask.length) {
				break;
			}

			if(this.mask[end] == '0') {
				outstanding--;
			} else if(this.mask[end] == '1') {
				return scanForLastSeatNr(indexOfNextFreeSeat(end), nr);
			}

			end++;
		}

		if(outstanding > 0) {
			return -1;
		} else {
			return end;
		}
	}

	/**
	 * return index of the next seat which is marked as free
	 * 
	 * @param start Start from here
	 * @return index of first empty seat
	 */
	private int indexOfNextFreeSeat(int start) {
		for (int i = start; i < this.mask.length; i++) {
			if(this.mask[i] == '0') {
				return i;
			}
		}

		return -1;
	}


	/**
	 * Reserve the specified seat
	 * 
	 * @param nr The Seat number to reserve
	 * @return true if the seat could be reserved.
	 */
	private boolean set(int nr) {
		if(this.mask[nr] == '0') {
			this.mask[nr] = '1';
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Represents the underlying SeatMask as a String using the following states
	 * 
	 * - is unavailable (seat does not exist)
	 * 0 seat is available
	 * 1 seat is reserved
	 * 
	 * @return A String representing the current SeatMask
	 */
	public String getMaskAsString() {
		return new String(this.mask);
	}
	
	public String toString() {
		return this.getMaskAsString();
	}
}
