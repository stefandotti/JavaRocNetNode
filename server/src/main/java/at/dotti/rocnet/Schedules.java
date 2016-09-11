package at.dotti.rocnet;

import java.util.List;
import java.util.Map;

/**
 * Created by stefan on 11.09.2016.
 */
public class Schedules {

    public Map<String, Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Map<String, Schedule> schedules) {
        this.schedules = schedules;
    }

    private Map<String, Schedule> schedules;

}
