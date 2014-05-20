package ots;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Seat {

	@Id
	@GeneratedValue
	private Integer id;
	private String category;
	private String sector;
	private int row;
	private int number;

	public Seat() {
	}

	public Seat(String category, String sector, int row, int number) {
		this.category = category;
		this.sector = sector;
		this.row = row;
		this.number = number;
	}

	public String getCategory() {
		return category;
	}

	public String getSector() {
		return sector;
	}

	public int getRow() {
		return row;
	}

	public int getNumber() {
		return number;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != Seat.class) {
			return false;
		}
		Seat seat = (Seat) obj;
		return seat.category.equals(category) && seat.sector.equals(sector)
				&& row == seat.row && number == seat.number;
	}

	@Override
	public int hashCode() {
		return category.hashCode() + sector.hashCode() + row + number;
	}

	@Override
	public String toString() {
		return category + "-" + sector + "-" + row + "-" + number;
	}
}
