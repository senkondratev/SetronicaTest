package senkondratev.demos.DemoSetronica.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import senkondratev.demos.DemoSetronica.dto.InfoDTO;
import senkondratev.demos.DemoSetronica.dto.PriceDTO;
import senkondratev.demos.DemoSetronica.dto.ProductDTO;
import senkondratev.demos.DemoSetronica.entity.Info;
import senkondratev.demos.DemoSetronica.entity.Price;
import senkondratev.demos.DemoSetronica.entity.Product;

@Configuration
public class ModelMapperConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        addCustomMappings(modelMapper);
        return modelMapper;
    }

    private void addCustomMappings(ModelMapper modelMapper){
        addInfoMappings(modelMapper);
        addPriceMappings(modelMapper);
        addProductMappings(modelMapper);
    }
    private void addInfoMappings(ModelMapper modelMapper){
        modelMapper.createTypeMap(InfoDTO.class, Info.class)
                .addMappings(mapper -> {
                    mapper.map(InfoDTO::getGoodsName, Info::setName);
                    mapper.map(InfoDTO::getGoodsDescription, Info::setDescription);
                    mapper.map(InfoDTO::getLanguage, Info::setLanguage);
                });
        modelMapper.createTypeMap(Info.class, InfoDTO.class)
                .addMappings(mapper -> {
                    mapper.map(Info::getId, InfoDTO::setInfoId);
                    mapper.map(Info::getName, InfoDTO::setGoodsName);
                    mapper.map(Info::getDescription, InfoDTO::setGoodsDescription);
                    mapper.map(Info::getLanguage, InfoDTO::setLanguage);
                });
    }

    private void addPriceMappings(ModelMapper modelMapper){
        modelMapper.createTypeMap(PriceDTO.class, Price.class)
                .addMappings(mapper -> {
                    mapper.map(PriceDTO::getCurrencyName, Price::setCurrency);
                    mapper.map(PriceDTO::getCurrencyAmount, Price::setPrice);
                });
        modelMapper.createTypeMap(Price.class, PriceDTO.class)
                .addMappings(mapper -> {
                    mapper.map(Price::getId, PriceDTO::setPriceId);
                    mapper.map(Price::getCurrency, PriceDTO::setCurrencyName);
                    mapper.map(Price::getPrice, PriceDTO::setCurrencyAmount);
                });
    }   

    private void addProductMappings(ModelMapper modelMapper){
        modelMapper.createTypeMap(Product.class, ProductDTO.class)
                .addMappings(mapper -> {
                    mapper.map(Product::getId, ProductDTO::setId);
                    mapper.map(Product::getPrice, ProductDTO::setPriceDTO);
                    mapper.map(Product::getInfo, ProductDTO::setInfoDTO);
                    mapper.map(Product::getCreationDate, ProductDTO::setCreationDate);
                    mapper.map(Product::getModificationDate, ProductDTO::setModificationDate);
                });
    }
}
