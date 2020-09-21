package Model;

public class customer {

    public String username,fullname,emailid,phoneno,gender;


    public customer(String s, String fullname, String emailid, String name, String phone, String email)
    {


    }

    public customer(String username, String fullname, String emailid, String phoneno, String gender) {
        this.username = username;
        this.fullname = fullname;
        this.emailid = emailid;
        this.phoneno = phoneno;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
