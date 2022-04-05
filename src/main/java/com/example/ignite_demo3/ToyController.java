package com.example.ignite_demo3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ToyController {
    private final ToyService toyService;

    @PostMapping("/get")
    public ResponseEntity getToy(){
        log.info("GET TOY");
        return ResponseEntity.status(HttpStatus.OK).body(toyService.getToy());
    }
    @PostMapping("/getId")
    public ResponseEntity getIdToy(@RequestBody ToyModel toyModel){
        log.info("GET ID TOY");
        return ResponseEntity.status(HttpStatus.OK).body(toyService.getIdToy(toyModel.getToy_id()));
    }

    @PostMapping("/insert")
    public ResponseEntity insertToy(@RequestBody ToyModel toyModel){
        log.info("INSERT TOY");
        return ResponseEntity.status(HttpStatus.OK).body(toyService.insertToy(toyModel));
    }
}
