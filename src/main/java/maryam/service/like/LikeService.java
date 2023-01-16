package maryam.service.like;

import lombok.RequiredArgsConstructor;
import maryam.data.like.LikeRepository;
import maryam.data.product.ArticleRepository;
import maryam.data.product.ProductRepository;
import maryam.data.user.UserRepository;
import maryam.models.like.Like;
import maryam.models.product.Article;
import maryam.models.product.Product;
import maryam.models.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service @RequiredArgsConstructor @Transactional
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ArticleRepository articleRepository;

    public Boolean check_like(Long id){
        //System.out.println("inside ckeck likes");
        //Optional<Product> optionalProduct = productRepository.findById(id);
        Optional<Article> optionalArticle = articleRepository.findById(id);
        User user = userRepository.findByUsername((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return likeRepository.findByArticleAndUser(optionalArticle.get(),user).isPresent();
    }

    public Like like_article(Long id){
        //Optional<Product> optionalProduct = productRepository.findById(id);
        Optional<Article> optionalArticle = articleRepository.findById(id);
        try{
            User user = userRepository.findByUsername((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            Optional<Like> optionalLike = likeRepository.findByArticleAndUser(optionalArticle.get(),user);
            if(optionalLike.isPresent()){
                 likeRepository.delete(optionalLike.get());
                 return null;
            }
            else{
                likeRepository.save(new Like(optionalArticle.get(),user));
            }
        }
        catch (Exception e){
            return null;
        }
        return null;
    }
}
