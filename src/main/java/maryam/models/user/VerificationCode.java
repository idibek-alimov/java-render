package maryam.models.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VerificationCode {
    @Id
    @GeneratedValue(generator = "verification_code_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "verification_code_id_generator", sequenceName = "Verification_code_id_generator",allocationSize=1)
    private Long id;

    private Integer code;

    @OneToOne
    private User user;
    public VerificationCode(Integer code,User user){
        this.code = code;
        this.user = user;
    }
}
