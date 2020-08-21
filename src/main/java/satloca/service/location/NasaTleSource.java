package satloca.service.location;

import satloca.model.NasaTle;

import javax.inject.Inject;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

class NasaTleSource {

    private final Clock utcClock;
    private final NasaTleApi nasaTleApi;
    private final NasaTleRepository nasaTleRepository;

    @Inject
    public NasaTleSource(Clock utcClock, NasaTleApi nasaTleApi, NasaTleRepository nasaTleRepository) {
        this.utcClock = utcClock;
        this.nasaTleApi = nasaTleApi;
        this.nasaTleRepository = nasaTleRepository;
    }

    NasaTle findById(Long satelliteId) {
        Instant tooOldPoint = utcClock.instant().minus(1, ChronoUnit.DAYS);
        return nasaTleRepository.findById(satelliteId)
                .filter(nasaTle -> nasaTle.getUpdated().isAfter(tooOldPoint))
                .orElseGet(() -> fetchFromNasa(satelliteId));
    }

    private NasaTle fetchFromNasa(Long satelliteId) {
        NasaTle nasaTle = nasaTleApi.fetchTle(satelliteId);
        nasaTleRepository.save(nasaTle);
        return nasaTle;
    }
}