package edu.uncc.assessment06;

import java.io.Serializable;

public class CartProduct implements Serializable {
    String pid,name,img_url,price,description,review_count,created_by_user,doc_id;

    CartProduct() {

    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReview_count() {
        return review_count;
    }

    public void setReview_count(String review_count) {
        this.review_count = review_count;
    }

    public String getCreated_by_user() {
        return created_by_user;
    }

    public void setCreated_by_user(String created_by_user) {
        this.created_by_user = created_by_user;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    @Override
    public String toString() {
        return "CartProduct{" +
                "pid='" + pid + '\'' +
                ", name='" + name + '\'' +
                ", img_url='" + img_url + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                ", review_count='" + review_count + '\'' +
                ", created_by_user='" + created_by_user + '\'' +
                ", doc_id='" + doc_id + '\'' +
                '}';
    }
}
