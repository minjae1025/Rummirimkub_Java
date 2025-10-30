package com.rummirimkub.user;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String sign_up(UserCreateForm userCreateForm) {
        return "signup";
    }

    @PostMapping("/signup")
    public String sign_up(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup";
        }

        try {
            userService.create(userCreateForm.getUsername(),
                    userCreateForm.getEmail(), userCreateForm.getPassword1());
        } catch (IllegalStateException e) { // 💡 IllegalStateException만 잡도록 수정 가능
            e.printStackTrace();
            // 💡 전역 오류로 등록: 필드명 없이 에러 코드를 사용하고, 예외 메시지를 기본 메시지로 사용
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup";
        } catch (Exception e) { // 그 외 예상치 못한 시스템 오류 처리
            e.printStackTrace();
            bindingResult.reject("systemError", "회원가입 중 알 수 없는 오류가 발생했습니다.");
            return "signup";
        }

        return "redirect:/user/signup";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/username")
    @ResponseBody
    public String currentUserName(Principal principal)
    {
        return principal.getName();
    }

    @GetMapping("mypage")
    public String mypage() {
        return "mypage";
    }
}