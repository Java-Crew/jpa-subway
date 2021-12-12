package com.javacrew.subway.repository;

import com.javacrew.subway.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
