package Model;

public class Cart
{
    private String dateandtime,discount,pid,pname,price,quentity,shop,userid;

    public Cart()
    {

    }

    public Cart(String dateandtime, String discount, String pid, String pname, String price, String quentity, String shop, String userid) {
        this.dateandtime = dateandtime;
        this.discount = discount;
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.quentity = quentity;
        this.shop = shop;
        this.userid = userid;
    }

    public String getDateandtime() {
        return dateandtime;
    }

    public void setDateandtime(String dateandtime) {
        this.dateandtime = dateandtime;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuentity() {
        return quentity;
    }

    public void setQuentity(String quentity) {
        this.quentity = quentity;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
