package site.metacoding.blogv2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.domain.user.User;
import site.metacoding.blogv2.domain.user.UserRepository;
import site.metacoding.blogv2.web.api.dto.user.JoinDto;
import site.metacoding.blogv2.web.api.dto.user.LoginDto;

// 스프링프레임워크
// 웹브라우저(컨트롤러 호출) -> 컨트롤러(view/데이터 돌려줌) -> 서비스(기능관리)
// -> 레파지토리(DB연결 및 쿼리) -> 영속성컨텍스트(쿼리수행과 테이블관계 처리) -> DB(데이터 저장)

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void 회원가입(JoinDto joinDto) {

        // JPA 레파지토리가 User를 관리하기 때문에 dto를 다시 user로 변경해줘야한다.
        // return 해주게 되면 insert 후 동기화가 되기 때문에 없는값이 입력되서 return된다.
        userRepository.save(joinDto.toEntity());
    }

    // WRITE시에만 트랜잭션
    public User 로그인(LoginDto loginDto) {

        // SELECT만 하면되기 때문에 따로 Entity로 변환하는 메서드가 필요하지 않다.
        return userRepository.mLogin(loginDto.getUsername(), loginDto.getPassword());
    }
}
