package com.dinlurceis.smartmed.service.impl;

import com.dinlurceis.smartmed.dto.request.BillItemRequest;
import com.dinlurceis.smartmed.dto.request.BillRequest;
import com.dinlurceis.smartmed.dto.response.BillResponse;
import com.dinlurceis.smartmed.exception.AppException;
import com.dinlurceis.smartmed.exception.ErrorCode;
import com.dinlurceis.smartmed.mapper.BillItemMapper;
import com.dinlurceis.smartmed.mapper.BillMapper;
import com.dinlurceis.smartmed.model.Bill;
import com.dinlurceis.smartmed.model.BillItem;
import com.dinlurceis.smartmed.model.Medicine;
import com.dinlurceis.smartmed.model.Pharmacist;
import com.dinlurceis.smartmed.repository.BillRepository;
import com.dinlurceis.smartmed.repository.MedicineRepository;
import com.dinlurceis.smartmed.repository.PharmacistRepository;
import com.dinlurceis.smartmed.service.BillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final PharmacistRepository pharmacistRepository;
    private final MedicineRepository medicineRepository;
    private final BillMapper billMapper;
    private final BillItemMapper billItemMapper;

    @Override
    public List<BillResponse> getBills(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return billRepository.findAll(pageRequest)
                .stream()
                .map(billMapper::toBillResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BillResponse getBillById(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BILL_NOT_FOUND));
        return billMapper.toBillResponse(bill);
    }

    @Override
    public BillResponse createBill(BillRequest request) {
        Bill bill = new Bill();

        Pharmacist pharmacist = pharmacistRepository.findById(request.getPharmacistId())
                .orElseThrow(() -> new AppException(ErrorCode.PHARMACIST_NOT_FOUND));
        bill.setPharmacist(pharmacist);
        bill.setCreatedAt(LocalDateTime.now());

        Set<BillItem> billItems = new HashSet<>();
        int totalQuantity = 0;
        double totalPrice = 0.0;

        for (BillItemRequest itemRequest : request.getBillItems()) {
            BillItem billItem = billItemMapper.toBillItem(itemRequest);
            Medicine medicine = medicineRepository.findById(itemRequest.getMedicineId())
                    .orElseThrow(() -> new AppException(ErrorCode.MEDICINE_NOT_FOUND));
            billItem.setMedicine(medicine);
            billItem.setBill(bill);

            billItems.add(billItem);
            totalQuantity += itemRequest.getQuantity();
            totalPrice += itemRequest.getImportPrice() * itemRequest.getQuantity();

            // cập nhật số lượng thuốc
            medicine.setQuantity(medicine.getQuantity() + itemRequest.getQuantity());
            medicineRepository.save(medicine);
        }

        bill.setBillItems(billItems);
        bill.setTotalQuantity(totalQuantity);
        bill.setTotalPrice(totalPrice);

        Bill savedBill = billRepository.save(bill);
        return billMapper.toBillResponse(savedBill);
    }
}

