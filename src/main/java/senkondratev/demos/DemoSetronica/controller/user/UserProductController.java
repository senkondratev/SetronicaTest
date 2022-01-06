package senkondratev.demos.DemoSetronica.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import senkondratev.demos.DemoSetronica.dto.ProductDTO;
import senkondratev.demos.DemoSetronica.dto.UserRequestDTO;
import senkondratev.demos.DemoSetronica.service.user.UserProductService;

import java.util.List;

@RestController
@RequestMapping("/user/product")
public class UserProductController {
    private UserProductService productService;

    public UserProductController(UserProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/get")
    public ResponseEntity<ProductDTO> getProduct(@Validated({UserRequestDTO.UserRequest.class,
            UserRequestDTO.UserSingleProductRequest.class})
                                                 @RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(productService.getProduct(userRequestDTO), HttpStatus.OK);
    }

    @GetMapping(path = "/getAll", params = {"page", "size"})
    public ResponseEntity<List<ProductDTO>> getAllProducts(@RequestParam("page") int page,
                                                           @RequestParam("size") int size,
                                                           @Validated(UserRequestDTO.UserRequest.class)
                                                           @RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(productService.getAllProducts(userRequestDTO, page, size), HttpStatus.OK);
    }

    @GetMapping(path = "/getByName", params = {"page", "size"})
    public ResponseEntity<List<ProductDTO>> getProductsByName(@RequestParam("page") int page,
                                                              @RequestParam("size") int size,
                                                              @Validated({UserRequestDTO.UserRequest.class,
                                                                      UserRequestDTO.UserRequestByName.class})
                                                              @RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(productService.getProductsByName(userRequestDTO, page, size), HttpStatus.OK);
    }

    @GetMapping(path = "/getByDescription", params = {"page", "size"})
    public ResponseEntity<List<ProductDTO>> getProductsByDescription(@RequestParam("page") int page,
                                                              @RequestParam("size") int size,
                                                              @Validated({UserRequestDTO.UserRequest.class,
                                                                      UserRequestDTO.UserRequestByDescription.class})
                                                              @RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(productService.getProductsByDescription(userRequestDTO, page, size), HttpStatus.OK);
    }

}
