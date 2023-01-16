package maryam.data.picture;

import maryam.models.picture.Picture;
import org.springframework.data.repository.CrudRepository;

public interface PictureRepository extends CrudRepository<Picture,Long> {
}
