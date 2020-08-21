package com.spacestudent.ssapi.payload.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

public class UsefulFunction {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UsefulFunction.class);
	
	private UsefulFunction() {
		
	}

    public static Date dateFromString(String pattern, String dateString){
        Date defaultDate = new Date();
        try {
            defaultDate = new SimpleDateFormat("yyyyMMddHHmmss").parse("20190101120000");
            return new SimpleDateFormat(pattern).parse(dateString);
        }catch (Exception ex){
            return defaultDate;
        }
    }

    public static String getListTrxType(List<String> trxType){
    	StringBuilder builder = new StringBuilder("(");
        for (String trx: trxType) {
        	builder.append("'" + trx + "',");
        }
        builder.append("'FKC')");
        return builder.toString();
    }

    public static int getIntFromString(String numberMessage){
        try{
            return Integer.parseInt(numberMessage);
        }catch (Exception ex){
            return 0;
        }
    }

    public static double getDoubleFromString(String numberMessage){
        try{
            return Double.parseDouble(numberMessage);
        }catch (Exception ex){
            return 0;
        }
    }

    public static String jsonEncode(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException var3) {
            return null;
        }
    }

    public static <T> T jsonDecode(String json, Class<T> tClass) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = json.replaceAll(", \" ",", \"");
            json = json.replaceAll(" \" :","\" :");
            json = json.replaceAll("\\n","");
            json = json.replaceAll("\\r","");
            return mapper.readValue(json, tClass);
        } catch (Exception var4) {
            LOGGER.error("{}", var4);
            return null;
        }
    }

    public static boolean isValidJson(String json) {
        try {
            new JSONObject(json);
        } catch (JSONException ex) {
            try {
                new JSONArray(json);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public static String currentDate(String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return sdf.format(timestamp);
    }

    public static Date getBeginDay(Date date){
	    try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateWithoutTime = sdf.parse(sdf.format(date));
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateWithoutTime);
            cal.set(Calendar.HOUR_OF_DAY,00);
            cal.set(Calendar.MINUTE,00);
            cal.set(Calendar.SECOND,0);
            cal.set(Calendar.MILLISECOND,0);
            return cal.getTime();
        }catch (Exception ex){
	        return date;
        }
    }

    public static Date getEndDay(Date date){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateWithoutTime = sdf.parse(sdf.format(date));
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateWithoutTime);
            cal.set(Calendar.HOUR_OF_DAY,23);
            cal.set(Calendar.MINUTE,59);
            cal.set(Calendar.SECOND,59);
            cal.set(Calendar.MILLISECOND,0);
            return cal.getTime();
        }catch (Exception ex){
            return date;
        }
    }

    public static String getHashSHA(String stringToHash) throws NoSuchAlgorithmException
    {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.update(stringToHash.getBytes(StandardCharsets.UTF_8), 0, stringToHash.length());
        byte[] sha1hash = messageDigest.digest();
        return convertToHex(sha1hash);
    }

    public static String getSignature(String userCode, String agSalt, String secretKey) {
        //Getting HMAC_SHA1
        try {
            SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(), "HmacSHA1");
            String dataToSign = userCode + agSalt;
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            return toHexString(mac.doFinal(dataToSign.getBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
           LOGGER.error("{}", e);
        }
        return null;
    }

    //Convert an hexadecimal (octet) to String
    public static String convertToHex(byte[] data)
    {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int twoHalfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (twoHalfs++ < 1);
        }
        return buf.toString();
    }

    //Convert an hexadecimal (octet) to String
    public static String toHexString(byte[] bytes) {
    	try(Formatter formatter = new Formatter();) {
    		for (byte b : bytes) {
                formatter.format("%02x", b);
            }
            return formatter.toString();
    	} catch (Exception e) {
    		LOGGER.error("{}", e);
		}  
    	return null;
    }
}
