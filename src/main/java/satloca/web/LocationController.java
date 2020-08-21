package satloca.web;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.reactivestreams.Publisher;
import satloca.model.GeodeticCoordinates;
import satloca.service.location.LocationService;

import javax.inject.Inject;
import java.util.Optional;

import static io.micronaut.http.MediaType.APPLICATION_JSON;
import static io.micronaut.http.MediaType.APPLICATION_JSON_STREAM;

@Controller
public class LocationController {

    private final LocationService locationService;

    @Inject
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Operation(summary = "Track a satellite's position", description = "Streams the location of a satellite."
            + "  (To see results delivered in real-time use CURL command with \"--no-buffer\" option)")
    @Get(uri = "/track/{satelliteId}", produces = {APPLICATION_JSON_STREAM, APPLICATION_JSON})
    public Publisher<GeodeticCoordinates> track(
            @PathVariable
            @Parameter(description = "The NORAD catalogue number of the satellite (https://celestrak.com/NORAD/elements/)", example = "25544")
                    Long satelliteId,
            @QueryValue
            @Parameter(description = "The number of positions to return (optional)", example = "5")
                    Optional<Integer> count) {
        return locationService.track(satelliteId, count);
    }
}