package project.pizza.repository.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import project.pizza.domain.member.Member;
import project.pizza.repository.MemberRepository;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class JdbcMemberRepository implements MemberRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcMemberRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("members")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Member save(Member member) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(member);

        try {
            Number key = jdbcInsert.executeAndReturnKey(param);
            member.setId(key.longValue());
            return member;
        } catch (Exception e) {
            log.info("[JdbcMemberRepository][save][ERROR] - Failed to Save");
            throw new RuntimeException(e.getMessage());
        }
    }

    public Optional<Member> findByEmail(String email) {
        String sql = "select * from members where email = :email";

        try {
            Map<String, Object> param = Map.of("email", email);
            Member member = template.queryForObject(sql, param, memberRowMapper());
            return Optional.ofNullable(member);
        } catch (RuntimeException e) {
            log.info("[JdbcMemberRepository][findByEmail][ERROR] - Failed to find a Member of the Email");
            return Optional.empty();
        }
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setEmail(rs.getString("email"));
            member.setPassword(rs.getString("password"));
            member.setFirstName(rs.getString("first_name"));
            member.setLastName(rs.getString("last_name"));
            member.setAddress(rs.getString("address"));
            member.setRole(rs.getString("role"));
            return member;
        };
    }


}
