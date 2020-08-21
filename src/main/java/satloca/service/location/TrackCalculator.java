package satloca.service.location;

import com.github.amsacode.predict4java.SatPos;
import com.github.amsacode.predict4java.Satellite;
import com.github.amsacode.predict4java.SatelliteFactory;
import com.github.amsacode.predict4java.TLE;
import satloca.model.GeodeticCoordinates;
import satloca.model.NasaTle;

import java.time.Instant;
import java.util.Date;

class TrackCalculator {

    private final Satellite sat;

    TrackCalculator(NasaTle nasaTle) {
        TLE tle = new TLE(new String[]{
                nasaTle.getName(), nasaTle.getLine1(), nasaTle.getLine2()});
        this.sat = SatelliteFactory.createSatellite(tle);
    }

    GeodeticCoordinates calculatePosition(Instant time) {
        sat.calculateSatelliteVectors(new Date(time.toEpochMilli()));
        SatPos pos = sat.calculateSatelliteGroundTrack();
        return GeodeticCoordinates.builder()
                .time(Instant.ofEpochMilli(pos.getTime().getTime()))
                .latitude(pos.getLatitude() / Satellite.DEG2RAD)
                .longitude(pos.getLongitude() / Satellite.DEG2RAD)
                .altitude(pos.getAltitude())
                .build();
    }
}
