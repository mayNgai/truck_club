//package com.dtc.sevice.truckclub.helper;
//
//import android.util.Log;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.StatusLine;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.conn.ConnectTimeoutException;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicHeader;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpConnectionParams;
//import org.apache.http.params.HttpParams;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by May on 10/11/2017.
// */
//
//public class JSONParser {
//    static InputStream is = null;
//    static JSONArray jarray = null;
//    static String json = "";
//
//    // constructor
//    public JSONParser()
//    {
//
//    }
//
//    public static JSONArray getJSONFromUrl(String url)
//    {
//        StringBuilder builder = new StringBuilder();
//        HttpClient client = new DefaultHttpClient();
//        HttpGet httpGet = new HttpGet(url);
//        try
//        {
//            HttpResponse response = client.execute(httpGet);
//            StatusLine statusLine = response.getStatusLine();
//            int statusCode = statusLine.getStatusCode();
//            if (statusCode == 200)
//            {
//                HttpEntity entity = response.getEntity();
//                InputStream content = entity.getContent();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
//                String line;
//                while ((line = reader.readLine()) != null)
//                {
//                    builder.append(line);
//                }
//            }
//            else
//            {
//                Log.e("==>", "Failed to download file");
//            }
//        }
//        catch (ClientProtocolException e)
//        {
//            e.printStackTrace();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//
//        // try parse the string to a JSON object
//        try
//        {
//            jarray = new JSONArray( builder.toString());
//        }
//        catch (JSONException e)
//        {
//            Log.e("JSON Parser", "Error parsing data " + e.toString());
//        }
//
//        // return JSON String
//        return jarray;
//    }
//
//    public static JSONArray getJSONFromWebService(String url)
//    {
//        StringBuilder builder1 = new StringBuilder();
//        StringBuilder builder2 = null;
//        HttpClient client = new DefaultHttpClient();
//        HttpGet httpGet = new HttpGet(url);
//        try
//        {
//            HttpResponse response = client.execute(httpGet);
//            StatusLine statusLine = response.getStatusLine();
//            int statusCode = statusLine.getStatusCode();
//            if (statusCode == 200)
//            {
//                HttpEntity entity = response.getEntity();
//                InputStream content = entity.getContent();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
//                String line;
//                while ((line = reader.readLine()) != null)
//                {
//                    builder1.append(line);
//                }
//                String temp = String.valueOf(builder1);
//                if(temp.length()>0)
//                {
//                    int STchk = temp.indexOf("<?xml version=\"1.0\" encoding=\"utf-8\"?><string xmlns=\"http://tempuri.org/\">")+74;
//                    int ENDchk = temp.indexOf("</string>");
//                    temp = temp.substring(STchk, ENDchk);
//                }
//                builder2 = new StringBuilder(temp);
//            }
//            else
//            {
//                Log.e("==>", "Failed to download file");
//            }
//        }
//        catch (ClientProtocolException e)
//        {
//            e.printStackTrace();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//
//        // try parse the string to a JSON object
//        try
//        {
//            jarray = new JSONArray("["+builder2.toString()+"]");
//
//        }
//        catch (JSONException e)
//        {
//            Log.e("JSON Parser", "Error parsing data " + e.toString());
//        }
//
//        // return JSON String
//        return jarray;
//    }
//
//    public static JSONArray getJSONFromWebService(String url, String data)
//    {
//        URI absolute = null;
//        try
//        {
//            absolute = new URI(url);
//        }
//        catch (URISyntaxException ex)
//        {
//            ex.printStackTrace();
//        }
//        StringBuilder builder1 = new StringBuilder();
//        StringBuilder builder2 = null;
//        try
//        {
//            HttpPost post = new HttpPost();
//            post.setURI(absolute);
//            List params = new ArrayList();
//            params.add(new BasicNameValuePair("Data",data));
//            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
//
//            HttpParams httpParameters = new BasicHttpParams();
//            // Set the timeout in milliseconds until a connection is established.
//            int timeoutConnection = 50000;
//            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
//            // Set the default socket timeout (SO_TIMEOUT)
//            int timeoutSocket = 50000;
//            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
//
//            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
//            HttpResponse httpResponse = httpClient.execute(post);
//            HttpEntity messageEntity = httpResponse.getEntity();
//            InputStream content = messageEntity.getContent();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
//            String line;
//            while ((line = reader.readLine()) != null)
//            {
//                builder1.append(line);
//            }
//            String temp = String.valueOf(builder1);
//            if(temp.length()>0)
//            {
//                int STchk = temp.indexOf("{");
//                int ENDchk = temp.indexOf("}");
//                temp = temp.substring(STchk, ENDchk);
//            }
//            builder2 = new StringBuilder(temp);
//        }
//        catch (ConnectTimeoutException e)
//        {
//            e.printStackTrace();
//        }
//        catch (ClientProtocolException e)
//        {
//            e.printStackTrace();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//
//        // try parse the string to a JSON object
//        try
//        {
//            if(builder1.indexOf("]")>0){
//                jarray = new JSONArray( builder1.toString());
//            }else{
//                jarray = new JSONArray("[" + builder1.toString()+"]");
//            }
//
////			jarray = builder2.toString();
//        }
//        catch (JSONException e)
//        {
//            Log.e("JSON Parser", "Error parsing data " + e.toString());
//        }
//
//        // return JSON String
//        return jarray;
//    }
//
//    public static JSONArray getJSONFromWebService_NoTimeOut(String url, String data)
//    {
//        URI absolute = null;
//        try
//        {
//            absolute = new URI(url);
//        }
//        catch (URISyntaxException ex)
//        {
//            ex.printStackTrace();
//        }
//        StringBuilder builder1 = new StringBuilder();
//        StringBuilder builder2 = null;
//        try
//        {
//            HttpPost post = new HttpPost();
//            post.setURI(absolute);
//            List params = new ArrayList();
//            params.add(new BasicNameValuePair("Data",data));
//            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
//            DefaultHttpClient httpClient = new DefaultHttpClient();
//            HttpResponse httpResponse = httpClient.execute(post);
//            HttpEntity messageEntity = httpResponse.getEntity();
//            InputStream content = messageEntity.getContent();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
//            String line;
//            while ((line = reader.readLine()) != null)
//            {
//                builder1.append(line);
//            }
//            String temp = String.valueOf(builder1);
//            if(temp.length()>0)
//            {
//                int STchk = temp.indexOf("<?xml version=\"1.0\" encoding=\"utf-8\"?><string xmlns=\"http://tempuri.org/\">")+74;
//                int ENDchk = temp.indexOf("</string>");
//                temp = temp.substring(STchk, ENDchk);
//            }
//            builder2 = new StringBuilder(temp);
//        }
//        catch (ConnectTimeoutException e)
//        {
//            e.printStackTrace();
//        }
//        catch (ClientProtocolException e)
//        {
//            e.printStackTrace();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//
//        // try parse the string to a JSON object
//        try
//        {
//            jarray = new JSONArray(builder2.toString());
//        }
//        catch (JSONException e)
//        {
//            Log.e("JSON Parser", "Error parsing data " + e.toString());
//        }
//
//        // return JSON String
//        return jarray;
//    }
//    // === Narist ===
//    public JSONObject SendAndGetJson_reJsonObject(JSONObject json_send, String url){
//        try{
//            HttpParams myParams = new BasicHttpParams();
//            HttpConnectionParams.setConnectionTimeout(myParams, 20000);
//            HttpConnectionParams.setSoTimeout(myParams, 20000);
//            HttpClient httpclient = new DefaultHttpClient(myParams);
//            String json = json_send.toString();
//
//            HttpPost httppost = new HttpPost(url.toString());
//            httppost.setHeader("Content-type", "application/json");
//
//            StringEntity se = new StringEntity(json, HTTP.UTF_8);
//            se.setContentEncoding(new BasicHeader(
//                    HTTP.CONTENT_TYPE, "application/json"));
//            httppost.setEntity(se);
//
//            HttpResponse response = httpclient.execute(httppost);
//            String temp = EntityUtils.toString(response.getEntity());
//            Log.d("NARIS", temp);
//
//            JSONObject json_return = new JSONObject(temp);
//
//            return json_return;
//        }catch(Exception e){
//            return null;
//        }
//
//
//
//    }
//
//
//    public static JSONArray SendAndGetJson_reJsonArray(String json_send, String url){
//        try{
//            //AndroidHttpClient
//            HttpParams myParams = new BasicHttpParams();
//            HttpConnectionParams.setConnectionTimeout(myParams, 20000);
//            HttpConnectionParams.setSoTimeout(myParams, 20000);
//            HttpClient httpclient = new DefaultHttpClient(myParams);
//            String json = json_send.toString();
//
//            //HttpPost request
//            HttpPost httppost = new HttpPost(url.toString());
//            httppost.setHeader("Content-type", "application/json");
//
//            //Set Entity to populate the request
//            StringEntity se = new StringEntity(json,HTTP.UTF_8);
//            se.setContentEncoding(new BasicHeader(
//                    HTTP.CONTENT_TYPE, "application/json"));
//            httppost.setEntity(se);
//
//            HttpResponse response = httpclient.execute(httppost);
//            String temp = EntityUtils.toString(response.getEntity());
//
//            JSONArray json_return = new JSONArray(temp);
//            return json_return;
//        }catch(Exception e){
//            return null;
//        }
//    }
//
//    public static JSONObject SendAndGetJson_reJsonObject(String json_send, String url){
//        try{
//            //AndroidHttpClient
//            HttpParams myParams = new BasicHttpParams();
//            HttpConnectionParams.setConnectionTimeout(myParams, 20000);
//            HttpConnectionParams.setSoTimeout(myParams, 20000);
//            HttpClient httpclient = new DefaultHttpClient(myParams);
//            String json = json_send.toString();
//
//            //HttpPost request
//            HttpPost httppost = new HttpPost(url.toString());
//            httppost.setHeader("Content-type", "application/json");
//
//            //Set Entity to populate the request
//            StringEntity se = new StringEntity(json,HTTP.UTF_8);
//            se.setContentEncoding(new BasicHeader(
//                    HTTP.CONTENT_TYPE, "application/json"));
//            httppost.setEntity(se);
//
//            HttpResponse response = httpclient.execute(httppost);
//            String temp = EntityUtils.toString(response.getEntity());
//
//            JSONObject json_return = new JSONObject(temp);
//            return json_return;
//        }catch(Exception e){
//            return null;
//        }
//    }
//}
