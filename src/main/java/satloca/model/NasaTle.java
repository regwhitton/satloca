package satloca.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.DateUpdated;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.Instant;

/**
 * The NORAD Two Line Element as retrieved from the NASA TLE API.
 *
 * @see <a href="https://celestrak.com/columns/v04n03/">CelesTrack: Two-Line Element Set Format</a>
 * @see <a href="https://api.nasa.gov/">NASA APIs</a>
 */
@Entity
@Data
@Introspected
public class NasaTle {

    @Id
    Long satelliteId;

    @NotBlank
    String name;

    @Pattern(regexp = "1 [0-9]{5}[US] [0-9]{5}[A-Z ]{3} [0-9]{5}\\.[0-9]{8} [-+ ]\\.[0-9]{8} [-+ ][0-9]{5}[-+][0-9] [-+ ][0-9]{5}[-+][0-9] [0-9] [0-9 ]{5}")
    @NotNull
    String line1;

    @Pattern(regexp = "2 [0-9]{5} [0-9 ]{3}\\.[0-9]{4} [0-9 ]{3}\\.[0-9]{4} [0-9]{7} [0-9 ]{3}\\.[0-9]{4} [0-9 ]{3}\\.[0-9]{4} [0-9 ]{2}\\.[0-9]{8}[0-9 ]{6}")
    @NotNull
    String line2;

    // JPA Update of "updated" field ceases if "created" not present.
    @DateCreated
    Instant created = Instant.EPOCH;

    @DateUpdated
    Instant updated = Instant.EPOCH;
}