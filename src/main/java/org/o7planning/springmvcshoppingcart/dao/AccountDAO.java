package org.o7planning.springmvcshoppingcart.dao;

import org.o7planning.springmvcshoppingcart.entity.Account;

public interface AccountDAO {


    public Account findAccount(String userName );
    public void saveAccount (Account account);
    public void newSave(String un, String ps);


}