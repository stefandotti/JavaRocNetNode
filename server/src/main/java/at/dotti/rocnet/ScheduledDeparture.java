package at.dotti.rocnet;

/**
 * Created by stefan on 11.09.2016.
 */
public class ScheduledDeparture {

    private String time;
    private int id;
    private int platform;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }
}
