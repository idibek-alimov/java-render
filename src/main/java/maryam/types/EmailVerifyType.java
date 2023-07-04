package maryam.types;

import lombok.Data;

@Data
public class EmailVerifyType {
    String email;
    Integer code;
}
