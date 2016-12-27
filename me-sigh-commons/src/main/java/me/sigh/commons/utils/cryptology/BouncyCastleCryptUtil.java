package me.sigh.commons.utils.cryptology;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangtingbang on 2016/12/27.
 */
public class BouncyCastleCryptUtil {


  private static Logger log = LoggerFactory.getLogger(BouncyCastleCryptUtil.class);

  public static boolean initialized = false;

  public static final String ALGORITHM = "DES/ECB/PKCS7Padding";

  /**
   * @param str  要被加密的字符串
   * @param key  加/解密要用的长度为32的字节数组（256位）密钥
   * @return byte[]  加密后的字节数组
   */
  public static byte[] encrypt(String str, byte[] key) {
    initialize();
    byte[] result = null;
    try {
      Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
      SecretKeySpec keySpec = new SecretKeySpec(key, "DES"); //生成加密解密需要的Key
      cipher.init(Cipher.ENCRYPT_MODE, keySpec);
//      result = cipher.doFinal(str.getBytes("UTF-8"));
      result = cipher.doFinal(str.getBytes());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * @param  bytes  要被解密的字节数组
   * @param  key    加/解密要用的长度为32的字节数组（256位）密钥
   * @return String  解密后的字符串
   */
  public static String decrypt(byte[] bytes, byte[] key) {
    initialize();
    String result = null;
    try {
      Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
      SecretKeySpec keySpec = new SecretKeySpec(key, "DES"); //生成加密解密需要的Key
      cipher.init(Cipher.DECRYPT_MODE, keySpec);
      byte[] decoded = cipher.doFinal(bytes);
      result = new String(decoded, "UTF-8");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  public static void initialize() {
    if (initialized)
      return;
    Security.addProvider(new BouncyCastleProvider());
    initialized = true;
  }

  public static void main(String[] argv) throws Exception{

    String key = "cmbtest1";
    String data = "some words";
    String encrypted = new String(encrypt(data, key.getBytes()));
//    String decrypted = decrypt(encrypted.getBytes(), key.getBytes());

    log.info("encrypted:{}", encrypted);
//    log.info("decrypted:{}", decrypted);

  }
}

