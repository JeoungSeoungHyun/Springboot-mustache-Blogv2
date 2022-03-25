package site.metacoding.blogv2.web.api;

import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.domain.user.User;
import site.metacoding.blogv2.service.UserService;
import site.metacoding.blogv2.web.api.dto.ResponseDto;
import site.metacoding.blogv2.web.api.dto.user.JoinDto;
import site.metacoding.blogv2.web.api.dto.user.LoginDto;

// 웹이 아닌 프로그램에는 뷰가 아닌 JSON데이터를 돌려줘야 하기 때문에 ApiController가 필요
@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final HttpSession session;

    @PostMapping("/join")
    public ResponseDto<?> join(@RequestBody JoinDto joinDto) {

        userService.회원가입(joinDto);

        return new ResponseDto<String>(1, "회원가입 성공", null);
    }

    @PostMapping("/api/login")
    public ResponseDto<?> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {

        User userEntity = userService.로그인(loginDto);

        if (userEntity != null) {
            // 쿠키 로직
            session.setAttribute("principal", userEntity);
            if (loginDto.getRemember().equals("on")) {
                response.addHeader(
                        "Set-Cookie",
                        "remember=" + loginDto.getUsername() + ";path=/");
                // "remember=" + loginDto.getUsername() + ";path=/" +";HttpOnly=true");

                // Cookie cookie = new Cookie("remember", loginDto.getUsername());
                // cookie.setPath("/");
                // response.addCookie(cookie);
            }

            return new ResponseDto<String>(1, "로그인 성공", null);
        } else {
            return new ResponseDto<String>(-1, "로그인 실패", null);
        }

    }

    @GetMapping("/logout")
    public ResponseDto<?> logout() {

        session.invalidate();

        return new ResponseDto<>(1, "성공", null);
    }
}
