package senkondratev.demos.DemoSetronica.service.user;

import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import senkondratev.demos.DemoSetronica.dto.ProductDTO;
import senkondratev.demos.DemoSetronica.dto.UserRequestDTO;
import senkondratev.demos.DemoSetronica.entity.Info;
import senkondratev.demos.DemoSetronica.entity.Price;
import senkondratev.demos.DemoSetronica.entity.Product;
import senkondratev.demos.DemoSetronica.exception.NoRelatedDataException;
import senkondratev.demos.DemoSetronica.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProductService {
    private ProductRepository productRepository;
    private ModelMapper mapper;

    public UserProductService(ProductRepository productRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @SneakyThrows
    public ProductDTO getProduct(UserRequestDTO dto) {
        Product product = productRepository
                .findByIdAndPrice_CurrencyAndInfo_Language(
                        dto.getProductId(),
                        dto.getPriceCurrency(),
                        dto.getInfoLanguage());
        if (product == null) {
            throw new NoRelatedDataException("There's no product with provided parameters");
        }
        return mapper.map(filterProduct(product, dto), ProductDTO.class);
    }

    @SneakyThrows
    public List<ProductDTO> getAllProducts(UserRequestDTO dto, int page, int size) {
        List<Product> products = productRepository
                .findAllByPrice_CurrencyAndInfo_Language(
                        dto.getPriceCurrency(),
                        dto.getInfoLanguage(),
                        PageRequest.of(page, size));
        validateProductList(products);
        List<Product> filteredProducts = new ArrayList<>(products.size());
        products.forEach(p -> filteredProducts.add(filterProduct(p, dto)));
        return filteredProducts.stream().map(p -> mapper.map(p, ProductDTO.class)).collect(Collectors.toList());
    }

    @SneakyThrows
    public List<ProductDTO> getProductsByName(UserRequestDTO dto, int page, int size) {
        List<Product> products = productRepository
                .findAllByPrice_CurrencyAndInfo_LanguageAndInfo_NameContaining(
                        dto.getPriceCurrency(),
                        dto.getInfoLanguage(),
                        dto.getGoodsName(),
                        PageRequest.of(page, size)
                );
        validateProductList(products);
        List<Product> filteredProducts = new ArrayList<>(products.size());
        products.forEach(p -> filteredProducts.add(filterProduct(p, dto)));
        return filteredProducts.stream().map(p -> mapper.map(p, ProductDTO.class)).collect(Collectors.toList());
    }

    @SneakyThrows
    public List<ProductDTO> getProductsByDescription(UserRequestDTO dto, int page, int size){
        List<Product> products = productRepository
                .findAllByPrice_CurrencyAndInfo_LanguageAndInfo_DescriptionContaining(
                        dto.getPriceCurrency(),
                        dto.getInfoLanguage(),
                        dto.getGoodsDescription(),
                        PageRequest.of(page, size)
                );
        validateProductList(products);
        List<Product> filteredProducts = new ArrayList<>(products.size());
        products.forEach(p -> filteredProducts.add(filterProduct(p, dto)));
        return filteredProducts.stream().map(p -> mapper.map(p, ProductDTO.class)).collect(Collectors.toList());
    }

    private Product filterProduct(Product product, UserRequestDTO dto) {
        Product filteredProduct = new Product();
        filteredProduct.setPrice(filterPrices(product.getPrice(), dto.getPriceCurrency()));
        filteredProduct.setInfo(filterInfo(product.getInfo(), dto.getInfoLanguage()));
        filteredProduct.setModificationDate(product.getModificationDate());
        filteredProduct.setCreationDate(product.getCreationDate());
        filteredProduct.setId(product.getId());
        return filteredProduct;
    }

    private List<Price> filterPrices(List<Price> prices, String currency) {
        return prices.stream()
                .filter(p -> p.getCurrency().equals(currency))
                .collect(Collectors.toList());
    }

    private List<Info> filterInfo(List<Info> info, String language) {
        return info.stream()
                .filter(i -> i.getLanguage().equals(language))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private void validateProductList(List<Product> products){
        if (products == null || products.size() == 0){
            throw  new NoRelatedDataException("There's no product with provided parameters");
        }
    }
}
