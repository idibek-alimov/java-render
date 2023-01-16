package maryam.service.visit;

import lombok.RequiredArgsConstructor;
import maryam.data.product.ProductRepository;
import maryam.data.user.UserRepository;
import maryam.data.visit.UserVisitRepository;
import maryam.data.visit.VisitRepository;
import maryam.models.product.Product;
import maryam.models.user.User;
import maryam.models.uservisit.UserVisits;
import maryam.models.uservisit.Visit;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor @Transactional
public class VisitService {
    private final UserRepository userRepository;
    private final UserVisitRepository userVisitRepository;
    private final VisitRepository visitRepository;
    private final UserVisitService userVisitService;
    private final ProductRepository productRepository;
    public void addVisit(Long id){
        User user = userRepository.findByUsername((String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal());
        Optional<UserVisits> userVisitsOptional = userVisitRepository.findByUser(user);
        UserVisits userVisits;
        if(!userVisitsOptional.isPresent()){
           userVisits = userVisitService.createUserVisits(user);
        }
        else{
            userVisits  = userVisitsOptional.get();
        }
        Product product = productRepository.findById(id).get();
        Optional<Visit> visitOptional = visitRepository.findByProductAndUser(product,user);
        if(visitOptional.isPresent()){
               visitOptional.get().setCreatedAt(new Date());
        }
        else {
            visitRepository.save(new Visit(user, product));
            userVisits.setVisitcount(userVisits.getVisitcount()+1);
        }
        if(userVisits.getVisitcount()>200){
            visitRepository.delete(visitRepository.findAllByUserOrderByCreatedAt(user).get(0));
            userVisits.setVisitcount(userVisits.getVisitcount()-1);
        }
    }
    public List<Visit> getAllVisits(){
        User user = userRepository.findByUsername((String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal());
        System.out.println(user);
        return visitRepository.findAllByUserOrderByCreatedAt(user);
    }

}
