package project.pizza.web;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.pizza.domain.member.Member;
import project.pizza.repository.MemberRepository;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
//        Member memberA = new Member("test@gmail.com", "1234",
//                "Bob", "Smith",
//                "1760 finch Ave, North York, ON", "admin");
//
//        Member result = memberRepository.save(memberA);

    }
}
