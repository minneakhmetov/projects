package com.kursach.repositories;

import com.kursach.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Component
public class OrdersRepositoryImpl  implements OrdersRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public OrdersRepositoryImpl(DriverManagerDataSource driverManagerDataSource) {
        this.jdbcTemplate = new JdbcTemplate(driverManagerDataSource);
    }

    private static final String SELECT_BY_SELLER = "SELECT orders_table.id as id, seller, buyer, product_id, name, surname, price, photo_url, activity FROM orders_table join product on orders_table.product_id = product.id " +
            "join user_table on orders_table.seller = user_table.vk_id where seller = ?";
    private static final String SELECT_BY_BUYER = "SELECT orders_table.id as id, seller, buyer, product_id, name, surname, price, photo_url, activity FROM orders_table join product on orders_table.product_id = product.id join user_table on orders_table.buyer = user_table.vk_id where buyer = ?";
    private static final String DELETE_BY_SELLER = "DELETE FROM orders_table where seller = ? AND id = ?";
    private static final String DELETE_BY_BUYER = "DELETE FROM orders_table where buyer = ? AND id = ?";
    private static final String INSERT = "INSERT INTO orders_table (seller, buyer, product_id) values (?,?,?)";

    RowMapper<Order> rowMapper = new RowMapper<Order>() {
        @Override
        public Order mapRow(ResultSet resultSet, int i) throws SQLException {
            return Order.builder()
                    .activity(resultSet.getString("activity"))
                    .avatar(resultSet.getString("photo_url"))
                    .buyer(resultSet.getInt("buyer"))
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name") + " " + resultSet.getString("surname"))
                    .product(resultSet.getLong("product_id"))
                    .seller(resultSet.getInt("seller"))
                    .price(resultSet.getString("price"))
                    .build();
        }
    };

    public List<Order> getOrdersBySeller(Integer id){
        return jdbcTemplate.query(SELECT_BY_SELLER, rowMapper, id);
    }

    public List<Order> getOrdersByBuyer(Integer id){
        return jdbcTemplate.query(SELECT_BY_BUYER, rowMapper, id);
    }

    public void deleteBySeller(Integer userId, Long order){
        jdbcTemplate.update(DELETE_BY_SELLER, userId, order);
    }

    public void deleteByBuyer(Integer userId, Long order){
        jdbcTemplate.update(DELETE_BY_BUYER, userId, order);
    }

    public void insert(Order order){
        jdbcTemplate.update(INSERT, order.getSeller(), order.getBuyer(), order.getProduct());
    }

    public void insert(List<Order> orders){
        jdbcTemplate.batchUpdate(INSERT,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, orders.get(i).getSeller());
                        ps.setInt(2, orders.get(i).getBuyer());
                        ps.setLong(3, orders.get(i).getProduct());
                    }

                    public int getBatchSize() {
                        return orders.size();
                    }
                } );
    }


}
