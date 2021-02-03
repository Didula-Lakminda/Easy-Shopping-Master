package com.example.easyshopping.session;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class sessionManager {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    public static  final String SESSION_USERSESSION = "userLoginSession";
    public static  final String SESSION_REMEMBERME = "rememberMe";

    //user session varialbles
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_FULLNAME = "fullname";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONENO = "phoneNo";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_DATE = "date";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_PASSWORD = "password";

    //remember me variables
    private static final String IS_REMEMBERME = "IsRememberMe";
    public static final String KEY_SESSIONPHONENUMBER = "phoneNo";
    public static final String KEY_SESSIONPASSWORD = "password";

    public sessionManager(Context _context, String sessionName){
        context = _context;
        userSession = context.getSharedPreferences(sessionName,Context.MODE_PRIVATE);
        editor = userSession.edit();
    }
    //user start session
    public void createLoginSession(String fullname,String email,String phoneNo,String username,String date){

        editor.putBoolean(IS_LOGIN,true);

        editor.putString(KEY_FULLNAME,fullname);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_PHONENO,phoneNo);
        editor.putString(KEY_USERNAME,username);
        editor.putString(KEY_DATE,date);
        //editor.putString(KEY_GENDER,gender);
        //editor.putString(KEY_PASSWORD,password);

        editor.commit();

    }

    public HashMap<String, String> getUserDetailFromSession(){
        HashMap<String,String> userData = new HashMap<String, String>();

        userData.put(KEY_FULLNAME,userSession.getString(KEY_FULLNAME, null));
        userData.put(KEY_EMAIL,userSession.getString(KEY_EMAIL, null));
        userData.put(KEY_PHONENO,userSession.getString(KEY_PHONENO, null));
        userData.put(KEY_USERNAME,userSession.getString(KEY_USERNAME, null));
        userData.put(KEY_DATE,userSession.getString(KEY_DATE, null));
        //  userData.put(KEY_GENDER,userSession.getString(KEY_GENDER, null));
        //  userData.put(KEY_PASSWORD,userSession.getString(KEY_PASSWORD, null));

        return userData;
    }

    public boolean checkLogin(){
        if(userSession.getBoolean(IS_LOGIN, false))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }


    //remember me start session
    public void createRememberMeSession(String phoneNo,String password){

        editor.putBoolean(IS_REMEMBERME,true);

        editor.putString(KEY_SESSIONPHONENUMBER,phoneNo);
        editor.putString(KEY_SESSIONPASSWORD,password);

        editor.commit();

    }

    public HashMap<String, String> getRememberMeDetailFromSession(){
        HashMap<String,String> userData = new HashMap<String, String>();

        userData.put(KEY_SESSIONPHONENUMBER,userSession.getString(KEY_SESSIONPHONENUMBER, null));
        userData.put(KEY_SESSIONPASSWORD,userSession.getString(KEY_SESSIONPASSWORD, null));

        return userData;
    }

    public boolean checkRememberMe(){
        if(userSession.getBoolean(IS_REMEMBERME, false))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
