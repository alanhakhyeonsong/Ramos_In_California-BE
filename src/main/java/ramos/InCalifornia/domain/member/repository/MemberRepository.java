package ramos.InCalifornia.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ramos.InCalifornia.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}
