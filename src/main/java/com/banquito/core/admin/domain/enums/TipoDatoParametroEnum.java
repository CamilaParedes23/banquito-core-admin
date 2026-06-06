package com.banquito.core.admin.domain.enums;

import lombok.Getter;

@Getter
public enum TipoDatoParametroEnum {
    STRING("STRING"),
INTEGER("INTEGER"),
DECIMAL("DECIMAL"),
BOOLEAN("BOOLEAN"),
TIME("TIME"),
JSON("JSON");

    private final String value;

    TipoDatoParametroEnum(String value) {
        this.value = value;
    }
}
