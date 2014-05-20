package ots;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.log4j.Logger;
import org.eclipse.persistence.annotations.Index;

/**
 * Class representing a row in a given category, sector
 * The Row contains a textual representation of the seat status {@link SeatMask}
 */
@Entity

public class Row {
	private static Logger logger = Logger.getLogger(Row.class);
	
	@Id
	@GeneratedValue
	private Integer id;
	@Index private String category;
	private String sector;
	private int number;
	@Index private int free;
	private String state;
	
	public Row() { }
	
	/**
	 * Create new row
	 * 
	 * @param category
	 * @param sector
	 * @param row
	 * @param free
	 * @param nrAdjacent
	 */
	public Row(String category, String sector, int number, int free, String state) {
		this.category = category;
		this.sector = sector;
		this.number = number;
		this.free = free;
		this.state = state;
	}
	
	public String getCategory() {
		return category;
	}

	public String getSector() {
		return sector;
	}

	public int getNumber() {
		return number;
	}
	
	public void increaseFree() {
		free++;
	}
	
	public String getIdentifier() {
		return category+" "+sector+" "+number;
	}
	
	public void decreaseFree(int nr) {
		free = free - nr;
	}
	
	public int getFree() {
		return free;
	}
	
	public void setFree(int free) {
		this.free = free;
	}

	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + number;
		result = prime * result + ((sector == null) ? 0 : sector.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Row other = (Row) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (number != other.number)
			return false;
		if (sector == null) {
			if (other.sector != null)
				return false;
		} else if (!sector.equals(other.sector))
			return false;
		return true;
	}
}