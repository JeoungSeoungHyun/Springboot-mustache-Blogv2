package site.metacoding.blogv2.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.domain.user.User;
import site.metacoding.blogv2.domain.user.UserRepository;
import site.metacoding.blogv2.web.api.dto.user.JoinDto;
import site.metacoding.blogv2.web.api.dto.user.LoginDto;
import site.metacoding.blogv2.web.api.dto.user.UpdateDto;

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

    public User 회원정보(Integer id) {

        // 핸들러에서 예외처리를 해주기 때문에 Optional 처리를 하지 않아도 된다.
        // return userRepository.findById(id).get();

        // 바로 get하여 Exception을 발생할 수도 있지만
        // 이 경우에는 내가 원하는 Exception메세지를 할 수 없기 때문에
        Optional<User> userOp = userRepository.findById(id);

        if (userOp.isPresent()) {
            return userOp.get();
        } else {
            throw new RuntimeException("아이디를 찾을 수 없습니다");
        }

    }

    @Transactional
    public void 회원정보수정(UpdateDto user, Integer id) {

        // UPDATE user SET password = ?, email = ? WHERE id = ?

        Optional<User> userOp = userRepository.findById(id); // 영속화 하는 것. DB의 row를 영속성 컨텍스트에 옮김

        if (userOp.isPresent()) {
            User userEntity = userOp.get();

            userEntity.setPassword(user.getPassword());
            userEntity.setEmail(user.getEmail());
            userEntity.setAddr(user.getAddr());

        } else {
            throw new RuntimeException("아이디를 찾을 수 없습니다");
        }
    } // 트랜잭션이 걸려있어야 영속화 된 것을 확인하여 더티체킹하고 UPDATE 처리가 된다.
}
