package Model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Sessions
{
    SharedPreferences usersessions;
    SharedPreferences.Editor editor;
    Context context;

    private static  final String IS_Login = "IsLoggedin";

    public static final String KEY_Emailid = "emailid";
    public static final String KEY_Fullname = "fullname";
    public static final String KEY_Gender = "gender";
    public static final String KEY_Phoneno = "phoneno";
    public static final String KEY_Username = "username";

    public Sessions (Context _context)
    {
        context = _context;
        usersessions = context.getSharedPreferences("userloginsession",Context.MODE_PRIVATE);
        editor = usersessions.edit();
    }

    public void createloginsession(String emailid, String fullname,String gender,String phoneno,String username)
    {
        editor.putBoolean(IS_Login,true);

        editor.putString(KEY_Emailid,emailid);
        editor.putString(KEY_Fullname,fullname);
        editor.putString(KEY_Gender,gender);
        editor.putString(KEY_Phoneno,phoneno);
        editor.putString(KEY_Username,username);

        editor.commit();
    }

    public HashMap<String, String> getuserdetails()
    {
        HashMap<String, String> userdata = new HashMap<String, String>();

        userdata.put(KEY_Emailid,usersessions.getString(KEY_Emailid,null));
        userdata.put(KEY_Fullname,usersessions.getString(KEY_Fullname,null));
        userdata.put(KEY_Gender,usersessions.getString(KEY_Gender,null));
        userdata.put(KEY_Phoneno,usersessions.getString(KEY_Phoneno,null));
        userdata.put(KEY_Username,usersessions.getString(KEY_Username,null));

        return userdata;
    }

    public boolean checklogin()
    {
        if(usersessions.getBoolean(IS_Login,false))
        {
            return true;
        }
        else return false;
    }

    public void logoutuserfromsession()
    {
        editor.clear();
        editor.commit();
    }
}
