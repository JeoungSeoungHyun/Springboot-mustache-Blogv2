package site.metacoding.blogv2.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// 인터페이스로 생성
public interface UserRepository extends JpaRepository<User, Integer> {

    // 로그인 처리 쿼리를 JPA에서 제공해주지 않는다.
    @Query(value = "SELECT * FROM user WHERE username=:username AND password = :password", nativeQuery = true)
    User mLogin(@Param("username") String username, @Param("password") String password);
}
