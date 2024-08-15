package project.pizza.repository.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.StringUtils;
import project.pizza.domain.item.ImageFile;
import project.pizza.domain.item.Item;
import project.pizza.domain.item.ItemPrice;
import project.pizza.repository.ItemRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Slf4j
public class JdbcItemRepository implements ItemRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcItemRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("items")
                .usingGeneratedKeyColumns("id")
                .usingColumns("item_name", "category", "desc", "original_img", "stored_img");
    }

    @Override
    public Item save(Item item) {

        /* Add Items Table */
        String uploadImageName = "";
        String storeImageName = "";

        if (item.getImgFile() != null) {
            uploadImageName = item.getImgFile().getUploadFileName();
            storeImageName = item.getImgFile().getStoreFileName();
        }

        SqlParameterSource paramItem = new MapSqlParameterSource()
                .addValue("item_name", item.getItemName())
                .addValue("category", item.getCategory())
                .addValue("desc", item.getDescription())
                .addValue("original_img", uploadImageName)
                .addValue("stored_img", storeImageName);

        // To do
        // Applying Transaction

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
        String sql = "select * from items";

        // to change to param
        if (StringUtils.hasText(category)) {
            sql += "where category = " + category;
        }

        // Finding prices for each item
        List<Item> items = template.query(sql, itemRowMapper());

        for (Item item : items) {
            List<ItemPrice> prices = findPrices(item.getId());
            item.setPrices(prices);
        }

        return items;
    }

    @Override
    public String delete(Long id) {
        String sql = "delete from items where id=:itemId";
        String sqlPrice = "delete from item_prices where item_id=:itemId";
        Map<String, Long> itemId = Map.of("itemId", id);
        String message = "Item Deleted";

        try {
            template.update(sql, itemId);
            template.update(sqlPrice, itemId);
        } catch (Exception e) {
            log.info("[JdbcItemRepository][save][ERROR] - Failed to Delete Item or Item_Prices");
            message = "Item Delete Failed";
            throw new RuntimeException(e.getMessage());
        }

        return message;
    }

    private List<ItemPrice> findPrices(Long itemId) {
        String sql = "select * from item_prices where item_id=:id";

        Map<String, Object> param = Map.of("id", itemId);

        List<ItemPrice> prices = template.query(sql, param, priceRowMapper());
        return prices;
    }

    private RowMapper<Item> itemRowMapper() {
        return (rs, rowNum) -> {
            Item item = new Item();
            item.setId(rs.getLong("id"));
            item.setItemName(rs.getString("item_name"));
            item.setCategory(rs.getString("category"));
            item.setDescription(rs.getString("desc"));
            ImageFile img = new ImageFile(rs.getString("original_img"), rs.getString("stored_img"));
            item.setImgFile(img);
            return item;
        };
    }

    private RowMapper<ItemPrice> priceRowMapper() {
        return (rs, rowNum) -> {
            ItemPrice price = new ItemPrice();
            price.setItemId(rs.getLong("item_id"));
            price.setSize(rs.getString("size"));
            price.setPrice(rs.getDouble("price"));
            return price;
        };
    }
}
