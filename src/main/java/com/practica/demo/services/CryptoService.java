package com.practica.demo.services;

import com.practica.demo.models.DataModel;
import com.practica.demo.models.SessionModelRequest;
import com.practica.demo.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URLEncoder;

@Service
public class CryptoService {

    @Autowired
    IUserRepository userRepository;

    @Value("${crypto.secret.reset-password}")
    private String secret;

    public DataModel encryptData(Object data) {
        DataModel encry = new DataModel();
        try {
            SecretKeySpec key = createKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            String json = new ObjectMapper().writeValueAsString(data);
            byte[] encryptedBytes = cipher.doFinal(json.getBytes(StandardCharsets.UTF_8));
            String base64 = Base64.getEncoder().encodeToString(encryptedBytes);
            encry.setData(URLEncoder.encode(base64, StandardCharsets.UTF_8.name()));

            return encry;

        } catch (Exception e) {
            throw new RuntimeException("Error encrypting data", e);
        }
    }

    public <T> T decryptData(DataModel encryptedData, Class<T> clazz) {
        try {
            SecretKeySpec key = createKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);

            // decodeURIComponent + base64 decode
            String decodedUrl = java.net.URLDecoder.decode(encryptedData.getData(), "UTF-8");
            byte[] encryptedBytes = Base64.getDecoder().decode(decodedUrl);

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            String json = new String(decryptedBytes, java.nio.charset.StandardCharsets.UTF_8);

            // Convert JSON a objeto Java
            return new com.fasterxml.jackson.databind.ObjectMapper().readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting data", e);
        }
    }

    private SecretKeySpec createKey(String secret) throws Exception {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        byte[] hash = sha1.digest(secret.getBytes("UTF-8"));

        // Tomar los primeros 16 bytes (128 bits)
        byte[] keyBytes = new byte[16];
        System.arraycopy(hash, 0, keyBytes, 0, 16);

        return new SecretKeySpec(keyBytes, "AES");
    }
}
