package jakubfilipiak.ForbiddenZonesGeneratorWeb.utils;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.crypto.CustomSecretKey;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.CustomSecretKeyService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.LocalFileService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Optional;

/**
 * Created by Jakub Filipiak on 30.07.2019.
 */
@Component
public class FileManager {

    private LocalFileService fileService;
    private CustomSecretKeyService keyService;

    public FileManager(LocalFileService fileService, CustomSecretKeyService keyService) {
        this.fileService = fileService;
        this.keyService = keyService;
    }

    public Optional<File> createFileFromEncryptedFile(String encFilePath,
                                                      String decFileName) {
        Optional<CustomSecretKey> aesKey = keyService.getAesKey();
        File encFile = new File(encFilePath);
        String decFilePath = fileService.getTmpStoragePath() + decFileName;
        if (aesKey.isPresent()) {
            return CryptographyUtils.decryptFile(
                    keyService.getAesKey().get().getKey(),
                    new File(encFilePath),
                    fileService.getTmpStoragePath() + decFileName);
        }
        return Optional.empty();
    }

    public void deleteTmpFile(File tmpFile) {
        if (tmpFile.exists()) tmpFile.delete();
    }

    @EventListener(ApplicationReadyEvent.class)
    private void testTmpPath() {
        System.out.println(fileService.getTmpStoragePath());
        String key = keyService.getAesKey().get().getKey();
        Optional<File> optFile = CryptographyUtils.encryptFile(key,
                new File("D:/crypto/tests/filetoencrypt.txt"),
                "D:/crypto/tests/enc.enc");
        optFile.ifPresent(file -> {
            createFileFromEncryptedFile(file.getPath(), "testenc.txt");
        });
    }
}
