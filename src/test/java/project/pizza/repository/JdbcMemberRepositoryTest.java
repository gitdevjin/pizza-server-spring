package project.pizza.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import project.pizza.domain.member.Member;

import java.util.NoSuchElementException;
import java.util.Optional;

@Transactional
@SpringBootTest
public class JdbcMemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {

    }

    @Test
    public void save() {
        Member memberA = new Member("test2@google.com", "1234",
                "Bob", "Smith",
                "1760 finch Ave, North York, ON", "admin");

        Member result = memberRepository.save(memberA);

        // Successfully Saved
        Assertions.assertThat(result).isEqualTo(memberA);

        // Failed to save due to duplicate email
        Assertions.assertThatThrownBy(() -> memberRepository.save(memberA))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void findByEmail() {
        Member memberB = new Member("findTest@google.com","test-password",
                "Bob", "Smith",
                "595 St.Clair West Ave, Toronto, ON","customer");

        // Save a Member
        memberRepository.save(memberB);

        // Successfully Found
        Optional<Member> retrievedMemberOptional = memberRepository.findByEmail(memberB.getEmail());
        Member retrievedMember = retrievedMemberOptional.orElse(null);
        Assertions.assertThat(retrievedMember).isEqualTo(memberB);

        // Not Found
        Optional<Member> NotFoundMemberOptional = memberRepository.findByEmail("wrongEmail@google.com");
        Assertions.assertThatThrownBy(NotFoundMemberOptional::orElseThrow)
                .isInstanceOf(NoSuchElementException.class);
    }

}
