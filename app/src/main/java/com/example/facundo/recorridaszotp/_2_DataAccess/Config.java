package com.example.facundo.recorridaszotp._2_DataAccess;

import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gonzalo on 13/04/16.
 */
public class Config {
    private static Config instance = null;
    private int rol = -1;
    private int area = -1;
    private int userWebId;
 //   private String userMail = "rodrif89@gmail.com"; 123456789
    private String userMail = "";
    private String userPassword = "";
    private boolean isLoginOk = false;
    private Map<String, String> token;
    private int numIntento;
    private boolean isEditing = false;

    private Config () {
        this.token = new HashMap<String, String>();
        this.token.put(Utils.TOKEN_TYPE, Utils.BEARER);
        this.token.put(Utils.CLIENT,"");
        this.token.put(Utils.EXPIRY,"");
        this.token.put(Utils.UID,"");
        this.token.put(Utils.ACCESS_TOKEN,"");
    }

    static public Config getInstance() {
        if (instance == null) {
            instance = new Config();

        }
        return instance;
    }

    public boolean isLoginOk() {
        return isLoginOk;
    }

    public void setLoginOk() {
        this.isLoginOk = true;
    }

    public void setIsLoginOk(boolean isLoginOk) {
        this.isLoginOk = isLoginOk;
    }

    public int getRol() {
        return this.rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public int getNumIntento() {
        return this.numIntento;
    }

    public void setNumIntento(int numIntento) {
        this.numIntento = numIntento;
    }

    public String getExpiry() {
        return this.token.get(Utils.EXPIRY);
    }

    public void setExpiry(String expiry) {
        this.token.put(Utils.EXPIRY, expiry);
    }

    public String getUid() {
        return this.token.get(Utils.UID);
    }

    public void setUid(String uid) {
        this.token.put(Utils.UID,uid);
    }

    public String getClient() {
        return this.token.get(Utils.CLIENT);
    }

    public void setClient(String client) {
        this.token.put(Utils.CLIENT, client);
    }

    public String getAccessToken() {
        return this.token.get(Utils.ACCESS_TOKEN);
    }

    public void setAccessToken(String token) {
        this.token.put(Utils.ACCESS_TOKEN, token);
    }

    public String getTokenType() {
        return this.token.get(Utils.TOKEN_TYPE);
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserWebId() {
        return userWebId;
    }

    public void setUserWebId(int userWebId) {
        this.userWebId = userWebId;
    }

    public boolean isEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public void logOut() {
        isLoginOk = false;
        userMail = "";
        userPassword = "";
        rol = -1;
        isEditing = false;
    }
}
