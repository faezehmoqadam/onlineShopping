package org.o7planning.springmvcshoppingcart.dao;

import org.o7planning.springmvcshoppingcart.entity.Discount;

public interface  DiscountDAO {


    public Discount findDiscount(String code);
    public void saveDiscount(String code, String amount);

}
