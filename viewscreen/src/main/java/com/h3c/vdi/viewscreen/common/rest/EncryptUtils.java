package com.h3c.vdi.viewscreen.common.rest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.Charset;

/**
 * Created by x19765 on 2020/10/23.
 */

public class EncryptUtils {
    private static Charset CHARSET_UTF8 = Charset.forName("UTF-8");
    private static Log log = LogFactory.getLog(EncryptUtils.class);
    private static final byte[] PASSWORD_KEY = new byte[]{108, 105, 95, 48, 49, 48, 49, 48};
    private static Cipher encryptDesCipher;
    private static Cipher decryptDesCipher;

    private static final String ENCRYPT_AL = "DES/ECB/PKCS5Padding";

    public static String encryptText(String plainText) {
        try {
            byte[] cryptoText = encryptDesCipher.doFinal(plainText.getBytes(CHARSET_UTF8));
            return cryptoText != null ? new String(Base64.encodeBase64(cryptoText), CHARSET_UTF8) : null;
        } catch (Exception var2) {
            log.warn((Object)null, var2);
            return null;
        }
    }

    public static String decryptText(String cryptoText) {
        try {
            byte[] decodedText = Base64.decodeBase64(cryptoText.getBytes(CHARSET_UTF8));
            if (decodedText == null) {
                return null;
            } else {
                byte[] plainText = decryptDesCipher.doFinal(decodedText);
                return plainText != null ? new String(plainText, CHARSET_UTF8) : null;
            }
        } catch (Exception var3) {
            log.warn((Object)null, var3);
            return null;
        }
    }

    static {
        DESKeySpec desKeySpec;
        SecretKeyFactory factory;
        SecretKey desSecretKey;
        try {
            desKeySpec = new DESKeySpec(PASSWORD_KEY);
            factory = SecretKeyFactory.getInstance("DES");
            desSecretKey = factory.generateSecret(desKeySpec);
            encryptDesCipher = Cipher.getInstance(ENCRYPT_AL);
            encryptDesCipher.init(1, desSecretKey);
        } catch (Exception var4) {
            log.warn((Object)null, var4);
        }

        try {
            desKeySpec = new DESKeySpec(PASSWORD_KEY);
            factory = SecretKeyFactory.getInstance("DES");
            desSecretKey = factory.generateSecret(desKeySpec);
            decryptDesCipher = Cipher.getInstance(ENCRYPT_AL);
            decryptDesCipher.init(2, desSecretKey);
        } catch (Exception var3) {
            log.warn((Object)null, var3);
        }

    }
}

