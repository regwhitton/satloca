package satloca.model;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Builder
@Introspected
@Schema(description = "The geocentric location of the satellite")
public class GeodeticCoordinates {

    @Schema(description = "The instant that the satellite has this position")
    @NotNull
    Instant time;

    @Schema(description = "The latitude position of the satellite in degrees")
    @NotNull
    Double latitude;

    @Schema(description = "The longitude position of the satellite in degrees")
    @NotNull
    Double longitude;

    @Schema(description = "The altitude of the satellite in kilometers")
    @NotNull
    Double altitude;
}
