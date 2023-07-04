package maryam.service.visit;

import lombok.RequiredArgsConstructor;
import maryam.data.visit.UserVisitRepository;
import maryam.models.user.User;
import maryam.models.uservisit.UserVisits;
import maryam.service.user.UserService;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.Optional;

@Service @RequiredArgsConstructor @Transactional
public class UserVisitService {
    private final UserVisitRepository userVisitRepository;
    public UserVisits createUserVisits(User user){
        return userVisitRepository.save(new UserVisits(user));
    }
    public UserVisits getCurrentUserVisit(User user){
        try {

            if(user!=null){
                Optional<UserVisits> optionalUserVisits = userVisitRepository.findByUser(user);
                return optionalUserVisits.orElseGet(() -> createUserVisits(user));
            }
            return null;

        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
