package maryam.data.product;

import maryam.models.tag.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TagRepository extends CrudRepository<Tag,Long> {
    Optional<Tag> findByName(String name);
}
