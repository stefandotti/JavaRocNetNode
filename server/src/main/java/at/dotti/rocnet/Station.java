package at.dotti.rocnet;

import java.io.Serializable;

public class Station implements Serializable {

	private int stationId;

	private String stationName;

	public Station(int stationId, String stationName) {
		this.stationId = stationId;
		this.stationName = stationName;
	}

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
