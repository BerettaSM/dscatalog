package com.devsuperior.dscatalog.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class EmailModel {

    private String to;
    private String subject;
    private String body;

    public static EmailModel of(String to, String subject, String body) {
        return new EmailModel(to, subject, body);
    }
    
}
