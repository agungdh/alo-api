package com.bpkad.api.alo.resource;

import com.bpkad.api.alo.repo.SpmRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/spm")
public class SpmResource {
    @Autowired
    SpmRepo spmRepo;

    @GetMapping("/cariSpmUntukPenerimaan")
    public ResponseEntity<List<Map<String, Object>>> cariSpmUntukPenerimaan() {
        return ResponseEntity.ok().body(spmRepo.cariSpmUntukPenerimaan("gaji"));
    }
}
