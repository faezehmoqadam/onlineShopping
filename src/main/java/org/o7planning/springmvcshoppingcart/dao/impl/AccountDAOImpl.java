package org.o7planning.springmvcshoppingcart.dao.impl;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.o7planning.springmvcshoppingcart.dao.AccountDAO;
import org.o7planning.springmvcshoppingcart.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class AccountDAOImpl implements AccountDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Account findAccount(String userName ) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Account.class);
        crit.add(Restrictions.eq("userName", userName));
        return (Account) crit.uniqueResult();
    }
    @Override
    public void saveAccount(Account account) {
        Session session = sessionFactory.getCurrentSession();
        account.setActive(true);
        account.setUserRole("EMPLOYEE");

        session.save(account);
    }
    @Override
    public void newSave (String un, String ps){
        Account acc = new Account();
        acc.setPassword(ps);
        acc.setUserName(un);
        acc.setActive(true);
        acc.setUserRole("EMPLOYEE");
        sessionFactory.getCurrentSession().save(acc);
    }

}