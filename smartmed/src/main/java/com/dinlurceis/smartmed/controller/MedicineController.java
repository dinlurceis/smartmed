package com.dinlurceis.smartmed.controller;

import com.dinlurceis.smartmed.dto.request.MedicineCreateRequest;
import com.dinlurceis.smartmed.dto.request.PageRequestDTO;
import com.dinlurceis.smartmed.dto.response.ApiResponse;
import com.dinlurceis.smartmed.dto.response.MedicineResponse;
import com.dinlurceis.smartmed.dto.response.PageResponse;
import com.dinlurceis.smartmed.model.Medicine;
import com.dinlurceis.smartmed.service.MedicineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/medicine")
@Slf4j
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @GetMapping("/list-all")
    public ApiResponse<PageResponse<List<MedicineResponse>>> getAllMedicine(
            @RequestParam(defaultValue = "1",required = false) int page,
            @RequestParam(defaultValue = "10",required = false) int size
    ){
        return ApiResponse.<PageResponse<List<MedicineResponse>>>builder()
                .message("Get all medicine successfully")
                .result(medicineService.getAllMedicine(page, size))
                .build();
    }
    
    @PostMapping("/upload")
    public ApiResponse<MedicineResponse> createMedicine(@RequestPart MedicineCreateRequest request,
                                                        @RequestPart(value = "file", required = false)MultipartFile file){
        return ApiResponse.<MedicineResponse>builder()
                .message("Create medicine successfully")
                .result(medicineService.createMedicine(request, file))
                .build();
    }
    
    @GetMapping("/{medicineId}")
    public ApiResponse<MedicineResponse> getMedicineById(@PathVariable Long medicineId) {
        return ApiResponse.<MedicineResponse>builder()
                .message("Create medicine successfully")
                .result(medicineService.getMedicineById(medicineId))
                .build();
    }

    @GetMapping("/category/{categoryId}")
    public ApiResponse<PageResponse<List<MedicineResponse>>> getMedicineByCategory(
            @RequestParam(defaultValue = "1",required = false) int page,
            @RequestParam(defaultValue = "10",required = false) int size,
            @PathVariable Long categoryId
    ) {
        return ApiResponse.<PageResponse<List<MedicineResponse>>>builder()
                .message("Get all medicine successfully")
                .result(medicineService.getMedicineByCategory(page, size, categoryId))
                .build();
    }

    @PutMapping("/update/{medicineId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<MedicineResponse> updateMedicine(
            @PathVariable Long medicineId,
            @RequestPart MedicineCreateRequest request,
            @RequestPart(value = "file", required = false)MultipartFile file
    ) {
        return ApiResponse.<MedicineResponse>builder()
                .message("Update medicine successfully")
                .result(medicineService.updateMedicine(medicineId, request, file))
                .build();
    }

    @DeleteMapping("delete/{medicineId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<?> deleteMedicine(@PathVariable Long medicineId) {
        medicineService.deleteMedicine(medicineId);
        return ApiResponse.builder()
                .message("Delete medicine successfully")
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<PageResponse<List<MedicineResponse>>> getMedicines(@RequestBody PageRequestDTO request) {
        return ApiResponse.<PageResponse<List<MedicineResponse>>>builder()
                .message("Get all medicine successfully")
                .result(medicineService.getMedicines(request))
                .build();
    }
}
