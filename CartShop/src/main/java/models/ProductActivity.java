package models;

import lombok.*;

@Getter
@Setter
@Builder
@Data
@EqualsAndHashCode
public class ProductActivity {
    Long id;
    String name;
    String price;
    String avatar;
    String activity;
}
