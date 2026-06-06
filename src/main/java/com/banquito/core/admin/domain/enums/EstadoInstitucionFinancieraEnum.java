package com.banquito.core.admin.domain.enums;

import lombok.Getter;

@Getter
public enum EstadoInstitucionFinancieraEnum {
    ACTIVA("ACTIVA"),
INACTIVA("INACTIVA");

    private final String value;

    EstadoInstitucionFinancieraEnum(String value) {
        this.value = value;
    }
}
