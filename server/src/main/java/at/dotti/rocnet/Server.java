package at.dotti.rocnet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Comparator;

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
        return this.schedules.getStations();
    }

    @GET
    @Path("/display/{stationId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Display getStation(@PathParam("stationId") String stationId) {

        Station station = null;
        for (Station s : this.schedules.getStations()) {
            if (s.getId().equals(stationId)) {
                station = s;
            }
        }
        String stationName = "unkonwn station";
        if (station != null) {
            stationName = station.getName();
        }

        Display display = new Display();
        display.setStationId(stationId);
        display.setStationName(stationName);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());

        DateFormat TIME = new SimpleDateFormat("HH:mm");

        for (String id : this.rocNetService.getQueue()) {
            Schedule schedule = this.schedules.getSchedules().get(id);
            if (schedule != null) {
                for (ScheduledDeparture de : schedule.getDepartures()) {
                    if (de.getId().equals(stationId)) {
                        if (!this.rocNetService.getDeparturedBlock().containsKey(id)) {
                            // something is wrong
                            // train never enqueued the schedule
                            System.out.println("train not enqueued: " + de);
                            continue;
                        } else if (this.rocNetService.getDeparturedBlock().get(id).contains(de.getBid())) {
                            // train was already on this station
                            System.out.println("train already exited: " + de);
                            continue;
                        }

                        cal.set(Calendar.MINUTE, de.getTime());
                        String time = TIME.format(cal.getTime());
                        boolean arriving = this.rocNetService.getBlock().contains(de.getBid());
                        Line line = new Line(schedule.getTrainSign().toLowerCase(), schedule.getTrainNumber());

                        Departure d = new Departure(arriving, line, schedule.getDestination(), time, "", de.getPlatform());
                        display.getDepartures().add(d);
                        break;
                    }
                }
            } else {
                System.out.println("no schedules for " + id);
            }
        }
        
        display.getDepartures().sort(new Comparator<Departure>() {
                public int compare(Departure a, Departure b) {
                    return a.getTime().compareTo(b.getTime());
                }
        });

        return display;
    }

}
