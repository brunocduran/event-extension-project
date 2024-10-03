package br.com.eventextensionproject.MainExtensionProject.entity.enumarator;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Situation {
    INATIVO(0, "Inativo"),
    ATIVO(1, "Ativo");

    private Integer codigo;
    private String descricao;

    @JsonValue
    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    private Situation(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
}