package com.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.config.AppConstants;
import com.app.entites.Brand;
import com.app.payloads.BrandDTO;
import com.app.payloads.BrandResponse;
import com.app.services.BrandService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "E-Commerce Application")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @PostMapping("/admin/brand")
    public ResponseEntity<BrandDTO> createBrand(@Valid @RequestBody Brand brand) {
        BrandDTO savedBrandDTO = brandService.createBrand(brand);
        return new ResponseEntity<>(savedBrandDTO, HttpStatus.CREATED);
    }

    @GetMapping("/public/brands")
    public ResponseEntity<BrandResponse> getBrands(
        @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        @RequestParam(name = "sortBy", defaultValue = "brandName") String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        BrandResponse brands = brandService.getBrands(pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(brands);
    }


    @PutMapping("/admin/brands/{brandId}")
    public ResponseEntity<BrandDTO> updateBrand(@RequestBody Brand brand, @PathVariable Long brandId) {
        BrandDTO updatedBrandDTO = brandService.updateBrand(brand, brandId);
        return new ResponseEntity<>(updatedBrandDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/brands/{brandId}")
    public ResponseEntity<String> deleteBrand(@PathVariable Long brandId) {
        String status = brandService.deleteBrand(brandId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}

