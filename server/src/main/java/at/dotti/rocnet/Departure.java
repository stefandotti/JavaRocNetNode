package at.dotti.rocnet;

import java.util.Date;

public class Departure {

	private String trainName;

	private int platform;

	private String text;

	private String time;

	private String current;

	public Departure(String s40, String text, String time, String current, int platform) {
		this.trainName = s40;
		this.text = text;
		this.time = time;
		this.current = current;
		this.platform = platform;
	}

	public int getPlatform() {
		return platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTrainName() {

		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}
}
