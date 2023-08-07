package maryam.service.like;

import lombok.RequiredArgsConstructor;
import maryam.data.like.LikeRepository;
import maryam.data.product.ArticleRepository;
import maryam.data.product.ProductRepository;
import maryam.models.like.Like;
import maryam.models.product.Article;
import maryam.models.user.User;
import maryam.service.user.UserService;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.Optional;

@Service @RequiredArgsConstructor @Transactional
public class LikeService {
    private final LikeRepository likeRepository;
//    private final UserRepository userRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final ArticleRepository articleRepository;

    public Boolean check_like(Long id){
        //System.out.println("inside ckeck likes");
        //Optional<Product> optionalProduct = productRepository.findById(id);
        Optional<Article> optionalArticle = articleRepository.findById(id);
        User user = userService.getCurrentUser();
//        System.out.println(user);
        if (user != null) {
            try {
                return likeRepository.findByArticleAndUser(optionalArticle.get(), userService.getCurrentUser()).isPresent();
            }
            catch (Exception e){
                return false;
            }
        }
        else {
            return false;
        }
    }

    public Like likeArticle(Long id){
        //Optional<Product> optionalProduct = productRepository.findById(id);
        Optional<Article> optionalArticle = articleRepository.findById(id);
        try{
            //User user = userRepository.findByUsername((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            Optional<Like> optionalLike = likeRepository.findByArticleAndUser(optionalArticle.get(),userService.getCurrentUser());
            if(optionalLike.isPresent()){
                 likeRepository.delete(optionalLike.get());
                 return null;
            }
            else{
                likeRepository.save(new Like(optionalArticle.get(),userService.getCurrentUser()));
            }
        }
        catch (Exception e){
            return null;
        }
        return null;
    }
    public void deleteByArticleId(Long id){
        try {
            likeRepository.deleteByArticleId(id);
        }
        catch (Exception e){
            System.out.println("something went wrong in like service");
        }
    }
}
