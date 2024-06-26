package project.pizza.repository;

import project.pizza.domain.member.Member;

import java.util.Optional;

public interface MemberRepository {

    public Member save(Member member);
    public Optional<Member> findByEmail(String email);
}
