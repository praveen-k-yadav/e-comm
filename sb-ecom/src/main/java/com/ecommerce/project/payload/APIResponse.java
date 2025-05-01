package com.ecommerce.project.payload;


//Response class for exception that we would be using inside GlobaLExceptionHandler pass any sort of message to the user

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse {
    public String message;
    private boolean status;
}
