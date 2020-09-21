package Model;

public class Products
{
    private String category,date,descryption,image,pid,price,productname,shopkeeper_id,time;

    public Products()
    {

    }

    public Products(String category, String date, String descryption, String image, String pid, String price, String productname, String shopkeeper_id, String time) {
        this.category = category;
        this.date = date;
        this.descryption = descryption;
        this.image = image;
        this.pid = pid;
        this.price = price;
        this.productname = productname;
        this.shopkeeper_id = shopkeeper_id;
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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getShopkeeper_id() {
        return shopkeeper_id;
    }

    public void setShopkeeper_id(String shopkeeper_id) {
        this.shopkeeper_id = shopkeeper_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
