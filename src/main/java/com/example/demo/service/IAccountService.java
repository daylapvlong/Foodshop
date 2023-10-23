package com.example.demo.service;

import com.example.demo.model.Account;

public interface IAccountService {
    Account findAccountByEmailAndPassword(String username, String password);

    Account findAccountByPhoneAndPassword(String username, String password);

    Account findAccountByUsernameAndPassword(String username, String password);

    boolean checkExistMail(String email);

    boolean checkExistPhoneNumber(String phoneNumber);
}
