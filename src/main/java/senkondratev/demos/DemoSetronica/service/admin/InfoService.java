package senkondratev.demos.DemoSetronica.service.admin;

import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import senkondratev.demos.DemoSetronica.dto.InfoDTO;
import senkondratev.demos.DemoSetronica.entity.Info;
import senkondratev.demos.DemoSetronica.exception.KeyConstrainViolatonException;
import senkondratev.demos.DemoSetronica.repository.InfoRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InfoService {
    private InfoRepository infoRepository;
    private ModelMapper mapper;

    public InfoService(InfoRepository infoRepository, ModelMapper mapper) {
        this.infoRepository = infoRepository;
        this.mapper = mapper;
    }

    public InfoDTO createInfo(InfoDTO infoDTO){
        Info info = mapper.map(infoDTO, Info.class);
        info = infoRepository.save(info);
        return mapper.map(info, InfoDTO.class);
    }

    public InfoDTO getInfo(Long id){
        return mapper.map(infoRepository.findById(id).get(), InfoDTO.class);
    }

    public List<InfoDTO> getAllInfo(){
        List<Info> infoList = infoRepository.findAll();
        return infoList.stream().map(i -> mapper.map(i, InfoDTO.class)).collect(Collectors.toList());
    }

    public InfoDTO updateInfo(InfoDTO infoDTO){
        Info info = infoRepository.findById(infoDTO.getInfoId()).get();
        info.setName(infoDTO.getGoodsName());
        if(infoDTO.getGoodsDescription() != null){
            info.setDescription(infoDTO.getGoodsDescription());
        }
        if(infoDTO.getLanguage() != null){
            info.setLanguage(info.getLanguage());
        }
        info = infoRepository.save(info);
        return mapper.map(info, InfoDTO.class);
    }

    @SneakyThrows
    public void deleteInfo(Long id){
        infoRepository.deleteById(id);
    }

    @SneakyThrows
    public boolean isInfoRelatable(InfoDTO infoDTO, Long productId){
        Info info = infoRepository.findById(infoDTO.getInfoId()).get();
        if (info.getProduct() == null){
            throw new KeyConstrainViolatonException("Provided info id does not relate to any product id");
        }
        if (!Objects.equals(info.getProduct().getId(), productId)){
            throw new KeyConstrainViolatonException("Provided info id does not relate to provided product id");
        }
        return true;
    }
}
