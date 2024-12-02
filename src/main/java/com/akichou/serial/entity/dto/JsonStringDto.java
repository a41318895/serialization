package com.akichou.serial.entity.dto;

import jakarta.validation.constraints.NotBlank;

public record JsonStringDto(@NotBlank String jsonString) {
}
