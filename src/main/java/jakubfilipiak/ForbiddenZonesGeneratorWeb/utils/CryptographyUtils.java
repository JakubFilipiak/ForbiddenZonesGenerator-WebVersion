package jakubfilipiak.ForbiddenZonesGeneratorWeb.utils;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * Created by Jakub Filipiak on 25.07.2019.
 */
public class CryptographyUtils {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    public static Optional<File> encryptFile(String key, File inFile, String outFilePath) {
        return doFileCryptography(Cipher.ENCRYPT_MODE, key, inFile, outFilePath);
    }

    public static Optional<File> decryptFile(String key, File inFile, String outFilePath) {
        return doFileCryptography(Cipher.DECRYPT_MODE, key, inFile, outFilePath);
    }

    private static Optional<File> doFileCryptography(int mode, String key, File inFile,
                                                     String outFilePath) {
        BufferedInputStream inStream = null;
        CipherOutputStream outStream = null;
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(mode, secretKey);

            File outFile = new File(outFilePath);
            inStream = new BufferedInputStream(new FileInputStream(inFile));
            outStream = new CipherOutputStream(new FileOutputStream(outFile), cipher);

            byte[] buffer = new byte[8192];
            int counter;

            while ((counter = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, counter);
            }
            return Optional.of(outFile);
        } catch (NoSuchAlgorithmException
                | InvalidKeyException
                | NoSuchPaddingException
                | IOException e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            try {
                if (inStream != null) inStream.close();
                if (outStream != null) outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
