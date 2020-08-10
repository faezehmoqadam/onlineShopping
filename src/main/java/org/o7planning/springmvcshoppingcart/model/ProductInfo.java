package org.o7planning.springmvcshoppingcart.model;

import org.o7planning.springmvcshoppingcart.entity.Product;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ProductInfo {
    private String code;
    private String name;
    private double price;
    private  String category;
    private String Fee;
    private boolean exist;
//    private int quantity;

    private boolean newProduct=false;


    private CommonsMultipartFile fileData;

    public ProductInfo() {
    }

    public ProductInfo(Product product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
        this.category = product.getCategory();
        this.Fee = product.getFee();
        this.exist = product.isExist();
//        this.quantity = product.getQuantity();
    }

    public ProductInfo(String code, String name, double price, String category, String Fee, boolean exist) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.category = category;
        this.Fee = Fee;
//        this.quantity = quantity;
        this.exist = exist;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public CommonsMultipartFile getFileData() {
        return fileData;
    }

    public void setFileData(CommonsMultipartFile fileData) {
        this.fileData = fileData;
    }

    public boolean isNewProduct() {
        return newProduct;
    }

    public void setNewProduct(boolean newProduct) {
        this.newProduct = newProduct;
    }

    public String getFee() {
        return Fee;
    }

    public void setFee(String fee) {
        Fee = fee;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
}