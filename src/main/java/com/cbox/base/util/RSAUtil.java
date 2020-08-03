package com.cbox.base.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Map;
import java.util.Properties;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;

//@Component
public class RSAUtil {
    private static RSAPublicKey rsap = null;
    // private static final String RAS_ALGORITHM = "RSA";
    private static final String RESOURCE_FILE = "RSAKey.txt";
    private static String rsaModule = null;
    private static String rsaPublicExponent = null;
    private static String RESOURCE_PATH;
    public static final int KEY_SIZE = 512;

    @Value("${web.upload-path:}")
    public void setResourcePath(String resourcePath) {
        RESOURCE_PATH = resourcePath + "RSA" + File.separator;// 默认放到上传目录下
    }

    public static KeyPair generateKeyPair() {
        KeyPairGenerator keyPairGen = null;

        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA", "BC");
        } catch (NoSuchAlgorithmException arg1) {
            throw new RuntimeException("产生密钥对抛出违例，请检查密钥产生指定的算法是否正确", arg1);
        } catch (NoSuchProviderException arg2) {
            arg2.printStackTrace();
        }

        keyPairGen.initialize(512, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        saveKeyPair(keyPair);
        System.out.println("密钥对创建成功过，密钥长度为：512");
        return keyPair;
    }

    public static KeyPair getKeyPair() {
        if (rsap == null) {
            rsap = (RSAPublicKey) generateKeyPair().getPublic();
        }

        String path = getResource();
        ObjectInputStream ois = null;
        KeyPair kp = null;

        try {
            ois = new ObjectInputStream(new FileInputStream(path));
            kp = (KeyPair) ois.readObject();
        } catch (IOException arg11) {
            throw new RuntimeException("加载密钥对出错，请检查加载的文件路径" + path, arg11);
        } catch (ClassNotFoundException arg12) {
            throw new RuntimeException("加载密钥对出错，需要的类文件不存在，请检测依赖jar包", arg12);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException arg10) {
                    throw new RuntimeException("关闭对象输入流出错，请检查加载的文件路径" + path, arg10);
                }
            }

        }

        return kp;
    }

    private static String getResource() {
        StringBuffer file = new StringBuffer();
        file.append(RESOURCE_PATH).append(RESOURCE_FILE);
        FileUtil.createFile(file.toString());
        return file.toString();
    }

    public static void saveKeyPair(KeyPair kp) {
        String path = getResource();
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(kp);
        } catch (IOException arg10) {
            throw new RuntimeException("保存密钥对到" + path + "时出错，请检查保存的文件路径", arg10);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException arg9) {
                    throw new RuntimeException("关闭文件输入流资源" + path + "时出错，请检查文件路径", arg9);
                }
            }

        }

    }

    public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) {
        KeyFactory keyFac = null;
        RSAPublicKey publicKey = null;

        try {
            keyFac = KeyFactory.getInstance("RSA", "BC");
            RSAPublicKeySpec e = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
            publicKey = (RSAPublicKey) keyFac.generatePublic(e);
        } catch (NoSuchAlgorithmException arg4) {
            throw new RuntimeException("创建密钥工厂抛出违例，请检查密钥产生指定的算法是否正确", arg4);
        } catch (InvalidKeySpecException arg5) {
            throw new RuntimeException("创建公钥出错，请检查密钥规格是否正确", arg5);
        } catch (NoSuchProviderException arg6) {
            arg6.printStackTrace();
        }

        return publicKey;
    }

    public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) {
        KeyFactory keyFac = null;
        RSAPrivateKey privateKey = null;

        try {
            keyFac = KeyFactory.getInstance("RSA", "BC");
            RSAPrivateKeySpec e = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));
            privateKey = (RSAPrivateKey) keyFac.generatePrivate(e);
        } catch (NoSuchAlgorithmException arg4) {
            throw new RuntimeException("创建密钥工厂抛出违例，请检查密钥产生指定的算法是否正确", arg4);
        } catch (InvalidKeySpecException arg5) {
            throw new RuntimeException("创建私钥出错，请检查密钥规格是否正确", arg5);
        } catch (NoSuchProviderException arg6) {
            arg6.printStackTrace();
        }

        return privateKey;
    }

    public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA", "BC");
        cipher.init(1, pk);
        int blockSize = cipher.getBlockSize();
        int outputSize = cipher.getOutputSize(data.length);
        int leavedSize = data.length % blockSize;
        int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
        byte[] raw = new byte[outputSize * blocksSize];

        for (int i = 0; data.length - i * blockSize > 0; ++i) {
            if (data.length - i * blockSize > blockSize) {
                cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
            } else {
                cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
            }
        }

        return raw;
    }

    public static byte[] decrypt(PrivateKey pk, byte[] raw) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA", "BC");
        cipher.init(2, pk);
        int blockSize = cipher.getBlockSize();
        ByteArrayOutputStream bout = new ByteArrayOutputStream(64);

        for (int j = 0; raw.length - j * blockSize > 0; ++j) {
            bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
        }

        return bout.toByteArray();
    }

    public static String decryptJsData(PrivateKey pk, String encryptData) throws Exception {
        String convertData = convertBitLenth(encryptData);
        byte[] bytes = hex2Byte(convertData);
        byte[] decryptBytes = decrypt(pk, bytes);
        StringBuffer buffer = (new StringBuffer(new String(decryptBytes))).reverse();
        return buffer.toString();
    }

    public static String convertBitLenth(String encryptData) {
        short length = 128;
        int size = length - encryptData.length();
        String zero = "";

        for (int i = 0; i < size; ++i) {
            zero = zero + "0";
        }

        return zero + encryptData;
    }

    public static String byte2Hex(byte[] bytes) {
        String hs = "";
        String stmp = "";

        for (int i = 0; i < bytes.length; ++i) {
            stmp = Integer.toHexString(bytes[i] & 255);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }

        return hs.toUpperCase();
    }

    public static byte[] hex2Byte(String hex) {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException();
        } else {
            char[] arr = hex.toCharArray();
            byte[] b = new byte[hex.length() / 2];
            int i = 0;
            int j = 0;

            for (int l = hex.length(); i < l; ++j) {
                String swap = "" + arr[i++] + arr[i];
                int byteint = Integer.parseInt(swap, 16) & 255;
                b[j] = (new Integer(byteint)).byteValue();
                ++i;
            }

            return b;
        }
    }

    public static String decryptJsData(String encryptData) throws Exception {
        String decryptDate = "";
        String[] encryptArr = encryptData.split(" ");
        PrivateKey pk = getKeyPair().getPrivate();

        for (int i = 0; i < encryptArr.length; ++i) {
            decryptDate = decryptDate + decryptJsData(pk, convertBitLenth(encryptArr[i]));
        }

        return decryptDate;
    }

    public static void addRsaModuleAndPublicExponentToMap(Map<String, Object> modelMap) {
        addRsaModuleAndPublicExponentToMap(modelMap, (String) null, (String) null);
    }

    public static void addRsaModuleAndPublicExponentToMap(Map<String, Object> modelMap, String rsaModuleKey, String rsaPublicExponentKey) {
        if (modelMap != null) {
            if (rsaModule == null || rsaPublicExponent == null) {
                try {
                    RSAPublicKey e = (RSAPublicKey) getKeyPair().getPublic();
                    rsaModule = e.getModulus().toString(16);
                    rsaPublicExponent = e.getPublicExponent().toString(16);
                } catch (Exception arg3) {
                    arg3.printStackTrace();
                }
            }

            if (rsaModuleKey == null) {
                rsaModuleKey = "rsaModule";
            }

            if (rsaPublicExponentKey == null) {
                rsaPublicExponentKey = "rsaEmpoent";
            }

            modelMap.put(rsaModuleKey, rsaModule);
            modelMap.put(rsaPublicExponentKey, rsaPublicExponent);
        }

    }

    public static String readValue(String fileName, String key) {
        try {
            Properties e = new Properties();
            InputStream ips = RSAUtil.class.getClassLoader().getResourceAsStream(fileName);
            if (ips == null) {
                throw new RuntimeException("配置文件" + fileName + "不存在");
            } else {
                e.load(ips);
                String value = e.getProperty(key);
                System.out.println(key + "=" + value);
                return value;
            }
        } catch (Exception arg4) {
            arg4.printStackTrace();
            throw new RuntimeException(arg4);
        }
    }

    static {
        Security.addProvider(new BouncyCastleProvider());

        try {
            rsap = (RSAPublicKey) generateKeyPair().getPublic();
        } catch (Exception arg0) {
            rsap = null;
        }

    }
}