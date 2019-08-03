package jakubfilipiak.ForbiddenZonesGeneratorWeb.models.crypto;

import lombok.*;

import javax.persistence.*;
import java.util.Base64;

/**
 * Created by Jakub Filipiak on 29.07.2019.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "custom_secret_keys")
public class CustomSecretKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_custom_secret_key")
    private Long id;

    @Column(name = "key_name", nullable = false, unique = true)
    private String keyName;

    @Column(name = "value", nullable = false, unique = true)
    private String value;

    public String getKey() {
        byte[] decodedBytes = Base64.getDecoder().decode(value);
        return new String(decodedBytes);
    }
}
