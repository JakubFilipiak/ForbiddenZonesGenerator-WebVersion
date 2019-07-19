package jakubfilipiak.ForbiddenZonesGeneratorWeb.utils;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.PointOfTrack;

/**
 * Created by Jakub Filipiak on 30.05.2019.
 */
public class TurnAngleCalculator {

    public double calculateAbsoluteAngleOfLine(PointOfTrack beginPoint,
                                               PointOfTrack endPoint) {
        double x1 = beginPoint.getLongitude();
        double y1 = beginPoint.getLatitude();
        double x2 = endPoint.getLongitude();
        double y2 = endPoint.getLatitude();

        double absoluteAngleInRad = Math.atan2(y2 - y1, x2 - x1);
        return Math.toDegrees(absoluteAngleInRad);
    }

    public double calculateTurnAngle(double entranceAngle,
                                     double departureAngle) {
        boolean anglesTheSame = entranceAngle == departureAngle;
        boolean positiveEntranceAngle = entranceAngle >= 0;
        boolean positiveDepartureAngle = departureAngle >= 0;
        boolean theSameSignOfAngles =
                (positiveEntranceAngle && positiveDepartureAngle)
                || (!positiveEntranceAngle && !positiveDepartureAngle);

        if (anglesTheSame)
            return 0;
        if (!theSameSignOfAngles) {
            double rawAngle = Math.abs(entranceAngle) + Math.abs(departureAngle);
            if (rawAngle > 180)
                return 180 - (rawAngle - 180);
            return rawAngle;
        }
        return Math.abs(Math.abs(entranceAngle) - Math.abs(departureAngle));
    }
}
