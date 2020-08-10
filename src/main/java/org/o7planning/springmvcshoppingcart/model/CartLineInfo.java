package org.o7planning.springmvcshoppingcart.model;

public class CartLineInfo {

    private ProductInfo productInfo;
    private int quantity;
    private double shippingPrice = 15;


    public CartLineInfo() {
        this.quantity = 0;
    }

    public String getFee(){return productInfo.getFee();}
    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return this.productInfo.getPrice() * this.quantity;
    }

}