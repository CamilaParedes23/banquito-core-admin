package com.banquito.core.admin.domain.enums;

import lombok.Getter;

@Getter
public enum EstadoVentanaOperativaEnum {
    ACTIVA("ACTIVA"),
INACTIVA("INACTIVA");

    private final String value;

    EstadoVentanaOperativaEnum(String value) {
        this.value = value;
    }
}
