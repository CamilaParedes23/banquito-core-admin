package com.banquito.core.admin.api.dto.api;

import jakarta.validation.constraints.NotBlank;

public record ChangeStatusRequest(@NotBlank String status) {}
