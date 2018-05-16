package com.si.simembers.Common;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;

/**
 * Created by byun on 2018-03-28.
 */

public class TokenManager {

    private Context context;

    public TokenManager(Context context) {
        this.context = context;
    }

    public String createToken(String txt) {
        if (TextUtils.isEmpty(txt)) {
            return "";
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm", Locale.KOREA);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

        String deviceId = getDeviceId();
        String currentTime = dateFormat.format(new Date());

        txt = txt + "||" + deviceId + "||" + currentTime;
        txt = RSAUtils.encrypt(context, txt);
        txt = txt + "@" + deviceId;

        try {
            txt = URLEncoder.encode(txt, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return txt;
    }

    private String getDeviceId() {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        UUID uuid = null;

        try {
            if (!"9774d56d682e549c".equals(androidId)) {
                uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
            }
            else {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                    uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                }
            }
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (uuid != null && !TextUtils.isEmpty(uuid.toString())) {
            return uuid.toString();
        }
        else {
            return createId();
        }
    }

    private String createId() {
        StringBuilder newId = new StringBuilder();
        Random random = new Random();

        while (newId.length() < 10) {
            newId.append(random.nextInt(9));
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(newId.toString().getBytes());

            newId = new StringBuilder();
            byte[] byteStrings = digest.digest();

            for (byte byteString : byteStrings) {
                newId.append(Integer.toHexString((int) byteString & 0x00ff));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return newId.toString();
    }
}


