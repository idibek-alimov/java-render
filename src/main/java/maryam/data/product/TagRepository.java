package maryam.data.product;

import maryam.models.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {

    //@Query(value = "SELECT * FROM tag WHERE name=?1",nativeQuery = true)
    Optional<Tag> getByName(String name);
    //Optional<Tag> getByName(String name);
    @Modifying
    @Query(value = "DELETE FROM tag_product WHERE product_id=?1",nativeQuery = true)
    void deleteByProductId(Long id);
}
