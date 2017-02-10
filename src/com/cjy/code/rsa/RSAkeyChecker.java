package com.cjy.code.rsa;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RSA密钥合法性验证器
 * 
 * @author xianwu.zhang
 * @version $Id: RSAkeyChecker.java, v 0.1 2012-10-29 下午04:59:09 xianwu.zhang
 *          Exp $
 */
public class RSAkeyChecker {
    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(RSAkeyChecker.class);

    /**
     * 检查公钥的合法性
     * 
     * @param key 经过base64编码的公钥key
     * @return 生成公钥未抛异常，则返回<code>true</code>，否则返回<code>false</code>
     */
    public static boolean checkPublicKey(String key) {
        if (StringUtil.isBlank(key)) {
            return false;
        }

        try {
            getPublicKey(key);
            return true;
        } catch (Exception e) {
            logger.error("RSA公钥合法性校验失败", e);
            return false;
        }

    }

    /**
     * 生成RSA公钥
     * 
     * @param key 经过base64编码的公钥key
     * @return rsa公钥
     * @throws Exception key不合法，则抛异常
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes = (byte[]) Base64.decodeBase64(key.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static void main(String[] args) {
        System.out.println(RSAkeyChecker.checkPublicKey(
                "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMZfwTVvGNqtm+O6ZsC7IDU7+xm0vYcVOy/cGRSLB1Rbqk49hB/n+ydO8GGYs1iALrB46LeU4loVawpmuM/xsILkQ6USp4N5QGBYA0q6WLlO5+P0wPtfJYlqCjimnaZQyHjbN4iNtcxK2nQzDD9HjHepdyedEZrTvtmqB3KesPzFAgMBAAECgYEAvsCW/jW9VZI5Y8r7QXru5qGtvDNDSGDPEB958noZ6zFV23Rl13Rv2oWauo5mbsSboNAzH0zIN81+p3rNXg/PJOs+AwTXt4ry6mPj5eVa0BXemC9+Z6nw6jISZaK2cvVSK5HWZy7kYV0sZGtYAVX2y7y/zF7cewoQKaVOQIkCzoUCQQD1PzQRIq/htDp1CmhylzIJd7eRGzZ7B5jFyoJtc0NvdATUc09gQ8EPg0xAPAAXDVCP6a9+xuF2KxM9//jUq9o7AkEAzxJsNYAtf9q9QniKoLNTLp0U31dWmWCEFOWuVHKJmkfDXdVK/DypHAu1j2h3IIHPiAgrZEC7Nzl9AsfEkgIU/wJAfEaM4eoL6nnj7Y9aokbPylBqKxP5JZsR5ZjbQw9XqtDF6cqM/KEY4f2M1DgTa5sJqgOnNL0fXzwwwghGVa7IJQJAK98OWANxIF56rKOXU1bK79xN+MerTFGbikXjOncN2kcMCExRV8GOXqfIbrrGSnfWPU7lypbAU4H1j2o9pmWUhwJBALw/BxzwFFnA95+YCqnpl80HCv02Gd+ts0H9BAkLeFA1t2spQxdEDhg1fKWieCREilXdVvg40q88tr6NtOv1DIA="));
    }
}
