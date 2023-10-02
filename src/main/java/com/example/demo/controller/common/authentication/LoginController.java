package com.example.demo.controller.common.authentication;

import com.example.demo.model.Account;
import com.example.demo.service.servicesImp.AccountService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import java.net.http.HttpResponse;

@Controller
public class LoginController {
    private final String afterLoginRoute = "/home";

    @Autowired
    AccountService accountService;

    @RequestMapping("/login")
    public String loginPage(
            @CookieValue(value = "cuser") String cuser,
            @CookieValue(value = "cpassword") String cpassword,
            @CookieValue(value = "cremember") String cremember,
            Model model) {
        model.addAttribute("cuser", cuser);
        model.addAttribute("cpassword", cpassword);
        model.addAttribute("cremember", cremember);
        return "login";
    }

    @PostMapping("/login")
    public String userLogin(
            @RequestParam String username,
            @RequestParam String password,
            Model model,
            HttpSession session,
            HttpServletResponse response,
            WebRequest request
    ) {
        username = username.replace("+84", "0").replace(" ", "");
        model.addAttribute("cuser", username);
        Account account = accountService.findAccountByUsernameAndPassword(username.trim(), password);
        if (account != null) {
            session.setAttribute("user", account);
            //add to cookie
            String remember = request.getParameter("remember");
            Cookie cu = new Cookie("cuser", username);
            Cookie cp = new Cookie("cpass", password);
            Cookie cr = new Cookie("crem", remember);
            accountService.setCookie(cu, cp, cr, remember);
            response.addCookie(cu);
            response.addCookie(cp);
            response.addCookie(cr);
            return "redirect:" + afterLoginRoute;
        } else if (account == null) {
            model.addAttribute("errmsg", "Username or password is not correct");
        }
        return "authentication/login";
    }

}
