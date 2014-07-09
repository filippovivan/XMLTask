package by.bsu.traintask.enteties.accessory;

public class Cargo {
	private int id;
	private int weight;
	private Passenger owner;

	public Cargo(int id, int weight, Passenger owner) {
		super();
		this.id = id;
		this.weight = weight;
		this.owner = owner;
	}

	public Cargo() {
	}

	public Passenger getOwner() {
		return owner;
	}

	public void setOwner(Passenger owner) {
		this.owner = owner;
	}

	public int getId() {
		return id;
	}

	public int getWeight() {
		return weight;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setWeight(int weight) {
		this.weight = weight;
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
		Cargo other = (Cargo) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return id + " " + weight + " tons";
	}
}
