package com.example.demo.testApp;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;

import com.example.demo.tmpAcces.Util;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.google.gson.Gson;

public class Main {
    private static final String K = "gFn/XoAfNz0LjSnrsHc3CA=="; // this is temporary
    private static final long UID = 1;

    public static void main(String[] args) {
        // Example data - these should be derived from arguments or some input
        long patientid = 2;
        String firstName = "BND2";
        String lastName = "notshit";
        String dateOfBirth = "1990-01-01";
        long nationalId = 123456787L;
        int gender = 123;
        String email = "john.doe@example.com";
        String phoneNumber = "1234567890";
        String address = "1234 Street, City";
        String url = "http://localhost:8080";

        // the doctor data for testing purposes
        long doctorId = 1 ;
        String picture = " lol ";
        String about = " alger + LOL " ;
        String hashcodepin = " pin + LOL " ;
        String doctorStatus = " doctor status lol ";

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("patientId",patientid);
        map.put("firstName", firstName);
        map.put("lastName", lastName);
        map.put("dateOfBirth", dateOfBirth);
        map.put("nationalId", nationalId);
        map.put("gender", gender);
        map.put("email", email);
        map.put("phoneNumber", phoneNumber);
        map.put("sessionKey", K);
        map.put("address", address);

        // creating the doctor dto map to send
        Date date = new Date();
        date.getTime();
        LinkedHashMap<String , Object> map2 = new LinkedHashMap<>();
        map2.put("doctorId" , doctorId);
        map2.put("firstName", firstName);
        map2.put("lastName", lastName);
        map2.put("gender", gender);
        map2.put("picture", picture);
        map2.put("nationalId", nationalId);
        map2.put("about" ,about);
        map2.put("email", email);
        map2.put("phoneNumber", phoneNumber);
        map2.put("address", address);
        map2.put("hashedCodepin" , hashcodepin);
        map2.put("cardExpiringDate", "2024-12-31");
        map2.put("doctorStatus", doctorStatus);
//        try {
//            // Converting the map containing user data to JSON
//            String js = UtilRequest.mapToJsonString(map);
//            // Encrypt the data using AES with the admin's session key then base64
//            String data = Base64.getEncoder().encodeToString(UtilRequest.aesJsonToByte(js, K));
//            // Other useful parameters for the request
//            long timestamp = System.currentTimeMillis();
//            String hmac = UtilRequest.requestHash(timestamp, K, data);
//            // URL encode data
//            data = URLEncoder.encode(data, StandardCharsets.UTF_8.toString());
//            hmac = URLEncoder.encode(hmac, StandardCharsets.UTF_8.toString());
//            // Sending the request
//            UtilRequest.Response response = UtilRequest.sendRequest(
//                    "POST",
//                    "uid=" + UID + "&timestamp=" + timestamp + "&hmac=" + hmac + "&data=" + data,
//                    url + "/patients/getbyid",
//                    "application/x-www-form-urlencoded"
//
//            );
//            System.out.println();
//            System.out.println(response.getCode());
//            System.out.println(response.getBody());
//
//            String jsEncrypted = response.getBody();
//            String  jsdecrypted = Util.aesByteToJson(Base64.getDecoder().decode(jsEncrypted) , K);
//            System.out.println(jsdecrypted);
//
//        }catch (Exception e){}

        try {
            // Converting the map containing user data to JSON
            String js2 = UtilRequest.mapToJsonString(map2);
            // Encrypt the data using AES with the admin's session key then base64
            String data = Base64.getEncoder().encodeToString(UtilRequest.aesJsonToByte(js2, K));
            // Other useful parameters for the request
            long timestamp = System.currentTimeMillis();
            String hmac = UtilRequest.requestHash(timestamp, K, data);
            // URL encode data
            data = URLEncoder.encode(data, StandardCharsets.UTF_8.toString());
            hmac = URLEncoder.encode(hmac, StandardCharsets.UTF_8.toString());
            // Sending the request
            UtilRequest.Response response = UtilRequest.sendRequest(
                    "POST",
                    "uid=" + UID + "&timestamp=" + timestamp + "&hmac=" + hmac + "&data=" + data,
                    url + "/doctors/add",
                    "application/x-www-form-urlencoded"

            );
            System.out.println();
            System.out.println(response.getCode());
            System.out.println(response.getBody());

//            String jsEncrypted = response.getBody();
//            String  jsdecrypted = Util.aesByteToJson(Base64.getDecoder().decode(jsEncrypted) , K);
//            System.out.println(Base64.getEncoder().encode(jsdecrypted));

        }catch (Exception e){}

        try {
            UtilRequest.Response response2 = UtilRequest.sendRequest(
                    "GET",""
                    ,
                    "http://localhost:8080/connect/hiii","application/x-www-form-urlencoded"
            );
            System.out.println(response2.getBody());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("fin");
    }
}