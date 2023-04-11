package mediscreen.reportservice.domain;

public enum DiabetesRiskLevel {
	NONE("None"), BORDERLINE("Borderline"), INDANGER("In danger"), EARLYONSET("Early onset");

	private String levelString;

	DiabetesRiskLevel(String levelString) {
		this.levelString = levelString;
	}

	public String getLevelString() {
		return this.levelString;
	}

}
