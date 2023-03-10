package maryam.data.user;

import maryam.models.user.User;
import maryam.models.user.VerificationCode;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VerificationCodeRepository extends CrudRepository<VerificationCode,Long> {
    public Optional<VerificationCode> findByUser(User user);
}
