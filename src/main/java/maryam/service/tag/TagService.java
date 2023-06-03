package maryam.service.tag;

import lombok.RequiredArgsConstructor;
import maryam.data.product.TagRepository;
import maryam.models.product.Product;
import maryam.models.tag.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service @RequiredArgsConstructor @Transactional
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> addTagToProduct(Product product, List<Tag> tags){
        System.out.println(tags);
        List<Tag> tagList = new ArrayList<>();
        System.out.println(61);
        Optional<Tag> optionalTag;
        System.out.println(62);
        for(Tag tag:tags) {
            System.out.println(63);
            System.out.println(tag.getName().toString());
            optionalTag = tagRepository.getByName("summer");
            System.out.println(64);
            if (optionalTag.isPresent()){
                System.out.println(65);
                tagList.add(optionalTag.get());
                System.out.println(66);
            }
            else {
                System.out.println(67);
                tagList.add(tagRepository.save(new Tag(tag.getName())));
                System.out.println(68);
            }
        }
        System.out.println(69);
        product.setTags(tagList);
        System.out.println(69.5);
        return tagList;
    }
    public List<Tag> updateTagProduct(Product product,List<Tag> tags){
        return addTagToProduct(product,tags);
    }
    public List<Tag> updateProductTags(Product product,List<Tag> tags){
        List<Tag> tagList = new ArrayList<>();

        for (Tag tag:tags){
            Optional<Tag> optionalTag = tagRepository.getByName(tag.getName());
            if (optionalTag.isPresent()){
                tagList.add(optionalTag.get());
            }
            else {
                tagList.add(tagRepository.save(tag));
            }
        }
        product.setTags(tagList);
        return tagList;
    }
}
