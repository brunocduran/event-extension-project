package br.com.eventextensionproject.MainExtensionProject.entity.enumerator;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusEvent {
    ATIVO(0, "Ativo"),
    CANCELADO(1, "Cancelado"),
    INICIADO(2, "Iniciado"),
    FINALIZADO(3, "Finalizado"),
    ENCERRADO(4, "Encerrado");

    private Integer code;
    private String description;
    
    private StatusEvent(Integer code, String description) {
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

    public static StatusEvent parse(Integer codigo) {
        if(codigo != null) {
            for(StatusEvent se : StatusEvent.values()) {
                if(se.code.equals(codigo)) {
                    return se;
                }
            }
        }
        return null;
    }
}
