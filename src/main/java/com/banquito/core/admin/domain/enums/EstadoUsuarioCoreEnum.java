package com.banquito.core.admin.domain.enums;

import lombok.Getter;

@Getter
public enum EstadoUsuarioCoreEnum {
    ACTIVO("ACTIVO"),
INACTIVO("INACTIVO"),
SUSPENDIDO("SUSPENDIDO");

    private final String value;

    EstadoUsuarioCoreEnum(String value) {
        this.value = value;
    }
}
