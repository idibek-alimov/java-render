package maryam.data.product;

import maryam.models.product.Color;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ColorRepository extends CrudRepository<Color,Long> {

    Optional<Color> findByName(String name);

    @Query(value = "SELECT * FROM color  WHERE ?1 % ANY(STRING_TO_ARRAY(color.name,' ')) or" +
            " ?1 % ANY(STRING_TO_ARRAY(color.nameru,' ')) or" +
            " ?1 % ANY(STRING_TO_ARRAY(color.nametj,' '))",nativeQuery = true)
    List<Color> findBySimilarName(String name);
}
