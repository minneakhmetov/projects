/*
 * Developed by Razil Minneakhmetov on 12/21/18 5:52 PM.
 * Last modified 12/21/18 5:52 PM.
 * Copyright © 2018. All rights reserved.
 */

package repositories;

import dto.ProductDto;
import forms.LoginForm;
import lombok.SneakyThrows;
import models.Product;
import models.ProductActivity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public interface ProductRepository extends Repository {
    void create(Product product);
    void delete();
    ProductActivity readOne(Long id);
    List<ProductActivity> readAll();
    void batchUpdate(final List<ProductDto> users);
    List<ProductActivity> find(String string);
}