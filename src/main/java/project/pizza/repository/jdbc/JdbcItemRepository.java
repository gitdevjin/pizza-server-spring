package project.pizza.repository.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import project.pizza.domain.item.Item;
import project.pizza.repository.ItemRepository;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
public class JdbcItemRepository implements ItemRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcItemRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("item")
                .usingGeneratedKeyColumns("id");
    }


    @Override
    public Item save(Item item) {
        return null;
    }

    @Override
    public List<Item> findAll(String category) {
        return null;
    }
}
