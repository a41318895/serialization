package com.akichou.serial.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserInfoDto(
        @NotBlank String name,
        @NotNull Integer age) {
}
