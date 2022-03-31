package site.metacoding.blogv2.web.api;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.domain.post.Post;
import site.metacoding.blogv2.domain.user.User;
import site.metacoding.blogv2.service.PostService;
import site.metacoding.blogv2.web.api.dto.ResponseDto;
import site.metacoding.blogv2.web.api.dto.post.WriteDto;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;
    private final HttpSession session;

    @PostMapping("/s/post")
    public ResponseDto<?> write(@RequestBody WriteDto writeDto) {

        System.out.println("디티오 : " + writeDto);

        User principal = (User) session.getAttribute("principal");

        Post post = writeDto.toEntity(principal);
        // 원래는 그냥 dto를 넘겼는데, 지금 dto를 넘기면 session값 두개 넘겨야 해서 하나로 합쳐서 넘김

        postService.글쓰기(post);

        return new ResponseDto<>(1, "성공", null);
    }

    @GetMapping("/api/post")
    public ResponseDto<?> list(@RequestParam(defaultValue = "0") Integer page) {

        Page<Post> list = postService.글목록(page);

        // System.out.println("리스트 : " + list);

        return new ResponseDto<>(1, "성공", list);
    }
}
