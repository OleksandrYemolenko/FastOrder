package com.example.codersinlaw.fastorder;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Handler {
    public static final String token = "8675197a88457357fa2d87e1ca22ed3b";
    public static final String format = "json";
    public static final String link = "https://oleg-fomenko.joinposter.com";

    private String result = "error";

    public String sendRequest(String request, String requestType, String... parms) {
        String ss = link + "/api/" + request + "?token=" + token + "&format=" + format;
        for(String p : parms) ss += "&" + p;

        new AsyncReuest().execute(ss, requestType);
        return result;

        /*try {
            URL url = new URL(ss);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(requestType);
            BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String content = "", line = "";
            while((line = bf.readLine()) != null) {
                content += line;
            }

            return content;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "error";*/
    }

    class AsyncReuest extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... arg) {
            try {
                URL url = new URL(arg[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod(arg[1]);
                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String content = "", line = "";
                while((line = bf.readLine()) != null) {
                    content += line;
                }

                return content;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "error";
        }

        @Override
        protected void onPostExecute(String s) {
            result = s;
        }
    }
}


