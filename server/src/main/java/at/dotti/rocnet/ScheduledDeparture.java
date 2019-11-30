package at.dotti.rocnet;

/**
 * Created by stefan on 11.09.2016.
 */
public class ScheduledDeparture {

    private short time;
    private String id;
    private String bid;
    private int platform;

    public short getTime() {
        return time;
    }

    public void setTime(short time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }
    
    public void setBid(String bid) {
        this.bid = bid;
    }
    
    public String getBid() {
        return this.bid;
    }
}
