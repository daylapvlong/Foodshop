package com.example.demo.service.servicesImp;

import com.example.demo.model.Account;
import com.example.demo.repository.IAccountRepository;
import com.example.demo.service.IAccountService;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService {
    @Autowired
    IAccountRepository accountRepository;

    @Override
    public Account findAccountByEmailAndPassword(String username, String password){
        return accountRepository.findAccountByEmailAndPassword(username, password);
    }

    @Override
    public Account findAccountByPhoneAndPassword(String username, String password){
        return accountRepository.findAccountByPhoneAndPassword(username,password);
    }

    @Override
    public Account findAccountByUsernameAndPassword(String username, String password) {
        Account account;
        if (username.contains("@"))
            account = accountRepository.findAccountByEmailAndPassword(username, password);
        else
            account = accountRepository.findAccountByPhoneAndPassword(username, password);
        return account;
    }

    public void setCookie(Cookie cu, Cookie cp, Cookie cr, String remember) {
        if(remember!=null){
            cu.setMaxAge(60*60*24*7);
            cp.setMaxAge(60*60*24*7);
            cr.setMaxAge(60*60*24*7);
        } else {
            cu.setMaxAge(0);
            cp.setMaxAge(0);
            cr.setMaxAge(0);
        }
    }

    @Override
    public boolean checkExistMail(String email) {
        if(accountRepository.findUserByEmail(email) != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkExistPhoneNumber(String phoneNumber) {
        if(accountRepository.findUserByPhone(phoneNumber) != null) {
            return true;
        }
        return false;
    }

    public Account createAccount(String fullName, String username, String password, int roleId) {
        Account account = new Account();
        account.setName(fullName);
        account.setEmail(username);
        account.setPassword(password);
        account.setRole(2);

        accountRepository.save(account);

        return account;
    }
}
