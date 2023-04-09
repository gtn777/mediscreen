package mediscreen.reportservice;

public enum DiabetesRiskLevel {
	NONE("Aucun risque"), BORDERLINE("Risque limité"), INDANGER("Danger"), EARLYONSET("Apparition précoce");

	private String levelString;

	DiabetesRiskLevel(String levelString) {
		this.levelString = levelString;
	}

	public String getLevelString() {
		return this.levelString;
	}

}
