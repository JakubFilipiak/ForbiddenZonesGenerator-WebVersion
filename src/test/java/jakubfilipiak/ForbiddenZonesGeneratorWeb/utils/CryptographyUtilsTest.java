package jakubfilipiak.ForbiddenZonesGeneratorWeb.utils;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Jakub Filipiak on 25.07.2019.
 */
public class CryptographyUtilsTest {

    private String aesKey;
    private File testFile;
    private File encryptedTestFile;
    private String fileContent;

    @Before
    public void setUp() throws Exception {
        aesKey = "a16BytesPassword";
        testFile = new File("D:/crypto/tests/filetoencrypt.txt");
        encryptedTestFile = new File("D:/crypto/tests/filetodecrypt.enc");
        fileContent = "Some random text, just for checking.";
        FileWriter writer = new FileWriter(testFile);
        writer.write(fileContent);
        writer.close();
    }

    @Test
    public void shouldCorrectEncryptFile() throws IOException {
        // given
        String key = aesKey;
        File fileToEncrypt = testFile;
        String encryptedFilePath = "D:/crypto/tests/encrypted.enc";

        // when
        Optional<File> resultEncryptedFile = CryptographyUtils.encryptFile(key,
                fileToEncrypt, encryptedFilePath);
        assertThat(resultEncryptedFile).containsInstanceOf(File.class);

        byte[] resultBytes = new byte[1024];
        FileInputStream sourceFileInStream =
                new FileInputStream(resultEncryptedFile.get());
        sourceFileInStream.read(resultBytes);
        sourceFileInStream.close();
        String resultText = new String(resultBytes).trim();

        // then
        assertThat(resultText).isNotEqualTo(fileContent);
    }

    @Test
    public void shouldCorrectDecryptFile() throws IOException {
        // given
        String key = aesKey;
        File fileToDecrypt = encryptedTestFile;
        String decryptedFilePath = "D:/crypto/tests/decrypted.txt";

        // when
        Optional<File> resultDecryptedFile = CryptographyUtils.decryptFile(key,
                fileToDecrypt, decryptedFilePath);
        assertThat(resultDecryptedFile).containsInstanceOf(File.class);

        byte[] resultBytes = new byte[1024];
        FileInputStream sourceFileInStream =
                new FileInputStream(resultDecryptedFile.get());
        sourceFileInStream.read(resultBytes);
        sourceFileInStream.close();
        String resultText = new String(resultBytes).trim();

        // then
        assertThat(resultText).isEqualTo(fileContent);
    }
}