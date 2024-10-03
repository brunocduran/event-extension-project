package br.com.eventextensionproject.MainExtensionProject.entity.enumerator;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TypePerson {
    JURIDICO(0, "PERSON_JURIDICO"),
    FISICO(1, "PERSON_FISICO");


    private Integer code;
    private String description;

    TypePerson(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonValue
    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}

