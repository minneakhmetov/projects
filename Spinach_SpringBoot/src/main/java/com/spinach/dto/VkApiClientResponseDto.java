package com.spinach.dto;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class VkApiClientResponseDto {
    VkApiClient vk;
    UserAuthResponse authResponse;
    Long id;
}
