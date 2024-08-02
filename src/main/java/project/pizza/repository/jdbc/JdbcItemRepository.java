package project.pizza.repository.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import project.pizza.domain.item.Item;
import project.pizza.domain.item.ItemPrice;
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
                .withTableName("items")
                .usingGeneratedKeyColumns("id")
                .usingColumns("item_name", "category", "desc", "img_src");
    }


    @Override
    public Item save(Item item) {

        /* Add Items Table */
        SqlParameterSource paramItem = new MapSqlParameterSource()
                .addValue("item_name", item.getItemName())
                .addValue("category", item.getCategory())
                .addValue("desc", item.getDescription())
                .addValue("img_src", item.getImgFile().getStoreFileName());

        try {
            Number key = jdbcInsert.executeAndReturnKey(paramItem);
            item.setId(key.longValue());
        } catch (Exception e) {
            log.info("[JdbcItemRepository][save][ERROR] - Failed to Save Item");
            throw new RuntimeException(e.getMessage());
        }
        /* ******************************* */


        /* Add Item_Prices Table */
        List<ItemPrice> prices = item.getPrices();

        for (ItemPrice price : prices) {
            String sql = "insert into item_prices (item_id, size, price) values (:itemId, :size, :price)";

            KeyHolder keyHolder = new GeneratedKeyHolder();

            SqlParameterSource paramPrice = new MapSqlParameterSource()
                    .addValue("itemId", item.getId())
                    .addValue("size", price.getSize())
                    .addValue("price", price.getPrice());

            try {
                template.update(sql, paramPrice, keyHolder);
            } catch (Exception e) {
                log.info("[JdbcItemRepository][save][ERROR] - Failed to Save Item_Prices");
                throw new RuntimeException(e.getMessage());
            }
        }

        /* ******************************* */

        return item;
    }

    @Override
    public List<Item> findAll(String category) {
        return null;
    }
}
