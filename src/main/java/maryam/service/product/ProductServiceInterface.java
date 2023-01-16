package maryam.service.product;

import maryam.models.inventory.Inventory;
import maryam.models.picture.Picture;
import maryam.models.product.Color;
import maryam.models.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductServiceInterface {
    public Product addProduct(Product product, List<Inventory> inventories, Color color, List<MultipartFile> pictures);
    public void removeProduct(Long id);
    public Product editProduct(Product product);
    public Optional<Product> getProduct(Long id);
    public Page<Product> listOfProducts(Integer page, Integer amount);
}
