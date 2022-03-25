package site.metacoding.blogv2.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// 인터페이스로 생성
public interface UserRepository extends JpaRepository<User, Integer> {

    // 로그인 처리 쿼리를 JPA에서 제공해주지 않는다.
    @Query(value = "SELECT * FROM user WHERE username=:username AND password = :password", nativeQuery = true)
    User mLogin(@Param("username") String username, @Param("password") String password);

    // @Modifying // DB의 변경을 필요로 하는 경우 붙여줘야한다.
    // @Query(value = " UPDATE user SET password = :password, email = :email, addr =
    // :addr WHERE id = :id", nativeQuery = true)
    // void mUpdate(@Param("password") String password, @Param("email") String
    // email, @Param("addr") String addr,
    // @Param("id") Integer id);
}
