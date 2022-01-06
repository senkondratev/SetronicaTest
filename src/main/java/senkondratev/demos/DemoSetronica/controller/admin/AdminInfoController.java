package senkondratev.demos.DemoSetronica.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import senkondratev.demos.DemoSetronica.dto.InfoDTO;
import senkondratev.demos.DemoSetronica.service.admin.InfoService;

import java.util.List;

@RestController
@RequestMapping("/admin/info")
public class AdminInfoController {
    private InfoService infoService;

    public AdminInfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @PostMapping("/create")
    public ResponseEntity<InfoDTO> createInfo(@Validated(InfoDTO.CreateInfo.class) @RequestBody InfoDTO infoDTO){
        return new ResponseEntity<>(infoService.createInfo(infoDTO), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<InfoDTO> getInfo(@PathVariable Long id){
        return new ResponseEntity<>(infoService.getInfo(id), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<InfoDTO>> getAllInfo(){
        return new ResponseEntity<>(infoService.getAllInfo(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<InfoDTO> updateInfo(@Validated(InfoDTO.UpdateInfo.class) @RequestBody InfoDTO infoDTO){
        return new ResponseEntity<>(infoService.updateInfo(infoDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteInfo(@PathVariable Long id){
        infoService.deleteInfo(id);
        return new ResponseEntity<>(String.format("Entity with id %d was successfully deleted", id), HttpStatus.OK);
    }
}
