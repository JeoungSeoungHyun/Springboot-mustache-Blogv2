package site.metacoding.blogv2.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

// 인터페이스로 생성
public interface UserRepository extends JpaRepository<User, Integer> {

}
