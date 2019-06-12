package jakubfilipiak.ForbiddenZonesGeneratorWeb.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Jakub Filipiak on 12.06.2019.
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "processing_configs")
public class ProcessingConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "config_name", nullable = false, unique = true)
    private String configName;

    // Turn

    @Column(name = "min_turn_initiation_angle", nullable = false)
    private int minTurnInitiationAngle;

    @Column(name = "ignored_turn_min_value", nullable = false)
    private int ignoredTurnMinValue;
    @Column(name = "ignored_turn_max_value", nullable = false)
    private int ignoredTurnMaxValue;

    // Zone forbidden by turns

    @Column(name = "min_turns_number_in_series", nullable = false)
    private int minTurnsNumberInSeries;
    @Column(name = "max_pauses_number_between_turns", nullable = false)
    private int maxPausesNumberBetweenTurns;

    @Column(name = "single_turn_zone_full_time", nullable = false)
    private boolean singleTurnZoneFullTime;
    @Column(name = "single_turn_zone_begin_offset", nullable = false)
    private int singleTurnZoneBeginOffset;
    @Column(name = "single_turn_zone_end_offset", nullable = false)
    private int singleTurnZoneEndOffset;

    @Column(name = "group_of_turns_zone_full_time", nullable = false)
    private boolean groupOfTurnsZoneFullTime;
    @Column(name = "group_of_turns_zone_begin_offset", nullable = false)
    private int groupOfTurnsZoneBeginOffset;
    @Column(name = "group_of_turns_zone_end_offset", nullable = false)
    private int groupOfTurnsZoneEndOffset;

    // Zone forbidden by points

    @Column(name = "point_neighborhood_verification", nullable = false)
    private boolean pointNeighborhoodVerification;
    @Column(name = "radius_of_pixels_to_be_verified", nullable = false)
    private int radiusOfPixelsToBeVerified;

    @Column(name = "min_points_number_in_series", nullable = false)
    private int minPointsNumberInSeries;
    @Column(name = "max_pauses_number_between_points", nullable = false)
    private int maxPausesNumberBetweenPoints;

    @Column(name = "single_point_zone_begin_offset", nullable = false)
    private int singlePointZoneBeginOffset;
    @Column(name = "single_point_zone_end_offset", nullable = false)
    private int singlePointZoneEndOffset;

    @Column(name = "group_of_points_zone_begin_offset", nullable = false)
    private int groupOfPointsZoneBeginOffset;
    @Column(name = "group_of_points_zone_end_offset", nullable = false)
    private int groupOfPointsZoneEndOffset;
}
