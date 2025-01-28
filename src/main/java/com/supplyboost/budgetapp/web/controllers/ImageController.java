package com.supplyboost.budgetapp.web.controllers;


import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ImageController {

    private static final MediaType MEDIA_TYPE_WEBP = MediaType.parseMediaType("image/webp");

    @GetMapping("/image")
    public ResponseEntity<Resource> getImage() throws Exception{
        Path path = Paths.get("src/main/resources/static/images/404-image.webp");

        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .contentType(MEDIA_TYPE_WEBP)
                .body(resource);
    }
}
