package at.dotti.rocnet;

import java.util.List;

/**
 * Created by stefan on 11.09.2016.
 */
public class Schedule {

    private String id;
    private String trainSign;
    private int trainNumber;
    private String destination;
    private List<ScheduledDeparture> departures;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainSign() {
        return trainSign;
    }

    public void setTrainSign(String trainSign) {
        this.trainSign = trainSign;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<ScheduledDeparture> getDepartures() {
        return departures;
    }

    public void setDepartures(List<ScheduledDeparture> departures) {
        this.departures = departures;
    }
}
