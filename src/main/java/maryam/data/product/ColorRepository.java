package maryam.data.product;

import maryam.models.product.Color;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<Color,Long> {
    public Optional<Color> findByName(String name);
}
