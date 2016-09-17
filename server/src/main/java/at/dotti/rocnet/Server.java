package at.dotti.rocnet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Path("/rest")
@ApplicationScoped
public class Server {

    @Inject
    private RocNetService rocNetService;

    private Schedules schedules;

    @PostConstruct
    public void construct() throws IOException {
        Gson g = new Gson();
        schedules = g.fromJson(IOUtils.toString(Server.class.getResourceAsStream("/META-INF/schedules.json")), Schedules.class);
    }

    @GET
    @Path("/stations")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Station> getStations() {
        List<Station> stations = new ArrayList<>();
        return stations;
    }

    @GET
    @Path("/display/{stationId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Display getStation(@PathParam("stationId") String stationId) {
        Display display = new Display();
        display.setStationId("hs");
        display.setStationName("Heiligenstadt");

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis() + (1000 * 60));

        for (String id : this.rocNetService.getQueue()) {
            Schedule schedule = this.schedules.getSchedules().get(id);
            if (schedule != null) {
		for(ScheduledDeparture de : schedule.getDepartures()) {
                    if (de.getId().equals(stationId)) {
                        Departure d = new Departure(new Line(schedule.getTrainSign().toLowerCase(), schedule.getTrainNumber()), schedule.getDestination(), de.getTime(), "", de.getPlatform());
                        display.getDepartures().add(d);
			break;
                    }
		}
            }
        }

        return display;
    }

}
