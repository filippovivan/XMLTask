package by.bsu.traintask.enteties;

import by.bsu.traintask.exceptions.LogicalException;

public abstract class TrainPart {
	private int id;
	private int taroMass;

	public int getMass() {
		return taroMass;
	}

	public void setMass(int mass) throws LogicalException {
		if (mass > 0) {
			this.taroMass = mass;
		} else {
			throw new LogicalException("Mass must be positive.");
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		TrainPart other = (TrainPart) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
