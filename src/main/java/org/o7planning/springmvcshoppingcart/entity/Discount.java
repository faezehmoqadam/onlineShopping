package org.o7planning.springmvcshoppingcart.entity;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "discount")
public class Discount implements Serializable {
    @Autowired
    private SessionFactory sessionFactory;
    private static final long serialVersionUID = -2054388655979281969L;
    private String code;
    private Double amount;
    @Id
    @Column(name = "code", length = 8, nullable = false)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    @Column(name = "amount", length = 15, nullable = false)
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
