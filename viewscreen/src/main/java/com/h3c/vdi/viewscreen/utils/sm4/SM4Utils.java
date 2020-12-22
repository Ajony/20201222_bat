package com.h3c.vdi.viewscreen.utils.sm4;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SM4Utils {

    // 管理平台中使用的密钥
    private final static String SECRET_KEY_WEB = "*yoiH&^%56_Ha!@#";

    // 与客户端通信时使用的密钥
    private final static String SECRET_KEY_CLIENT = "*yoiH&^%56_Ha!@#";

    // CBC模式下才使用的偏移量
    private final static String IV = "UISwD9fW6cFh9SNS";

    private final static SM4Instance webSm4Instance = new SM4Instance(SECRET_KEY_WEB);
    private final static SM4Instance clientSm4Instance = new SM4Instance(SECRET_KEY_CLIENT);

    /**
     * 对字符串进行加密，主要用于在保存用户密码时，进行加密操作，web使用
     */
    public static String webEncryptText(String plainText) {
        return webSm4Instance.encryptData(plainText);
    }

    /**
     * 对字符串数据进行解密操作，web使用
     */
    public static String webDecryptText(String cipherText) {
        return webSm4Instance.decryptData(cipherText);
    }

    /**
     * 与客户端通信时，加密字符串
     */
    public static String clientEncryptText(String plainText) {
        return clientSm4Instance.encryptData(plainText);
    }

    /**
     * 与客户端通信时，解密客户端传输的字符串
     */
    public static String clientDecryptText(String cipherText) {
        return clientSm4Instance.decryptData(cipherText);
    }

    public static void main(String[] args) {

        String encryptText = webEncryptText("Cloud@1234");
        System.out.println(encryptText);
        System.out.println(webDecryptText(encryptText));
        System.out.println(webDecryptText("Cloud@1234"));

        System.out.println("====================================================");

        System.out.println(webDecryptText(webEncryptText("123456abc")));
        System.out.println(clientDecryptText(clientEncryptText("123456abc")));
    }
}
