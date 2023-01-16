package maryam.service.visit;

import lombok.RequiredArgsConstructor;
import maryam.data.visit.UserVisitRepository;
import maryam.models.user.User;
import maryam.models.uservisit.UserVisits;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service @RequiredArgsConstructor @Transactional
public class UserVisitService {
    private final UserVisitRepository userVisitRepository;
    public UserVisits createUserVisits(User user){
        return userVisitRepository.save(new UserVisits(user));
    }
}
