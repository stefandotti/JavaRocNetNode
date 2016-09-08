package at.dotti.rocnet;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Path("/rest")
public class Server {

	@Inject
	private RocNetService rocNetService;

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
	public Display getStation(@PathParam("stationId") int stationId) {
		Display display = new Display();
		display.setStationId(1);
		display.setStationName("Heiligenstadt");

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis() + (1000 * 60));

		for (Departure d : this.rocNetService.getQueue()) {
			display.getDepartures().add(d);
		}

		//display.getDepartures().add(new Departure("S40", "Tulln an der Donau", SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT).format(cal.getTime()), "", 1));
		return display;
	}

}
