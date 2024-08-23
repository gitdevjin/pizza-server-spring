package project.pizza.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.pizza.repository.ItemRepository;
import project.pizza.repository.MemberRepository;
import project.pizza.repository.jdbc.JdbcItemRepository;
import project.pizza.repository.jdbc.JdbcMemberRepository;
import project.pizza.repository.jdbc.MemoryImageRepository;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class RepositoryConfig {

    private final DataSource dataSource;

    @Bean
    public MemberRepository memberRepository() {
        return new JdbcMemberRepository(dataSource);
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JdbcItemRepository(dataSource);
    }

    @Bean
    public MemoryImageRepository memoryImageRepository() {
        return new MemoryImageRepository();
    }


}
