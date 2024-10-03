package br.com.eventextensionproject.MainExtensionProject.entity.enumerator;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AccountStatus {
    ABERTO(0, "Aberto"),
    BAIXADO(1, "Baixado");

    private Integer codigo;
    private String descricao;

    @JsonValue
    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    private AccountStatus(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
}
