package project.pizza.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.pizza.domain.member.Member;
import project.pizza.repository.MemberRepository;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String email, String password) {
        return memberRepository.findByEmail(email)
                .filter(m -> m.getPassword().equals(password)).orElse(null);
    }
}
