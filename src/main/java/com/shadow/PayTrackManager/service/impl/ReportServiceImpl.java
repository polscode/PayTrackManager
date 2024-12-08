package com.shadow.PayTrackManager.service.impl;

import com.shadow.PayTrackManager.dto.*;
import com.shadow.PayTrackManager.exception.ObjectNotFoundException;
import com.shadow.PayTrackManager.persistence.entity.*;
import com.shadow.PayTrackManager.persistence.repository.ReportRepository;
import com.shadow.PayTrackManager.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Override
    public ReportCalculatedDataDTO calculateReport(ReportRequestDTO reportRequestDTO) {

        ReportCalculatedDataDTO reportCalculated = new ReportCalculatedDataDTO();
        BigDecimal calculatedTotalDiscount = reportRequestDTO.getDiscounts()
                .stream()
                .map(DiscountRequestDTO::getAmounts)
                .flatMap(List::stream)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        reportCalculated.setTotalDiscount(calculatedTotalDiscount);

        BigDecimal calculatedTotalMoney = reportRequestDTO.getMonies()
                .stream()
                .map(money -> new BigDecimal(money.getValue()).multiply(money.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        reportCalculated.setTotalMoney(calculatedTotalMoney);

        BigDecimal calculatedTotalPayable = reportRequestDTO.getTotalPlanilla().subtract(calculatedTotalDiscount);
        reportCalculated.setTotalPayable(calculatedTotalPayable);

        BigDecimal calculatedBalance = reportRequestDTO.getTotalPlanilla().subtract(calculatedTotalDiscount.add(calculatedTotalMoney));
        reportCalculated.setBalance(calculatedBalance);

        return reportCalculated;
    }

    @Override
    public Page<ReportResponseDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Report> findById(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Report> findByDate(Pageable pageable) {
        return null;
    }

    @Override
    public Report save(SaveReportDTO saveReportDTO) {

        Optional<Report> reportFromDB = reportRepository.findById(saveReportDTO.getId());
        if (reportFromDB.isEmpty()) {
            Report report = new Report();
            report.setId(saveReportDTO.getId());
            report.setTotalPlanilla(saveReportDTO.getTotalPlanilla());

            Driver driver = new Driver();
            driver.setId(saveReportDTO.getDriverId());
            report.setDriver(driver);

            Plate plate = new Plate();
            plate.setId(saveReportDTO.getPlateId());
            report.setPlate(plate);

            List<Discount> discounts = new ArrayList<>();
            saveReportDTO.getDiscounts().forEach((discountDto) -> {

                Discount discount = new Discount();
                String name = discountDto.getName();
                try {
                    DiscountType type = DiscountType.valueOf(name.toUpperCase());
                    discount.setDiscountType(type);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Tipo de descuento invalido: " + name);
                }

                discount.setReport(report);

                List<Amount> amounts = new ArrayList<>();
                if (discountDto.getAmounts() != null) {

                    discountDto.getAmounts().forEach(amountValue -> {
                        Amount amount = new Amount();
                        amount.setValue(amountValue);
                        amount.setDiscount(discount);
                        amounts.add(amount);
                    });
                }
                discount.setAmounts(amounts);
                discounts.add(discount);

            });

            report.setDiscounts(discounts);

            List<Money> monies = new ArrayList<>();
            if (saveReportDTO.getMonies() != null) {
                saveReportDTO.getMonies().forEach(moneyDto -> {
                    Money money = new Money();
                    String value = moneyDto.getValue();

                    try {
                        Denomination type = Denomination.valueOf(value.toUpperCase());
                        money.setDenomination(type);
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Tipo de denominacion invalido: " + value);
                    }

                    money.setQuantity(moneyDto.getQuantity());
                    money.setReport(report);
                    monies.add(money);
                });
            }
            report.setMonies(monies);
            try {
                return reportRepository.save(report);
            } catch (ObjectOptimisticLockingFailureException ex) {
                throw new RuntimeException("El reporte ha sido modificado por otro proceso. Intenta nuevamente.");
            }

        } else {
            throw new ObjectNotFoundException("Ya existe el reporte");
        }
    }
}
