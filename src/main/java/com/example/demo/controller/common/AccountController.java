package com.example.demo.controller.common;

import com.example.demo.model.Account;
import com.example.demo.service.servicesImp.AccountService;
import com.example.demo.utils.Validate;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class AccountController {
    @Autowired
    AccountService accountService;
    @GetMapping("/profile")
    public String getAccountInfo(HttpSession session, Model model){
        Account account = (Account) session.getAttribute("account");
        model.addAttribute("account", account);
        return "/common/account";
    }

    @PostMapping("/profile")
    public String postAccountInfo(WebRequest request, HttpSession session, Model model) {
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        Account account = (Account) session.getAttribute("user");
        account.setPhone(phone);
        account.setAddress(address);
        account.setEmail(email);
        account.setName(fullName);
        session.setAttribute("account", account);
        if (!Validate.validEmail(email) || !Validate.validPhoneNumber(phone) || !Validate.validFullname(fullName)) {
            model.addAttribute("errmsg", "Update fail!");
        } else {
            accountService.saveAccount(account);
            model.addAttribute("errmsg", "Update success!");

        }
        model.addAttribute("account", account);
        return "profile";
    }
}
