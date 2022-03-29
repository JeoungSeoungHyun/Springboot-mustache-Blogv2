package site.metacoding.blogv2.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.service.PostService;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    @GetMapping({ "/", "/post" })
    public String home() {
        return "post/list";
    }

    // 페이지 요청
    // /s 붙었으니까 인터셉터가 인증 처리를 한다.
    @GetMapping("/s/post/writeForm")
    public String writeForm() {
        return "post/writeForm";
    }
}
