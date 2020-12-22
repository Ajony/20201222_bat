package com.h3c.vdi.viewscreen.utils.sm4;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * sm4 算法实例， 使用方法，创建一个SM4Instance 对象，
 * ECB 模式只需指定密钥
 * CBC 模式需要指定密钥和IV偏移量
 */
@Slf4j
public class SM4Instance {

    private final static int MODE_ECB = 0;
    private final static int MODE_CBC = 1;

    private String iv;

    private static final Pattern PATTERN = Pattern.compile("\\s*|\t|\r|\n");

    private SM4Context enCtx;
    private SM4Context deCtx;

    private SM4 sm4Encrypt;
    private SM4 sm4Decrypt;

    private int type = MODE_ECB;

    public SM4Instance(String secretKey) {
        if (secretKey == null) {
            throw new NullPointerException();
        }
        init(secretKey);
    }

    public SM4Instance(String secretKey, String ivStr) {
        if (secretKey == null || ivStr == null) {
            throw new NullPointerException();
        }
        init(secretKey);
        iv = ivStr;
        type = MODE_CBC;
    }

    private void init(String secretKey) {
        // encrypt init
        try {
            enCtx = new SM4Context();
            enCtx.setPadding(true);
            enCtx.setMode(SM4.SM4_ENCRYPT);
            byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
            sm4Encrypt = new SM4();
            sm4Encrypt.sm4SetkeyEnc(enCtx, keyBytes);
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }

        // decrypt init
        try {
            deCtx = new SM4Context();
            deCtx.setPadding(true);
            deCtx.setMode(SM4.SM4_DECRYPT);
            byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
            sm4Decrypt = new SM4();
            sm4Decrypt.sm4SetkeyDec(deCtx, keyBytes);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
    }

    public String encryptData(String plainText) {
        if (type == MODE_ECB) {
            return encryptDataECB(plainText);
        } else {
            return encryptDataCBC(plainText);
        }
    }

    public String decryptData(String cipherText) {
        if (type == MODE_ECB) {
            return decryptDataECB(cipherText);
        } else {
            return decryptDataCBC(cipherText);
        }
    }

    private String encryptDataECB(String plainText) {
        String cipherText = null;
        try {
            byte[] encrypted = sm4Encrypt.sm4CryptEcb(enCtx, plainText.getBytes(StandardCharsets.UTF_8));
            cipherText = new BASE64Encoder().encode(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                Matcher m = PATTERN.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
        return cipherText;
    }

    private String decryptDataECB(String cipherText) {
        byte[] decrypted = new byte[0];
        try {
            decrypted = sm4Decrypt.sm4CryptEcb(deCtx, new BASE64Decoder().decodeBuffer(cipherText));
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    private String encryptDataCBC(String plainText) {
        String cipherText = null;
        try {
            byte[] encrypted = sm4Encrypt.sm4CryptCbc(enCtx, iv.getBytes(StandardCharsets.UTF_8), plainText.getBytes(StandardCharsets.UTF_8));
            cipherText = new BASE64Encoder().encode(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                Matcher m = PATTERN.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
        return cipherText;
    }

    private String decryptDataCBC(String cipherText) {
        byte[] decrypted = new byte[0];
        try {
            decrypted = sm4Decrypt.sm4CryptCbc(deCtx, iv.getBytes(StandardCharsets.UTF_8), new BASE64Decoder().decodeBuffer(cipherText));
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
        return new String(decrypted, StandardCharsets.UTF_8);
    }


    public static void main(String[] args) {
        String plainText = "abc";
        SM4Instance sm4Instance = new SM4Instance("*yoiH&^%56_Ha!@#");
        System.out.println("ECB模式");
        String cipherText = sm4Instance.encryptDataECB(plainText);
        System.out.println("密文: " + cipherText);
        System.out.println("===================================");

        plainText = sm4Instance.decryptDataECB(cipherText);
        System.out.println("明文: " + plainText);
        System.out.println("====================================");

        SM4Instance sm4CbcInstance = new SM4Instance("JeF8U9wHFOMfs2Y8", "UISwD9fW6cFh9SNS");
        System.out.println("CBC模式");
        cipherText = sm4CbcInstance.encryptDataCBC(plainText);
        System.out.println("密文: " + cipherText);
        System.out.println("====================================");

        plainText = sm4CbcInstance.decryptDataCBC(cipherText);
        System.out.println("明文: " + plainText);
    }

}
