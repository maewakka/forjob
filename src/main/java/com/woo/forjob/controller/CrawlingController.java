package com.woo.forjob.controller;

import com.woo.forjob.dto.crawling.CrawlingRequestDto;
import com.woo.forjob.service.CrawlingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping(value = "/job")
@Slf4j
@RestController
public class CrawlingController {

    private final CrawlingService crawlingService;

    @GetMapping("/main-category")
    public ResponseEntity getMainCategory() {
        return ResponseEntity.ok(crawlingService.getMainCategory());
    }

    @GetMapping("/sub-category")
    public ResponseEntity getSubCategory(@RequestParam(value = "main") Long mainCategoryId) {
        return ResponseEntity.ok(crawlingService.getSubCategory(mainCategoryId));
    }

    @GetMapping("/region")
    public ResponseEntity getRegions() {
        return ResponseEntity.ok(crawlingService.getRegion());
    }

    @PostMapping("/set")
    public void setInform(@RequestBody CrawlingRequestDto requestDto) throws IOException {
        crawlingService.setInform(requestDto);
    }

}
