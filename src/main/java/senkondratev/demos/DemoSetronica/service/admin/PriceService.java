package senkondratev.demos.DemoSetronica.service.admin;

import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import senkondratev.demos.DemoSetronica.dto.PriceDTO;
import senkondratev.demos.DemoSetronica.entity.Price;
import senkondratev.demos.DemoSetronica.exception.KeyConstrainViolatonException;
import senkondratev.demos.DemoSetronica.repository.PriceRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PriceService {
    private PriceRepository priceRepository;
    private ModelMapper mapper;

    public PriceService(PriceRepository priceRepository,ModelMapper mapper) {
        this.priceRepository = priceRepository;
        this.mapper = mapper;
    }

    public PriceDTO createPrice(PriceDTO priceDTO){
        Price price = mapper.map(priceDTO, Price.class);
        price = priceRepository.save(price);
        return mapper.map(price, PriceDTO.class);
    }

    public PriceDTO getPrice(Long id){
        return mapper.map(priceRepository.findById(id).get(), PriceDTO.class);
    }

    public List<PriceDTO> getAllPrices(){
        List<Price> priceList = priceRepository.findAll();
        return priceList.stream().map(p -> mapper.map(p, PriceDTO.class)).collect(Collectors.toList());
    }

    public PriceDTO updatePrice(PriceDTO priceDTO){
        Price price = priceRepository.findById(priceDTO.getPriceId()).get();
        price.setPrice(priceDTO.getCurrencyAmount());
        if(priceDTO.getCurrencyName() != null){
            price.setCurrency(priceDTO.getCurrencyName());
        }
        price = priceRepository.save(price);
        return mapper.map(price, PriceDTO.class);
    }

    @SneakyThrows
    public void deletePrice(Long id){
        priceRepository.deleteById(id);
    }

    @SneakyThrows
    public boolean isPriceRelatable(PriceDTO priceDTO, Long productId){
        Price price = priceRepository.findById(priceDTO.getPriceId()).get();
        if (price.getProduct() == null){
            throw new KeyConstrainViolatonException("Provided price id does not relate to any product id");
        }
        if (!Objects.equals(price.getProduct().getId(), productId)){
            throw new KeyConstrainViolatonException("Provided price id does not relate to provided product id");
        }
        return true;
    }
}
