package site.metacoding.blogv2.web.api;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.service.UserService;

// 웹이 아닌 프로그램에는 뷰가 아닌 JSON데이터를 돌려줘야 하기 때문에 ApiController가 필요
@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
}
