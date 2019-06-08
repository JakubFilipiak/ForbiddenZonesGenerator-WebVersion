package jakubfilipiak.ForbiddenZonesGeneratorWeb.services;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.ForbiddenZone;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.PointOfTrack;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.TurnOfTrack;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.generators.ZoneByTurnsGenerator;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.generators.TurnOfTrackGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalTime;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
public class TrackService {

    private PointOfTrackService pointOfTrackService = new PointOfTrackService();
    private TurnOfTrackGenerator turnOfTrackGenerator = new TurnOfTrackGenerator();
    private ZoneByTurnsGenerator zoneByTurnsGenerator = new ZoneByTurnsGenerator();

    public void processPointsOfTrack(BufferedReader bufferedReader) throws IOException {

        String line;

        while ((line = bufferedReader.readLine()) != null) {

            if (line.startsWith("T")) {
                PointOfTrack pointOfTrack = createPointOfTrack(line);

                if (pointOfTrackService.isPointOfTrackCorrect(pointOfTrack)) {
                    boolean bufferReady =
                            turnOfTrackGenerator.updatePointsBuffer(pointOfTrack);

                    if (bufferReady) {
                        TurnOfTrack turnOfTrack = turnOfTrackGenerator.createTurnFromBuffer();
                        System.out.println(turnOfTrack);
                        boolean zoneBufferReady =
                                zoneByTurnsGenerator.updateTurnsBuffer(turnOfTrack);
                        if (zoneBufferReady) {
                            ForbiddenZone zoneByTurns = zoneByTurnsGenerator.createZoneFromBuffer();
                            System.out.println(zoneByTurns);
                        }
                    }
                    System.out.println(pointOfTrack);
                    System.out.println();
                }
            }
        }
    }

    private PointOfTrack createPointOfTrack(String line) {

        final int LATITUDE_INDEX = 2;
        final int LONGITUDE_INDEX = 3;
        final int TIME_INDEX = 5;

        float latitude = 0f;
        float longitude = 0f;
        LocalTime time = LocalTime.of(0, 0, 0);

        String[] fields = line.split(" ");
        int fieldIndex = 0;

        for (String field : fields) {
            switch (fieldIndex) {
                case LATITUDE_INDEX:
                    latitude = Float.parseFloat(field.substring(1));
                    break;
                case LONGITUDE_INDEX:
                    longitude = Float.parseFloat(field.substring(2));
                    break;
                case TIME_INDEX:
                    time = LocalTime.parse(field);
                    break;
                default:
                    break;
            }
            fieldIndex++;
        }
        return new PointOfTrack.PointOfTrackBuilder()
                .setLatitude(latitude)
                .setLongitude(longitude)
                .setTime(time)
                .build();
    }
}
