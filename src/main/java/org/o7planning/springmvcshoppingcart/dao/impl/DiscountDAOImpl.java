package org.o7planning.springmvcshoppingcart.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.o7planning.springmvcshoppingcart.dao.DiscountDAO;
import org.o7planning.springmvcshoppingcart.entity.Discount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class DiscountDAOImpl implements DiscountDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Discount findDiscount(String code) {
            Session session = sessionFactory.getCurrentSession();
            Criteria crit = session.createCriteria(Discount.class);
            crit.add(Restrictions.eq("code", code));
            return (Discount) crit.uniqueResult();
    }

    @Override
    public void saveDiscount(String code, String amount) {
        Session session = sessionFactory.getCurrentSession();
        Discount dis = new Discount();
        dis.setAmount(Double.parseDouble(amount));
        dis.setCode(code);
        session.save(dis);
    }
}
