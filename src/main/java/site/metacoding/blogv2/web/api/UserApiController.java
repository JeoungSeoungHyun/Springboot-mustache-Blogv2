package site.metacoding.blogv2.web.api;

import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.domain.user.User;
import site.metacoding.blogv2.service.UserService;
import site.metacoding.blogv2.web.api.dto.ResponseDto;
import site.metacoding.blogv2.web.api.dto.user.JoinDto;
import site.metacoding.blogv2.web.api.dto.user.LoginDto;
import site.metacoding.blogv2.web.api.dto.user.UpdateDto;

// 웹이 아닌 프로그램에는 뷰가 아닌 JSON데이터를 돌려줘야 하기 때문에 ApiController가 필
@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final HttpSession session;

    // 회원가입 메서드
    @PostMapping("/join")
    public ResponseDto<?> join(@RequestBody JoinDto joinDto) {

        userService.회원가입(joinDto);

        return new ResponseDto<String>(1, "회원가입 성공", null);
    }

    // 로그인 메서드
    @PostMapping("/api/login")
    public ResponseDto<?> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {

        User userEntity = userService.로그인(loginDto);

        System.out.println("스프링 로그인dto : " + loginDto);

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

    // 로그아웃 메서드
    @GetMapping("/logout")
    public ResponseDto<?> logout() {

        session.invalidate();

        return new ResponseDto<>(1, "성공", null);
    }

    // 회원정보 수정 메서드
    // password, email
    @PutMapping("/s/api/user/{id}")
    public ResponseDto<?> updateForm(@PathVariable Integer id, @RequestBody UpdateDto user) {

        // 세션의 아이디와 id를 비교 = >권한 체크

        User principal = (User) session.getAttribute("principal");
        if (principal.getId() == id) {
            throw new RuntimeException("권한이 없습니다.");
        }

        User userEntity = userService.회원정보수정(user, id);
        session.setAttribute("principal", userEntity); // 세션 변경하기

        return new ResponseDto<>(1, "성공", null);
    }

    // 현재 웹브라우저에서는 사용하지 않음. 추후 앱에서 요청시 사용 예정
    @GetMapping("/s/api/user/{id}")
    public ResponseDto<?> userInfo(@PathVariable Integer id, @RequestBody UpdateDto user) {
        userService.회원정보수정(user, id);

        return new ResponseDto<>(1, "성공", null);
    }
}
