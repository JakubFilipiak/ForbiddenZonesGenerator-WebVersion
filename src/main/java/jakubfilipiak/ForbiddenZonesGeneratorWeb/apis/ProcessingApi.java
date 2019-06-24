package jakubfilipiak.ForbiddenZonesGeneratorWeb.apis;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.FileService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.storage.FileType;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.TrackService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.Track;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
public class ProcessingApi {

//    private FileService fileService = new FileService();
//    private TrackService trackService = new TrackService();

    public void processTrack(Track track) {

//        String pathname = track.getTrackFilePathname();
//
//        if (fileService.fileExists(pathname)) {
//            if (fileService.isFileTypeCorrect(pathname, FileType.TRK)) {
//                try {
//                    BufferedReader bufferedReader =
//                            fileService.createBufferedReader(pathname);
//                    trackService.processPointsOfTrack(bufferedReader);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }
}
