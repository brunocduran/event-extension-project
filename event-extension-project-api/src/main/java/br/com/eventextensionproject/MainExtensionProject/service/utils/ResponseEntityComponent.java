package br.com.eventextensionproject.MainExtensionProject.service.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseEntityComponent {
    
    private Integer status;
    private List<String> messages;
    private Object result;

    public ResponseEntityComponent() {
        this.status = HttpStatus.OK.value();
            this.messages = new ArrayList<>();
            this.result = null;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public List<String> getMessages() {
        return messages;
    }
    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
    public Object getResult() {
        return result;
    }
    public void setResult(Object result) {
        this.result = result;
    }

    public ResponseEntity<Object> ok(Object object, String message) {        
        HttpStatus httpStatus = HttpStatus.OK;

        List<String> messages = new ArrayList<>();
        messages.add(message);

        ResponseEntityComponent response = new ResponseEntityComponent();
        response.setStatus(httpStatus.value());
        response.setMessages(messages);
        response.setResult(object);

        return ResponseEntity.status(httpStatus).body(response);
    }

    public ResponseEntity<Object> unauthorized(Object object, String message) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        List<String> messages = new ArrayList<>();
        messages.add(message);

        ResponseEntityComponent response = new ResponseEntityComponent();
        response.setStatus(httpStatus.value());
        response.setMessages(messages);
        response.setResult(object);

        return ResponseEntity.status(httpStatus).body(response);
    }

    public ResponseEntity<Object> notFound(String message) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        List<String> messages = new ArrayList<>();
        messages.add(message);

        ResponseEntityComponent response = new ResponseEntityComponent();
        response.setStatus(httpStatus.value());
        response.setMessages(messages);

        return ResponseEntity.status(httpStatus).body(response);
    }

    public ResponseEntity<Object> notAcceptable(List<String> messages) {
        HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;

        ResponseEntityComponent response = new ResponseEntityComponent();
        response.setStatus(httpStatus.value());
        response.setMessages(messages);

        return ResponseEntity.status(httpStatus).body(response);
    }

    public ResponseEntity<Object> internalServerError(Exception e) {
        e.printStackTrace();

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        List<String> messages = new ArrayList<>();
        messages.add(String.format("Entre em contato com o suporte técnico - Erro %s", e.getMessage()));

        ResponseEntityComponent response = new ResponseEntityComponent();
        response.setStatus(httpStatus.value());
        response.setMessages(messages);

        return ResponseEntity.status(httpStatus).body(response);
    }

}
