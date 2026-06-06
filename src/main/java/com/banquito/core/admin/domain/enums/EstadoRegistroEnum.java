package com.banquito.core.admin.domain.enums;

import lombok.Getter;

@Getter
public enum EstadoRegistroEnum {
    ACTIVO("ACTIVO"),
INACTIVO("INACTIVO");

    private final String value;

    EstadoRegistroEnum(String value) {
        this.value = value;
    }
}
