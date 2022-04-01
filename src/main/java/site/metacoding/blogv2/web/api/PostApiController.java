package site.metacoding.blogv2.web.api;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.domain.post.Post;
import site.metacoding.blogv2.domain.user.User;
import site.metacoding.blogv2.service.PostService;
import site.metacoding.blogv2.web.api.dto.ResponseDto;
import site.metacoding.blogv2.web.api.dto.post.DetailResponseDto;
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

    @GetMapping("/api/post/{id}")
    public ResponseDto<?> detail(@PathVariable Integer id) {

        boolean auth;

        Post postEntity = postService.글상세보기(id);

        // 인증 확인
        User principal = (User) session.getAttribute("principal");

        System.out.println("세션 : " + principal);
        if (principal != null) {
            // 권한 확인
            System.out.println("작성자아이디 : " + postEntity.getUser().getId());
            if (principal.getId() == postEntity.getUser().getId()) {
                auth = true;
            } else {
                auth = false;
            }
        } else {
            auth = false;
        }
        DetailResponseDto detailResponseDto = new DetailResponseDto(postEntity, auth);

        return new ResponseDto<>(1, "성공", detailResponseDto);

    }

}
