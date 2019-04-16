package br.com.londrisoft.uniqueweb.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Brazil/East")
    private Date timestamp = new Date();
    private Boolean success;
    private String message;
    private Object data;

    public ApiResponse(Object data) {
        this.success = true;
        this.message = null;
        this.data = data;
    }

    public ApiResponse(String message, Object data) {
        this.success = true;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
