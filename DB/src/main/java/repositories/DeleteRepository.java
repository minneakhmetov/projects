/*
 * Developed by Razil Minneakhmetov on 12/27/18 2:14 AM.
 * Last modified 12/27/18 2:14 AM.
 * Copyright © 2018. All rights reserved.
 */

package repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DeleteRepository {
    private JdbcTemplate jdbcTemplate;

    public DeleteRepository(DriverManagerDataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    private static final String QUERY = "delete from groups_table where user_vk_id = ?;\n" +
            "delete from users_groups_table where user_vk_id = ?;\n" +
            "delete from friends_table where user_vk_id = ?;\n" +
            "delete from users_table where vk_id = ?;";

    public void deleteAll(long vkId){
        jdbcTemplate.update(QUERY, vkId, vkId, vkId, vkId);
    }
}