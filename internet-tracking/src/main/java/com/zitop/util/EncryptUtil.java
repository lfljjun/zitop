package com.zitop.util;

import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Author: wooce
 * Date: 2009-10-30
 * Time: 17:09:51
 */
public class EncryptUtil {
    /**
     * MD5 加密
     */
    public static byte[] encryptMD5(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        return md5.digest();
    }

    /**
     * SHA 加密
     */
    public static byte[] encryptSHA(byte[] data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA");
        sha.update(data);
        return sha.digest();
    }

    /**
     * 取得HMAC密钥
     */
    public static String getMacKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
        SecretKey secretKey = keyGenerator.generateKey();
        return new BASE64Encoder().encode(secretKey.getEncoded());
    }

    public static String encryptHMAC(String data, String key) throws Exception {
        return toHex(encryptHMAC(data.getBytes(),key));
    }
    
    /**
     * 执行加密
     */
    public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
        byte[] bkey = new BASE64Decoder().decodeBuffer(key);
        SecretKey secretKey = new SecretKeySpec(bkey, "HmacMD5");
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);
    }

    private static String toHex(byte[] buffer) {
        StringBuffer sb = new StringBuffer(buffer.length * 3);
        for (int i = 0; i < buffer.length; i++) {
            sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
            sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
        }
        return sb.toString();
    }

    /**  
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]  
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程  
     *   
     * @param arrB  
     *            需要转换的byte数组  
     * @return 转换后的字符串  
     * @throws Exception  
     *             本方法不处理任何异常，所有异常全部抛出  
     */  
   private static String byteArr2HexStr(byte[] arrB) throws Exception {   
     int iLen = arrB.length;   
     // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍   
     StringBuffer sb = new StringBuffer(iLen * 2);   
     for (int i = 0; i < iLen; i++) {   
      int intTmp = arrB[i];   
      // 把负数转换为正数   
      while (intTmp < 0) {   
       intTmp = intTmp + 256;   
      }   
      // 小于0F的数需要在前面补0   
      if (intTmp < 16) {   
       sb.append("0");   
      }   
      sb.append(Integer.toString(intTmp, 16));   
     }   
     return sb.toString();   
   }   
     
   /**  
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)  
     * 互为可逆的转换过程  
     *   
     * @param strIn  
     *            需要转换的字符串  
     * @return 转换后的byte数组  
     * @throws Exception  
     *             本方法不处理任何异常，所有异常全部抛出 
     * 
     */  
   private static byte[] hexStr2ByteArr(String strIn) throws Exception {   
     byte[] arrB = strIn.getBytes();   
     int iLen = arrB.length;   
     
     // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2   
     byte[] arrOut = new byte[iLen / 2];   
     for (int i = 0; i < iLen; i = i + 2) {   
      String strTmp = new String(arrB, i, 2);   
      arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);   
     }   
     return arrOut;   
   }   
     


     
   /**  
     * 加密字节数组  
     *   
     * @param arrB  
     *            需加密的字节数组  
     * @return 加密后的字节数组  
     * @throws Exception  
     */  
   public static byte[] encryptDES(byte[] arrB,String strDefaultKey) throws Exception {   
       Key key = getDesKey(strDefaultKey.getBytes());
       Cipher cipher = Cipher.getInstance("DES");
       cipher.init(Cipher.ENCRYPT_MODE, key);
       return cipher.doFinal(arrB); 
   }   
     
   /**  
     * 加密字符串  
     *   
     * @param strIn  
     *            需加密的字符串  
     * @return 加密后的字符串  
     * @throws Exception  
     */  
   public static String encryptDES(String strIn,String strDefaultKey) throws Exception {   
     return byteArr2HexStr(encryptDES(strIn.getBytes(),strDefaultKey));   
   }   
     
   /**  
     * 解密字节数组  
     *   
     * @param arrB  
     *            需解密的字节数组  
     * @return 解密后的字节数组  
     * @throws Exception  
     */  
   public static byte[] decryptDES(byte[] arrB,String strDefaultKey) throws Exception {   
       Key key = getDesKey(strDefaultKey.getBytes());
       Cipher cipher = Cipher.getInstance("DES");
       cipher.init(Cipher.DECRYPT_MODE, key);
       return cipher.doFinal(arrB);
   }   
     
   /**  
     * 解密字符串  
     *   
     * @param strIn  
     *            需解密的字符串  
     * @return 解密后的字符串  
     * @throws Exception  
     */  
   public static String decryptDES(String strIn,String strDefaultKey) throws Exception {   
     return new String(decryptDES(hexStr2ByteArr(strIn),strDefaultKey));   
   }   
     
   /**  
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位  
     *   
     * @param arrBTmp  
     *            构成该字符串的字节数组  
     * @return 生成的密钥  
     * @throws java.lang.Exception  
     */  
   private static Key getDesKey(byte[] arrBTmp) throws Exception {   
     // 创建一个空的8位字节数组（默认值为0）   
     byte[] arrB = new byte[8];   
     
     // 将原始字节数组转换为8位   
     for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {   
      arrB[i] = arrBTmp[i];   
     }   
     
     // 生成密钥   
     Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");   
     
     return key;   
   }   
   
    public static void main(String[] args) {
        String msg = "admin200810245123";
        byte[] data = msg.getBytes();
        try {
            System.out.println("msg:" + msg);
            System.out.println("md5:" + toHex(encryptMD5(data)));
            System.out.println("sha:" + toHex(encryptSHA(data)));
            String key = getMacKey();
            System.out.println("mac key:" + key);                       //kxUa0WOMmFc43GycNcTP0MgqHomHXJeEc+82sQph+Go7t88gBiROgqvv/Mwh88Auqd0m93B1N47/aPRXUuN8tA==
            System.out.println("mac:" + toHex(encryptHMAC(data, "kxUa0WOMmFc43GycNcTP0MgqHomHXJeEc+82sQph+Go7t88gBiROgqvv/Mwh88Auqd0m93B1N47/\n" +
                    "aPRXUuN8tA==")));  //cddeb0abca0927281b6b316d9c94f2ab
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
