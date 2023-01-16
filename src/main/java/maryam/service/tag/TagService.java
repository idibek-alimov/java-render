package maryam.service.tag;

import lombok.RequiredArgsConstructor;
import maryam.data.product.TagRepository;
import maryam.models.product.Product;
import maryam.models.tag.Tag;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor @Transactional
public class TagService {
    private final TagRepository tagRepository;
    @Transactional
    public List<Tag> addTagToProduct(Product product, List<Tag> tags){
        Optional<Tag> optionalTag;
        List<Tag> tagList = new ArrayList<>();
        for(Tag tag:tags){
            optionalTag = tagRepository.findByName(tag.getName());
            if(!optionalTag.isPresent()){
                optionalTag = Optional.of(tagRepository.save(new Tag(tag.getName())));
            }
            optionalTag.get().addProduct(product);
            tagList.add(optionalTag.get());
        }

        return tagList;
    }
}
