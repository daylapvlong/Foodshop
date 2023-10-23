package com.example.demo.controller.common.authentication;

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

import java.security.NoSuchAlgorithmException;

@Controller
public class RegisterController {
    @Autowired
    AccountService accountService;

    @GetMapping("/register")
    public String registerPage(Model model) {
        return "register";
    }
    @PostMapping("/register")
    public String registerAccount(WebRequest request, Model model, HttpSession session)
            throws NoSuchAlgorithmException {
        // get parameter from request
        String fullname = request.getParameter("fullname").trim();
        String termCheckbox = request.getParameter("termCheckbox");
        String username = request.getParameter("username").replace(" ", "");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // set session to save data
        session.setAttribute("r-fullname", fullname);
        session.setAttribute("r-username", username);

        // create model user
        Account account = new Account();
        account.setName(fullname);
        account.setPassword(password);
        account.setRole(2);

        if (Validate.validEmail(username)) {
            account.setEmail(username);
        }
        if (Validate.validPhoneNumber(username)) {
            account.setPhone(username);
        }

        // Validate Full Name
        if (!Validate.validFullname(fullname)) {
            return handleError(model, session, "Fullname must contain at least 2 words");
        }

        // Check Term Checkbox
        if (termCheckbox == null) {
            return handleError(model, session, "You must agree with our terms and conditions");
        }

        // Validate Email or Phone
        if (account.getEmail() == null && account.getPhone() == null) {
            return handleError(model, session, "Invalid email or phone number");
        }

        // Check Email Existence
        if (account.getEmail() != null) {
            if (accountService.checkExistMail(account.getEmail())) {
                return handleError(model, session, "Email already exists!");
            }
        }

        // Check Phone Number Existence
        if (account.getPhone() != null && accountService.checkExistPhoneNumber(account.getPhone())) {
            return handleError(model, session, "Phone number already exists!");
        }

        // Validate Password
        if (!Validate.validPassword(password)) {
            return handleError(model, session, "Password must contain at least 8 characters and have uppercase, lowercase, and a number");
        }


        if (!password.equals(confirmPassword)) {
            return handleError(model, session, "Password and confirm password must be the same");
        }

        accountService.createAccount(fullname, username, password, 2);
        return "redirect:/login";
    }

    // Helper method to handle errors
    private String handleError(Model model, HttpSession session, String errorMessage) {
        model.addAttribute("errmsg", errorMessage);
        model.addAttribute("fullname", session.getAttribute("r-fullname"));
        model.addAttribute("username", session.getAttribute("r-username"));
        session.removeAttribute("r-fullname");
        session.removeAttribute("r-username");
        return "common/register";
    }


}
