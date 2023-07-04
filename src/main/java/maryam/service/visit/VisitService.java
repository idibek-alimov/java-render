package maryam.service.visit;

import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
=======
import lombok.extern.slf4j.Slf4j;
import maryam.data.product.ArticleRepository;
>>>>>>> testings
import maryam.data.product.ProductRepository;
import maryam.data.visit.UserVisitRepository;
import maryam.data.visit.VisitRepository;
<<<<<<< HEAD
import maryam.models.product.Product;
=======
import maryam.models.product.Article;
>>>>>>> testings
import maryam.models.user.User;
import maryam.models.uservisit.UserVisits;
import maryam.models.uservisit.Visit;
import maryam.service.user.UserService;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor @Slf4j
public class VisitService {
//    private final UserRepository userRepository;
    private final UserService userService;
    private final UserVisitRepository userVisitRepository;
    private final VisitRepository visitRepository;
    private final UserVisitService userVisitService;
    private final ProductRepository productRepository;
<<<<<<< HEAD
    public void addVisit(Long id){
        User user = userRepository.findByUsername((String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal());
        Optional<UserVisits> userVisitsOptional = userVisitRepository.findByUser(user);
        UserVisits userVisits;
        if(!userVisitsOptional.isPresent()){
           userVisits = userVisitService.createUserVisits(user);
=======
    private final ArticleRepository articleRepository;
    public Visit getVisit(Article article) {
        User user = userService.getCurrentUser();
        if (user != null) {
           return visitRepository.findByArticleAndUser(article,user).orElse(null);
>>>>>>> testings
        }
        return null;
    }
    public Visit createVisit(Article article,User user){
            return visitRepository.save(new Visit(user, article));
    }
    public void addVisit(Article article){
        try {
            User user = userService.getCurrentUser();
            if (user != null) {
                Optional<Visit> optionalVisit = visitRepository.findBYArticleAndUser(article.getId(), user.getId());
                Visit visit;
                if (optionalVisit.isPresent()) {
                    visit = optionalVisit.get();
                    visit.setCreatedAt(new Date());
                } else {
                    visitRepository.save(new Visit(user, article));
                    //TO DO add count to user visits
                }
            }
        }
<<<<<<< HEAD
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
=======
        catch (Exception e){
            log.info(e.toString());
>>>>>>> testings
        }
    }
//    public void addVisit(Article article) {
////        try {
////            User user = userService.getCurrentUser();
////            System.out.println("inside try");
////            if (user!=null) {
////                System.out.println("user is found");
////                UserVisits userVisits = userVisitService.getCurrentUserVisit(user);
////                Visit visit = getVisit(article);
////                System.out.println("the visit is");
////                System.out.println(visit);
////                if (visit != null) {
////                    visit.setCreatedAt(new Date());
////                } else {
////                    visit = createVisit(article);
////                    userVisits.setVisitcount(userVisits.getVisitcount() + 1);
////                }
////                if (userVisits.getVisitcount() > 200) {
////                    visitRepository.delete(visitRepository.findAllByUserOrderByCreatedAt(user).get(0));
////                    userVisits.setVisitcount(userVisits.getVisitcount() - 1);
////                }
//            }
//        }
//        catch (Exception e){
//            System.out.println("inside the catch");
//            System.out.println(e);
//        }

    public List<Visit> getAllVisits(){
        User user = userService.getCurrentUser();
        if (user != null) {
            return visitRepository.findAllByUserOrderByCreatedAt(user);
        }
        else {
            return null;
        }
    }
    public void deleteByArticleId(Long id){
        visitRepository.deleteByArticleId(id);
    }

}
