package com.rotamobile.gursan.data;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Server {

    public static final String Main_URL = "http://178.18.200.116:86/api/";


    public static String GetUsers(String username,String password){

        String method_Login = "UserService/UserLogin";

        try {

            URL url = new URL(Main_URL  + method_Login); // here is your URL path

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserName",username);
            jsonObject.put("Password", password);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(jsonObject));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in=new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line="";

                while((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();
            }
            else {
             //User Info issue
                //return new String("false : "+responseCode);
                Log.i("Exception: ",""+responseCode);
                return "false";
            }

        } catch (Exception e) {
           //Connection issue
            //return new String("Exception: " + e.getMessage());
            Log.i("Exception: ",e.getMessage());
            return "hata";
        }

    }

    public static String GetProjects(String userID) {

        String method_Projects = "ProjectService/ProjectAuthorized";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet httppost = new HttpGet(Main_URL + method_Projects + "?" + "UserId=" + userID);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
       }
        finally {
        try{

            if(inputStream != null)inputStream.close();

        }catch(Exception squish){
            return "false";
        }
    }
        return result;
}

    public static String GetTerritory(Integer projectID) {

        String method_Projects = "TerritoryService/TerritoryOfProject";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet httppost = new HttpGet(Main_URL + method_Projects + "?" + "ProjectId=" + projectID);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        }
        finally {
            try{

                if(inputStream != null)inputStream.close();

            }catch(Exception squish){
                return "false";
            }
        }
        return result;
    }

    public static String GetBuilding(Integer territoryID) {

        String method_Projects = "BuildingService/BuildingOfTerritory";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpPost httppost = new HttpPost(Main_URL + method_Projects + "?" + "TerritoryId=" + territoryID);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        }
        finally {
            try{

                if(inputStream != null)inputStream.close();

            }catch(Exception squish){
                return "false";
            }
        }
        return result;
    }

    public static String GetArea(Integer buildingID) {

        String method_Projects = "AreaService/AreaOfBuilding";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpPost httppost = new HttpPost(Main_URL + method_Projects + "?" + "BuildingId=" + buildingID);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        }
        finally {
            try{

                if(inputStream != null)inputStream.close();

            }catch(Exception squish){
                return "false";
            }
        }
        return result;
    }

    public static String GetDevice(Integer areaID) {

        String method_Projects = "DeviceService/DeviceOfArea";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpPost httppost = new HttpPost(Main_URL + method_Projects + "?" + "AreaId=" + areaID);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        }
        finally {
            try{

                if(inputStream != null)inputStream.close();

            }catch(Exception squish){
                return "false";
            }
        }
        return result;
    }

    public static String GetUserList() {

        String method_Projects = "UserService/UserGet";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet httppost = new HttpGet(Main_URL + method_Projects);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        }
        finally {
            try{

                if(inputStream != null)inputStream.close();

            }catch(Exception squish){
                return "false";
            }
        }
        return result;
    }

    public static String GetSubjects() {

        String method_Projects = "HelperService/GetAllSubjects";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet httppost = new HttpGet(Main_URL + method_Projects);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        }
        finally {
            try{

                if(inputStream != null)inputStream.close();

            }catch(Exception squish){
                return "false";
            }
        }
        return result;
    }

    public static String TodoAdd(Integer project_ID,Integer territory_ID,Integer building_ID,Integer area_ID,
                                 Integer device_ID,Integer workOrderType_ID,Integer workImportance_ID,Integer workOrderCategory_ID,Integer workTopic_ID,
                                 Integer user_ID,String description,Integer insertUser_ID,Integer updateUser_ID){

        String method_Login = "Todo/TodoAdd";

        try {

            URL url = new URL(Main_URL  + method_Login); // here is your URL path

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ProjectID", project_ID);
            jsonObject.put("TerritoryID", territory_ID);
            jsonObject.put("BuildingID", building_ID);
            jsonObject.put("AreaID", area_ID);
            jsonObject.put("DeviceID", device_ID);
            jsonObject.put("WorkOrderTypeID", workOrderType_ID);
            jsonObject.put("WorkImportanceID", workImportance_ID);
            jsonObject.put("WorkOrderCategoryID", workOrderCategory_ID);
            jsonObject.put("SubjectID",workTopic_ID);
            jsonObject.put("AssignedUserID", user_ID);
            jsonObject.put("Description", description);
            jsonObject.put("InsertUserID",insertUser_ID);
            jsonObject.put("UpdateUserID",updateUser_ID);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(jsonObject));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in=new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line="";

                while((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();
            }
            else {
                //User Info issue
                //return new String("false : "+responseCode);
                Log.i("Exception: ",""+responseCode);
                return "false";
            }

        } catch (Exception e) {
            //Connection issue
            //return new String("Exception: " + e.getMessage());
            Log.i("Exception: ",e.getMessage());
            return "false";
        }

    }

    public static String GetTodoList(Integer userID,Integer project_id,Integer user_type_id,Integer status) {

        String method_Projects = "Todo/TodoList";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet httppost = new HttpGet(Main_URL + method_Projects + "?" + "UserID=" + userID + "&" + "ProjectID=" + project_id + "&" + "UserTypeID=" + user_type_id + "&" +
        "Status=" + status);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        }
        finally {
            try{

                if(inputStream != null)inputStream.close();

            }catch(Exception squish){
                return "false";
            }
        }
        return result;
    }

    public static String TodoListUpdate(Integer id,Integer project_ID,Integer territory_ID,Integer building_ID,Integer area_ID,
                                 Integer device_ID,Integer workTopic_ID,
                                 Integer assigned_user_ID,Integer updateUser_ID,Integer workOrderService_ID,String description){

        String method_Login = "Todo/TodoListUpdate";

        try {

            URL url = new URL(Main_URL  + method_Login); // here is your URL path

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID",id);// WorkOrder ID
            jsonObject.put("ProjectID", project_ID);
            jsonObject.put("TerritoryID", territory_ID);
            jsonObject.put("BuildingID", building_ID);
            jsonObject.put("AreaID", area_ID);
            jsonObject.put("DeviceID", device_ID);
            jsonObject.put("SubjectID",workTopic_ID);
            jsonObject.put("AssignedUserID", assigned_user_ID);
            jsonObject.put("UpdateUserID",updateUser_ID);
            jsonObject.put("WorkOrderServiceID",workOrderService_ID);
            jsonObject.put("Description",description);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(jsonObject));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in=new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line="";

                while((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();
            }
            else {
                //User Info issue
                //return new String("false : "+responseCode);
                Log.i("Exception: ",""+responseCode);
                return "false";
            }

        } catch (Exception e) {
            //Connection issue
            //return new String("Exception: " + e.getMessage());
            Log.i("Exception: ",e.getMessage());
            return "false";
        }

    }

    public static String DocumentAdd(Integer workOrderID,Integer documentTypeID, Boolean active, String documentContent, String commentText, Integer insertUserID,
                                     Integer updateUserID){

        String method_Login = "DocumentService/DocumentAdd";

        try {

            URL url = new URL(Main_URL  + method_Login); // here is your URL path

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID",0);
            jsonObject.put("CommentID",0);
            jsonObject.put("WorkOrderID",workOrderID);// WorkOrder ID
            jsonObject.put("DocumentTypeID",documentTypeID);
            jsonObject.put("DocumentContent",documentContent);
            jsonObject.put("Active",active);
            jsonObject.put("InsertUserID",insertUserID);
            jsonObject.put("InsertDate","2019-03-28T12:46:04.135Z");
            jsonObject.put("UpdateUserID",updateUserID);
            jsonObject.put("UpdateDate","2019-03-28T12:46:04.135Z");
            jsonObject.put("UserID",0);
            jsonObject.put("CommentText",commentText);



            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(jsonObject));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in=new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line="";

                while((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();
            }
            else {
                //User Info issue
                //return new String("false : "+responseCode);
                Log.i("Exception: ",""+responseCode);
                return "false";
            }

        } catch (Exception e) {
            //Connection issue
            //return new String("Exception: " + e.getMessage());
            Log.i("Exception: ",e.getMessage());
            return "false";
        }

    }

    public static String GetDefinedList() {

        String method_Projects = "DEFDefinedTaskService/GetAll";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet httppost = new HttpGet(Main_URL + method_Projects);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        }
        finally {
            try{

                if(inputStream != null)inputStream.close();

            }catch(Exception squish){
                return "false";
            }
        }
        return result;
    }



    public static String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }




}
