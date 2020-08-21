package satloca.service.location;

import io.reactivex.Flowable;
import org.reactivestreams.Publisher;
import satloca.model.GeodeticCoordinates;
import satloca.model.NasaTle;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.Clock;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Singleton
public class LocationService {

    private final Clock utcClock;
    private final NasaTleSource nasaTleSource;

    @Inject
    public LocationService(Clock utcClock, NasaTleSource nasaTleSource) {
        this.utcClock = utcClock;
        this.nasaTleSource = nasaTleSource;
    }

    @Valid
    public Publisher<GeodeticCoordinates> track(@NotNull Long satelliteId, Optional<Integer> count) {
        TrackCalculator calculator = new TrackCalculator(getNasaTle(satelliteId));
        return flowable(count)
                .map(i -> calculator.calculatePosition(utcClock.instant()));
    }

    private NasaTle getNasaTle(Long satelliteId) {
        return nasaTleSource.findById(satelliteId);
    }

    private Flowable<Long> flowable(Optional<Integer> count){
        Flowable<Long> flow = Flowable.interval(1000L, TimeUnit.MILLISECONDS);
        if (count.isPresent()) {
            flow = flow.take(count.get());
        }
        return flow.onBackpressureDrop();
    }
}
