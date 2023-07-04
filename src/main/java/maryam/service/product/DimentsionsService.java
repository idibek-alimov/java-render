package maryam.service.product;

import lombok.RequiredArgsConstructor;
import maryam.data.product.DimensionsRepository;
import maryam.models.product.Dimensions;
import maryam.models.product.Product;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DimentsionsService {
    private final DimensionsRepository dimensiontsRepository;
    public Dimensions addDimensions(Dimensions dimensions){
        Dimensions createdDimensions = dimensiontsRepository.save(dimensions);
        return createdDimensions;
    }
    public Dimensions addDimensionsToProduct(Dimensions dimensions,Product product){
        dimensions = addDimensions(dimensions);
        dimensions.setProduct(product);
        return dimensions;
    }
    public Dimensions updateDimensions(Dimensions dimensions,Product product){
        Optional<Dimensions> optionalDimensions = dimensiontsRepository.getByProduct(product);
        if (optionalDimensions.isPresent()){
            Dimensions updatableDimensions = optionalDimensions.get();
            updatableDimensions.setWidth(dimensions.getWidth());
            updatableDimensions.setLength(dimensions.getLength());
            updatableDimensions.setHeight(dimensions.getHeight());
            dimensiontsRepository.save(updatableDimensions);
            return updatableDimensions;
            }
        else {
            return null;
        }
    }
}
