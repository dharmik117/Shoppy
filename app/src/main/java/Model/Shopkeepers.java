package Model;

public class Shopkeepers {

    public String userid,shopname,emailid,phoneno,Permission;

    public Shopkeepers(String s, String name)
    {

    }

    public Shopkeepers(String username, String fullname, String emailid, String phoneno, String Permission) {
        this.userid = username;
        this.shopname = fullname;
        this.emailid = emailid;
        this.phoneno = phoneno;
        this.Permission = Permission;
    }
}
