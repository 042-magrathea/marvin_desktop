/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.app.dao.HTTPRequestDAO;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Helper class for Json methods
 * @author boscalent
 */
public class HTTPRequestDAOHelper {
    // HELPER FOR
    
    // POST
    // JSON PARSER
    
    private HTTPRequestDAOHelper(){}
    
       /**
     * Construeix el missatge POST per enviar al servidor PHP
     *
     * @param params paràmetres del webservice
     * @return un byte[] amb els paràmetres
     */
        ////////////////////// SINGLETON ///////////////////////////
    // Bill Pugh singleton pattern
    private static class LazyHolder {
        private static final HTTPRequestDAOHelper INSTANCE = new HTTPRequestDAOHelper();
    }

    public static HTTPRequestDAOHelper getInstance() {
        return LazyHolder.INSTANCE;
    }
    
    
    /**
     * Make POST Request from a MAP of params
     * @param params
     * @return Post query in byte[] format
     */
    public byte[] putParams(Map<String, Object> params) {
        byte[] postDataBytes = null;
        try {
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            postDataBytes = postData.toString().getBytes("UTF-8");
            System.out.println(postData.toString());
        } catch (UnsupportedEncodingException ex) {
        }
        return postDataBytes;
    }

    /**
     * Make Json[] froma Reader and a the name of a Node
     *
     * @param in Reader from conn
     * @param node Name of Node (Json Objects or null if is an Json[])
     * @return Json[]
     */
    public JsonArray getArrayFromJson(Reader in, String node) {
        JsonArray jarray = null;
        
        JsonElement jelement = new JsonParser().parse(in);
        if ( node != null){
            JsonObject jobject = jelement.getAsJsonObject();
            jarray = jobject.getAsJsonArray(node);
        } else {
            jarray = jelement.getAsJsonArray();
        }
        return jarray;
    }
}
