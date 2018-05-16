package com.si.simembers.Common;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Created by byun on 2018-03-28.
 */

public class RSAUtils {

    public static String encrypt(Context context, String value) {
        try {
            PublicKey publicKey = getPublicKey(context);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] cipherByteArray = cipher.doFinal(value.getBytes("UTF-8"));

            return new String(Base64.encode(cipherByteArray, Base64.NO_WRAP));
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static PublicKey getPublicKey(Context context) {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream keyStream = assetManager.open("public.pem");
            String publicKeyString = new String(toByteArray(keyStream), "UTF-8");

            publicKeyString = publicKeyString.replace("-----BEGIN PUBLIC KEY-----\n", "");
            publicKeyString = publicKeyString.replace("\n-----END PUBLIC KEY-----", "");
            publicKeyString = publicKeyString.replaceAll("(\\n|\\s|\\r)", "");

            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(publicKeyString.getBytes("UTF-8"), Base64.NO_WRAP));

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);

        }
        catch (Exception e) {
            Log.e("RSA", "Public key generating error");
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[8024];

        while ((nRead = input.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();

        return buffer.toByteArray();
    }
}
