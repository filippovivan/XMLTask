package by.bsu.traintask.enteties.accessory;

public enum PassengerCarType {
	COACH(2), SECONDARY_CLASS(3), SLEEPING(5), DINING(1);
	public final int comfortLevel;

	PassengerCarType(int comfortLevel) {
		this.comfortLevel = comfortLevel;
	}
}