package com.demo.KaKao.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class KaKaoAuthService {
    public String getAccessToken(String code){
        String access_Token = "";
        String refresh_Token ="";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try{
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=3636ffa50d28afafd41c5a0c184f8210");
            sb.append("&redirect_url=https://192.168.0.97:8092/kakao");
            sb.append("&code="+code);
            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode ::"+responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line="";
            String result = "";

            while((line=br.readLine())!=null)
                result +=line;
            System.out.println("response Body ::"+result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token::"+access_Token);
            System.out.println("refresh_token::"+refresh_Token);
            br.close();
            bw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return access_Token;
    }
}
