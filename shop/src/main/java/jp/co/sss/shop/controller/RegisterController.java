package jp.co.sss.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.sss.shop.form.RegisterForm;

@Controller
public class RegisterController {

    @GetMapping("/register")
    public String registerForm(RegisterForm form) {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Validated RegisterForm form,
                           BindingResult result
    ) {

        //Email一致チェック
        if (!form.getEmail().equals(form.getEmailConfirm())) {
            result.rejectValue("emailConfirm", "error.emailConfirm", "パスワードが一致しません。");
        }

        if (!form.getPassword().equals(form.getPasswordConfirm())) {
            result.rejectValue("passwordConfirm", "error.passwordConfirm", "パスワードが一致しません。");
        }

        if (result.hasErrors()) {
            return "register";
        }

        return "register_complete";
    }
}
