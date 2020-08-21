package satloca.service.location;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import satloca.model.NasaTle;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Micronaut implemented web-client for NASA TLE API.
 *
 * @see NasaTle
 */
@Client("${nasa.tle-api.url}")
interface NasaTleApi {

    @Get("/{satelliteId}")
    @Valid
    NasaTle fetchTle(@NotNull Long satelliteId);
}
