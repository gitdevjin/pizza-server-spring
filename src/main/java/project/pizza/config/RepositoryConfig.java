package project.pizza.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.pizza.repository.MemberRepository;
import project.pizza.repository.jdbc.JdbcMemberRepository;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class RepositoryConfig {

    private final DataSource dataSource;

    @Bean
    public MemberRepository memberRepository() {
        return new JdbcMemberRepository(dataSource);
    }


}
