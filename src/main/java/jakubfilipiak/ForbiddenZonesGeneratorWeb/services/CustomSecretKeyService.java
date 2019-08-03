package jakubfilipiak.ForbiddenZonesGeneratorWeb.services;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.crypto.CustomSecretKey;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.repositories.CustomSecretKeyRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

/**
 * Created by Jakub Filipiak on 30.07.2019.
 */
@Service
public class CustomSecretKeyService {

    private CustomSecretKeyRepository keyRepository;

    public CustomSecretKeyService(CustomSecretKeyRepository keyRepository) {
        this.keyRepository = keyRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    private void initAesKey() {
        Optional<CustomSecretKey> existingAesKey = getAesKey();
        if (!existingAesKey.isPresent()) {
            CustomSecretKey aesKey = CustomSecretKey.builder()
                    .keyName("captainFox")
                    .value(Base64.getEncoder().encodeToString("ValleyKissMyPiss".getBytes()))
                    .build();
            addKey(aesKey);
        }
        Optional<CustomSecretKey> keyFromDb = getAesKey();
        if (keyFromDb.isPresent()) {
            System.out.println(keyFromDb.get().getKeyName());
            System.out.println(keyFromDb.get().getValue());
            System.out.println(keyFromDb.get().getKey());
        }
    }

    public void addKey(CustomSecretKey key) {
        keyRepository.save(key);
    }

    public Optional<CustomSecretKey> getAesKey() {
        return keyRepository.findByKeyName("captainFox");
    }
}
