package me.sigh.commons.utils.cryptology;

/**
 * Created by wangtingbang on 2016/12/27.
 */
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AES256Encryption {

  public static final String KEY_ALGORITHM = "DES";

  public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS7Padding";

  public static byte[] initkey() throws Exception {
    //实例化密钥生成器
    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM, "BC");
    kg.init(256);
    kg.init(128);
    SecretKey secretKey = kg.generateKey();
    return secretKey.getEncoded();
  }

  public static Key toKey(byte[] key) throws Exception {

    SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);

    return secretKey;
  }

  public static byte[] encrypt(byte[] data, byte[] key) throws Exception {

    Key k = toKey(key);
    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");

    cipher.init(Cipher.ENCRYPT_MODE, k);

    return cipher.doFinal(data);
  }

  public static byte[] decrypt(byte[] data, byte[] key) throws Exception {

    Key k = toKey(key);

    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");

    cipher.init(Cipher.DECRYPT_MODE, k);

    return cipher.doFinal(data);
  }



  public static void main(String[] args) throws Exception {

    String str = "芸sweet";

    //打印原文
    System.out.println("原文：" + str);

    //密钥
    byte[] key;


    //生成随机密钥
    key = "cmbtest1".getBytes();//AES256Encryption.initkey();


    //加密
    byte[] data = AES256Encryption.encrypt(str.getBytes(), key);

    byte[] dataBase64 = Base64.encodeBase64(data);
    //打印密文
    System.out.println("加密后："+ new String(data));
    System.out.println("加密后："+ new String(dataBase64));

    data = AES256Encryption.decrypt(data, key);
    //打印原文
    System.out.println("解密后：" + new String(data));

    dataBase64 = AES256Encryption.decrypt(Base64.decodeBase64(dataBase64), key);
    System.out.println("解密后：" + new String(dataBase64));
  }
}
