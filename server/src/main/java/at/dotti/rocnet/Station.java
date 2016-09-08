package at.dotti.rocnet;

import java.io.Serializable;

public class Station implements Serializable {

	private int stationId;

	private String stationName;

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}
}
