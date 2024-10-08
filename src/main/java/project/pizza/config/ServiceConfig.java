package project.pizza.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.pizza.repository.ImageRepository;
import project.pizza.repository.ItemRepository;
import project.pizza.repository.MemberRepository;
import project.pizza.service.admin.ItemService;
import project.pizza.service.admin.LoginService;

@Configuration
@RequiredArgsConstructor
public class ServiceConfig {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ImageRepository imageRepository;

    @Bean
    public LoginService loginService() {
        return new LoginService(memberRepository);
    }

    @Bean
    public ItemService itemService() {
        return new ItemService(itemRepository, imageRepository);
    }

}
