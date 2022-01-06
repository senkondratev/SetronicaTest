package senkondratev.demos.DemoSetronica.service.admin;

import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import senkondratev.demos.DemoSetronica.dto.AddInfoDTO;
import senkondratev.demos.DemoSetronica.dto.AddPriceDTO;
import senkondratev.demos.DemoSetronica.dto.ProductDTO;
import senkondratev.demos.DemoSetronica.entity.Info;
import senkondratev.demos.DemoSetronica.entity.Price;
import senkondratev.demos.DemoSetronica.entity.Product;
import senkondratev.demos.DemoSetronica.exception.KeyConstrainViolatonException;
import senkondratev.demos.DemoSetronica.repository.InfoRepository;
import senkondratev.demos.DemoSetronica.repository.PriceRepository;
import senkondratev.demos.DemoSetronica.repository.ProductRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminProductService {
    private ProductRepository productRepository;
    private PriceRepository priceRepository;
    private InfoRepository infoRepository;
    private InfoService infoService;
    private PriceService priceService;
    private ModelMapper mapper;

    public AdminProductService(ProductRepository productRepository, PriceRepository priceRepository
            , InfoRepository infoRepository, InfoService infoService, PriceService priceService, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.priceRepository = priceRepository;
        this.infoRepository = infoRepository;
        this.infoService = infoService;
        this.priceService = priceService;
        this.mapper = mapper;
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        final Product product = new Product();
        if(productDTO.getInfoDTO() != null) {
            List<Info> infoList = productDTO.getInfoDTO()
                    .stream()
                    .map(i -> mapper.map(i, Info.class))
                    .collect(Collectors.toList());
            infoList.forEach(i -> i.setProduct(product));
            infoRepository.saveAll(infoList);
            product.setInfo(infoList);
        }
        if(productDTO.getPriceDTO() != null) {
            List<Price> priceList = productDTO.getPriceDTO()
                    .stream()
                    .map(p -> mapper.map(p, Price.class))
                    .collect(Collectors.toList());
            priceList.forEach(p -> p.setProduct(product));
            priceRepository.saveAll(priceList);
            product.setPrice(priceList);
        }
        product.setCreationDate(new Date());
        return mapper.map(productRepository.save(product), ProductDTO.class);
    }

    public ProductDTO getProduct(Long id) {
        return mapper.map(productRepository.findById(id).get(), ProductDTO.class);
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(p -> mapper.map(p, ProductDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getId()).get();
        Long productId = product.getId();
        productDTO.getInfoDTO().stream()
                .filter(dto -> infoService.isInfoRelatable(dto, productId))
                .forEach(infoService::updateInfo);
        productDTO.getPriceDTO().stream()
                .filter(dto -> priceService.isPriceRelatable(dto, productId))
                .forEach(priceService::updatePrice);
        product.setModificationDate(new Date());
        return mapper.map(product, ProductDTO.class);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @SneakyThrows
    @Transactional
    public ProductDTO addPrice(AddPriceDTO addPriceDTO) {
        Price price = priceRepository.findById(addPriceDTO.getPriceId()).get();
        if (price.getProduct() != null){
            throw new KeyConstrainViolatonException("Provided price is already in use");
        }
        Product product = productRepository.findById(addPriceDTO.getProductId()).get();
        price.setProduct(product);
        product.getPrice().add(price);
        return mapper.map(product, ProductDTO.class);
    }

    @SneakyThrows
    @Transactional
    public ProductDTO addInfo(AddInfoDTO addInfoDTO) {
        Info info = infoRepository.findById(addInfoDTO.getInfoId()).get();
        if (info.getProduct() != null){
            throw new KeyConstrainViolatonException("Provided info is already in use");
        }
        Product product = productRepository.findById(addInfoDTO.getProductId()).get();
        info.setProduct(product);
        product.getInfo().add(info);
        return mapper.map(product, ProductDTO.class);
    }
}
