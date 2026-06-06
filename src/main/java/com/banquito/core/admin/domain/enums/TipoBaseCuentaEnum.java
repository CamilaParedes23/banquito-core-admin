package com.banquito.core.admin.domain.enums;

import lombok.Getter;

@Getter
public enum TipoBaseCuentaEnum {
    AHORROS("AHORROS"),
CORRIENTE("CORRIENTE"),
NOMINA("NOMINA");

    private final String value;

    TipoBaseCuentaEnum(String value) {
        this.value = value;
    }
}
