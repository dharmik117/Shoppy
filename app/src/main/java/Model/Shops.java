package Model;

public class Shops
{
    private String category,date,descryption,image,permission,shopid,shopkeeperid,shopname,time;

    public Shops()
    {

    }

    public Shops(String category, String date, String descryption, String image, String permission, String shopid, String shopkeeperid, String shopname, String time) {
        this.category = category;
        this.date = date;
        this.descryption = descryption;
        this.image = image;
        this.permission = permission;
        this.shopid = shopid;
        this.shopkeeperid = shopkeeperid;
        this.shopname = shopname;
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescryption() {
        return descryption;
    }

    public void setDescryption(String descryption) {
        this.descryption = descryption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getShopkeeperid() {
        return shopkeeperid;
    }

    public void setShopkeeperid(String shopkeeperid) {
        this.shopkeeperid = shopkeeperid;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
