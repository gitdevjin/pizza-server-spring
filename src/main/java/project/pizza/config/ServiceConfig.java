package project.pizza.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.pizza.repository.MemberRepository;
import project.pizza.service.admin.LoginService;

@Configuration
@RequiredArgsConstructor
public class ServiceConfig {

    private final MemberRepository memberRepository;

    @Bean
    public LoginService loginService() {
        return new LoginService(memberRepository);
    }

}
