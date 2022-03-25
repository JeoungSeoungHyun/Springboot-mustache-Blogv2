package site.metacoding.blogv2.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.domain.user.User;
import site.metacoding.blogv2.service.UserService;

// 부가 로직(유효성 검사 같은)
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    // 웹브라우저 -> 회원가입 페이지 주세요!!
    // 앱 -> 회원가입 페이지 주세요!! 말이 안됨!!
    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm(HttpServletRequest request, Model model) {

        return "user/loginForm";
    }

    // @GetMapping("/logout")
    // public String logout() {
    // session.invalidate();

    // return "redirect:/";
    // }

    // 앱은 페이지 요청 불필요
    // 웹은 페이지 요청 필요
    @GetMapping("/s/user/{id}")
    public String userInfo(@PathVariable Integer id, Model model) {

        User userEntity = userService.회원정보(id);

        model.addAttribute("user", userEntity);

        return "user/updateForm";
    }

}
