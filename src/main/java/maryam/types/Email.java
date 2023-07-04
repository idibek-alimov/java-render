package maryam.types;

import lombok.Data;

@Data
public class Email {
    String toEmail;
    String subject;
    String body;
}
