package by.bsu.traintask.enteties.accessory;

public class Passenger {
	private int id;
	private String fullName;

	public Passenger(int id, String fullName) {
		super();
		this.id = id;
		this.fullName = fullName;
	}

	public int getId() {
		return id;
	}

	public String getFullName() {
		return fullName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Passenger other = (Passenger) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return id + " " + fullName;
	}
}
