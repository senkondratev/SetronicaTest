package senkondratev.demos.DemoSetronica.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import senkondratev.demos.DemoSetronica.dto.PriceDTO;
import senkondratev.demos.DemoSetronica.service.admin.PriceService;

import java.util.List;

@RestController
@RequestMapping("/admin/price")
public class AdminPriceController {
    private PriceService priceService;

    public AdminPriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @PostMapping("/create")
    public ResponseEntity<PriceDTO> createPrice(@Validated(PriceDTO.CreatePrice.class) @RequestBody PriceDTO priceDTO){
        return new ResponseEntity<>(priceService.createPrice(priceDTO), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PriceDTO> getPrice(@PathVariable Long id){
        return new ResponseEntity<>(priceService.getPrice(id), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<PriceDTO>> getAllPrices(){
        return new ResponseEntity<>(priceService.getAllPrices(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<PriceDTO> updatePrice(@Validated(PriceDTO.UpdatePrice.class) @RequestBody PriceDTO priceDTO){
        return new ResponseEntity<>(priceService.updatePrice(priceDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePrice(@PathVariable Long id){
        priceService.deletePrice(id);
        return new ResponseEntity<>(String.format("Price with id %d was successfully deleted", id), HttpStatus.OK);
    }

}
