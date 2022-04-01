package site.metacoding.blogv2.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.domain.post.Post;
import site.metacoding.blogv2.domain.post.PostRepository;

@RequiredArgsConstructor
@Service // 컴퍼넌트 스캔시 IoC에 등록 // 트랜잭션 관리를 위해 생성. //기능의 모임 //비즈닉스 로직(핵심 로직)
public class PostService {

    private final PostRepository postRepository;

    // DB에 insert한 Post를 다시 리턴 받음.
    @Transactional
    public void 글쓰기(Post post) {
        postRepository.save(post);
    }

    public Page<Post> 글목록(Integer page) {

        PageRequest pq = PageRequest.of(page, 3, Sort.by(Direction.DESC, "id"));

        return postRepository.findAll(pq);
    }

    public Post 글상세보기(Integer id) {
        Optional<Post> postOp = postRepository.findById(id);
        if (postOp.isPresent()) {
            return postOp.get();
        } else {
            return null;
        }
    }

    public void 글삭제(Integer id) {
        postRepository.deleteById(id);
    }
}
