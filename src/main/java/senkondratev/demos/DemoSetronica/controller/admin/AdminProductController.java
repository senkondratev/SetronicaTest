package senkondratev.demos.DemoSetronica.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import senkondratev.demos.DemoSetronica.dto.*;
import senkondratev.demos.DemoSetronica.service.admin.AdminProductService;

import java.util.List;

@RestController
@RequestMapping("/admin/product")
public class AdminProductController {
    private AdminProductService productService;

    public AdminProductController(AdminProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> createProduct(@Validated(
            {ProductDTO.CreateProduct.class, InfoDTO.CreateInfo.class, PriceDTO.CreatePrice.class})
                                                        @RequestBody ProductDTO productDTO){
        System.out.println(productDTO);
        return new ResponseEntity<>(productService.createProduct(productDTO), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id){
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductDTO> updateProduct(@Validated(
            {ProductDTO.UpdateProduct.class, InfoDTO.UpdateInfo.class, PriceDTO.UpdatePrice.class})
                                                       @RequestBody ProductDTO productDTO){
        return new ResponseEntity<>(productService.updateProduct(productDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(String.format("Product %d was successfully deleted", id), HttpStatus.OK);
    }

    @PutMapping("/add/price")
    public ResponseEntity<ProductDTO> addPrice(@RequestBody AddPriceDTO addPriceDTO){
        return new ResponseEntity<>(productService.addPrice(addPriceDTO), HttpStatus.OK);
    }

    @PutMapping("/add/info")
    public ResponseEntity<ProductDTO> addInfo(@RequestBody AddInfoDTO addInfoDTO){
        return new ResponseEntity<>(productService.addInfo(addInfoDTO), HttpStatus.OK);
    }
}
