package site.metacoding.blogv2.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.domain.user.UserRepository;

// 스프링프레임워크
// 웹브라우저(컨트롤러 호출) -> 컨트롤러(view/데이터 돌려줌) -> 서비스(기능관리)
// -> 레파지토리(DB연결 및 쿼리) -> 영속성컨텍스트(쿼리수행과 테이블관계 처리) -> DB(데이터 저장)

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
}
