package jakubfilipiak.ForbiddenZonesGeneratorWeb;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.apis.ProcessingApi;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.Track;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ForbiddenZonesGeneratorWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForbiddenZonesGeneratorWebApplication.class, args);

//		ProcessingApi processingApi = new ProcessingApi();
//
//		Track track = new Track("D:/ForbiddenZonesGenerator " +
//				"resources/LU-53-SP-ABM.trk");
//		processingApi.processTrack(track);
	}
}
