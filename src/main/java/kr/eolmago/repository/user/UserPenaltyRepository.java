package kr.eolmago.repository.user;

// CODE REVIEW: jk-Nam 작업

import kr.eolmago.domain.entity.user.UserPenalty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPenaltyRepository extends JpaRepository<UserPenalty, Long>, UserPenaltyRepositoryCustom {

}
