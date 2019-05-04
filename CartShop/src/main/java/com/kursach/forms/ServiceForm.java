package com.kursach.forms;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ServiceForm {
    String price;
    String serviceName;
}
