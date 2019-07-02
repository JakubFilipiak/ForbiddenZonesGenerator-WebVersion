package jakubfilipiak.ForbiddenZonesGeneratorWeb.services;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.PointOfTrack;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
@Service
public class PointOfTrackService {

    private static final String SPLIT_REGEX = " ";
    private static final int LATITUDE_INDEX = 2;
    private static final int LONGITUDE_INDEX = 3;
    private static final int TIME_INDEX = 5;
    private static final int LATITUDE_OFFSET = 1;
    private static final int LONGITUDE_OFFSET = 2;

    public Optional<PointOfTrack> createPointFromLine(String line) {

        PointOfTrack point = null;

        String[] rawData = createRawDataTableFromLine(line);

        Optional<Double> optLatitude = extractLatitudeFromRawDataTable(rawData);
        Optional<Double> optLongitude = extractLongitudeFromRawDataTable(rawData);
        Optional<LocalTime> optTime = extractTimeFromRawDataTable(rawData);

        if (isEachParamPresent(Arrays.asList(optLatitude, optLongitude, optTime))) {
            point = PointOfTrack
                    .builder()
                    .latitude(optLatitude.get())
                    .longitude(optLongitude.get())
                    .time(optTime.get())
                    .build();
        }
        return Optional.ofNullable(point);
    }

    private String[] createRawDataTableFromLine(String line) {
        return line.split(SPLIT_REGEX);
    }

    private Optional<Double> extractLatitudeFromRawDataTable(String[] dataTable) {
        String latitude = dataTable[LATITUDE_INDEX].substring(LATITUDE_OFFSET);
        return parseDoubleFromString(latitude);
    }

    private Optional<Double> extractLongitudeFromRawDataTable(String[] dataTable) {
        String longitude = dataTable[LONGITUDE_INDEX].substring(LONGITUDE_OFFSET);
        return parseDoubleFromString(longitude);
    }

    private Optional<LocalTime> extractTimeFromRawDataTable(String[] dataTable) {
        String time = dataTable[TIME_INDEX];
        return parseLocalTimeFromString(time);
    }

    private Optional<Double> parseDoubleFromString(String value) {
        try {
            Double result = Double.parseDouble(value);
            return Optional.of(result);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    private Optional<LocalTime> parseLocalTimeFromString(String value) {
        try {
            LocalTime result = LocalTime.parse(value);
            return Optional.of(result);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    private boolean isEachParamPresent(List<Optional> params) {
        for (Optional optParam : params) {
            if (!optParam.isPresent())
                return false;
        }
        return true;
    }
}
