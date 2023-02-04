package maryam.service.product;

import lombok.RequiredArgsConstructor;
import maryam.controller.product.CreateArticleHolder;
import maryam.controller.product.PictureHolder;
import maryam.data.product.ProductRepository;
import maryam.data.user.UserRepository;
import maryam.models.inventory.Inventory;
import maryam.models.picture.Picture;
import maryam.models.product.Color;
import maryam.models.product.Product;
import maryam.models.tag.Tag;
import maryam.models.user.User;
import maryam.models.uservisit.Visit;
import maryam.service.article.ArticleService;
import maryam.service.inventory.InventoryService;
import maryam.service.picture.PictureService;
import maryam.service.tag.TagService;
import maryam.service.visit.VisitService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.util.*;
import com.google.common.collect.Iterables;

@Service @RequiredArgsConstructor @Transactional
public class ProductService implements ProductServiceInterface{
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PictureService pictureService;
    private final InventoryService inventoryService;
    private final VisitService visitService;
    private final TagService tagService;
    private final ArticleService articleService;
    public Product addProduct(Product product,
                              List<Inventory> inventories,
                              Color color,
                              List<MultipartFile> pictures,
                              List<Tag> tags)  {
        User user = userRepository.findByUsername((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(user == null){
            throw new RuntimeException("the fucking user is not found");
        }
        product.setUser(user);
        Product createdProduct = productRepository.save(product);
        System.out.println("before add article");
        createdProduct.addArticle(articleService.addArticle(inventories,pictures,color,createdProduct));
        System.out.println("after add article");
        //        List<Inventory> createdInventories = inventoryService.addInventories(inventories,createdProduct);
//        List<Picture> createdPictures = pictureService.addPictures(pictures,createdProduct);
//        createdProduct.setPictures(createdPictures);
//        createdProduct.setInventory(createdInventories);
        tagService.addTagToProduct(createdProduct,tags);

        return createdProduct;
    }
    public Product addProduct(Product product,
                              List<Tag> tags,
                              List<CreateArticleHolder> articleHolders,
                                List<PictureHolder> pictureHolders)  {
        User user = userRepository.findByUsername((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(user == null){
            throw new RuntimeException("the fucking user is not found");
        }
        product.setUser(user);
        Product createdProduct = productRepository.save(product);
        for(int i=0;i<articleHolders.size();i++){
            createdProduct.addArticle(articleService.addArticle(
                    articleHolders.get(i).getInventories(),
                    pictureHolders.get(i).getPictures(),
                    articleHolders.get(i).getColor(),
                    createdProduct));
        }
//        for(CreateArticleHolder articleHolder:articleHolders){
//            createdProduct.addArticle(articleService.addArticle(
//                    articleHolder.getInventories(),
//                    articleHolder.getPictures(),
//                    articleHolder.getColor(),
//                    createdProduct));
//        }
        tagService.addTagToProduct(createdProduct,tags);

        return createdProduct;
    }
    @Override
    public Product addProduct(Product product,
                              List<Inventory> inventories,
                              Color color,
                              List<MultipartFile> pictures)  {
        User user = userRepository.findByUsername((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(user == null){
            throw new RuntimeException("the fucking user is not found");
        }
        product.setUser(user);
        Product createdProduct = productRepository.save(product);
        createdProduct.addArticle(articleService.addArticle(inventories,pictures,color,createdProduct));
//        List<Inventory> createdInventories = inventoryService.addInventories(inventories,createdProduct);
//        List<Picture> createdPictures = pictureService.addPictures(pictures,createdProduct);
//        createdProduct.setPictures(createdPictures);
//        createdProduct.setInventory(createdInventories);
        return createdProduct;
    }
    public Product addArticleToProduct(List<Inventory> inventories,
                                       Color color,
                                       List<MultipartFile> pictures,
                                       Long id){
        System.out.println("indise att article to product function");
        User user = userRepository.findByUsername((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(user == null){
            throw new RuntimeException("the fucking user is not found");
        }
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(!optionalProduct.isPresent()){
            throw new RuntimeException("the fucking product does not exist");
        }
        if(optionalProduct.get().getUser()!=user){
            throw new RuntimeException("motherfucker trying to add article to some other motherfuckers product");
        }
        System.out.println("passed all the ifs");
        optionalProduct.get().addArticle(articleService.addArticle(inventories,pictures,color,optionalProduct.get()));
        System.out.println("before returning the product");
        return optionalProduct.get();
    }
//    public Product addProduct(Product product,List<Inventory> inventories){
//        User user = userRepository.findByUsername((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        if(user == null){
//            throw new RuntimeException("the fucking user is not found");
//        }
//        product.setUser(user);
//        Product createdProduct = productRepository.save(product);
//        List<Inventory> createdInventories = inventoryService.addInventories(inventories,createdProduct);
//        //List<Picture> createdPictures = pictureService.addPictures(pictures,createdProduct);
//        //createdProduct.setPictures(createdPictures);
//        createdProduct.setInventory(createdInventories);
//        return createdProduct;
//    }

    @Override
    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }
    public List<Product> findByName(String name,Integer page,Integer amount){
        return productRepository.findByNameSimilar(name,PageRequest.of(page,amount));
    }

    @Override
    public Page<Product> listOfProducts(Integer page,Integer amount) {
        return productRepository.findAll(PageRequest.of(page,amount));
    }

    @Override
    public void removeProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product editProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> productsByUser(Integer page,Integer amount){
        User user = userRepository.findByUsername((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return productRepository.getByUser(user,PageRequest.of(page,amount));
    }

    public List<Product> similarProductsByTag(Long id,Integer page,Integer amount){
        Iterable<Tag> tags = productRepository.findById(id).get().getTags();
        Set<Long> tagIds = new HashSet<>();
        for (Tag tag:tags){
            tagIds.add(tag.getId());
        }
        return productRepository.getBySimilarTags(tagIds,PageRequest.of(page,amount));
    }
    public List<Product> getResentVisits(Integer page,Integer amount){

        User user = userRepository.findByUsername((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        //Iterable<Product> products = Iterables.limit(productRepository.getByResentVisit(user.getId()),amount);
        return productRepository.getByResentVisit(user.getId(),PageRequest.of(page,amount));
    }
    public List<Product> productsByTag(Integer page,Integer amount){
        List<Visit> visits = visitService.getAllVisits();
        Set<Long> tag_Ids = new HashSet<>();
        Set<Long> visitedProductsId = new HashSet<>();
        for (Visit visit:visits){
            visitedProductsId.add(visit.getProduct().getId());
            for(Tag tag:visit.getProduct().getTags()) {
                tag_Ids.add(tag.getId());
            }
        }
        return productRepository.getByTags(tag_Ids,visitedProductsId);
    }
    public List<Product> productsByPopularity(Integer page,Integer amount){
        return productRepository.getMostVisited(amount,PageRequest.of(page,amount));
    }
    public List<Product> getByPage(String name,Integer page, Integer amount){
        return productRepository.findProductByname(name,PageRequest.of(page,amount));
    }
}
