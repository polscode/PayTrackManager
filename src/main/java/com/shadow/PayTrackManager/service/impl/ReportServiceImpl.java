package com.shadow.PayTrackManager.service.impl;

import com.shadow.PayTrackManager.dto.*;
import com.shadow.PayTrackManager.exception.ObjectNotFoundException;
import com.shadow.PayTrackManager.persistence.entity.*;
import com.shadow.PayTrackManager.persistence.repository.DiscountRepository;
import com.shadow.PayTrackManager.persistence.repository.MoneyRepository;
import com.shadow.PayTrackManager.persistence.repository.ReportRepository;
import com.shadow.PayTrackManager.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private MoneyRepository moneyRepository;

    @Override
    public CalculatedDataDTO calculateReport(ReportSummaryDTO reportSummaryDTO) {

        CalculatedDataDTO reportCalculated = new CalculatedDataDTO();
        BigDecimal calculatedTotalDiscount = reportSummaryDTO.getDiscounts()
                .stream()
                .map(DiscountDTO::getAmounts)
                .flatMap(List::stream)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        reportCalculated.setTotalDiscount(calculatedTotalDiscount);

        BigDecimal calculatedTotalMoney = reportSummaryDTO.getMonies()
                .stream()
                .map(money -> new BigDecimal(money.getDenomination()).multiply(money.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        reportCalculated.setTotalMoney(calculatedTotalMoney);

        BigDecimal calculatedTotalPayable = reportSummaryDTO.getTotalPlanilla().subtract(calculatedTotalDiscount);
        reportCalculated.setTotalPayable(calculatedTotalPayable);

        BigDecimal calculatedBalance = reportSummaryDTO.getTotalPlanilla().subtract(calculatedTotalDiscount.add(calculatedTotalMoney));
        reportCalculated.setBalance(calculatedBalance);

        return reportCalculated;
    }

    @Override
    public Page<BasicReportDataDTO> findAll(Pageable pageable) {

        Page<Report> reportPage = reportRepository.findAll(pageable);
        return reportPage.map(report -> new BasicReportDataDTO(
           report.getId(),
           report.getDate()
        ));
    }

    @Override
    public Page<BasicReportDataDTO> findById(Long id, Pageable pageable) {
        Page<Report> reportPage = reportRepository.findById(id, pageable);
        return reportPage.map(report -> new BasicReportDataDTO(
                report.getId(),
                report.getDate()
        ));
    }

    @Override
    public Page<BasicReportDataDTO> findByDate(LocalDate date, Pageable pageable) {

        Page<Report> reportPage = reportRepository.findByDate(date, pageable);
        return reportPage.map(report -> new BasicReportDataDTO(
                report.getId(),
                report.getDate()
        ));
    }

    @Override
    public Report save(SaveReportDTO saveReportDTO) {

        if (reportRepository.findById(saveReportDTO.getId()).isPresent()) {
            throw new ObjectNotFoundException("El reporte ya existe en la base de datos");
        }
        Report report = new Report();
        report.setId(saveReportDTO.getId());
        report.setTotalPlanilla(saveReportDTO.getTotalPlanilla());
        report.setDriver(createDriver(saveReportDTO.getDriverId()));
        report.setPlate(createPlate(saveReportDTO.getPlateId()));
        report.setDiscounts(createDiscounts(report, saveReportDTO.getDiscounts()));
        report.setMonies(createMoneis(report, saveReportDTO.getMonies()));
        return reportRepository.save(report);

    }

    @Override
    public Report update(UpdateReportDTO updateReportDTO) {
        Report reportFromDB = reportRepository.findById(updateReportDTO.getId())
                .orElseThrow(()-> new ObjectNotFoundException("No existe el reporte con el id " + updateReportDTO.getId()));
        reportFromDB.setTotalPlanilla(updateReportDTO.getTotalPlanilla());
        reportFromDB.setDriver(createDriver(updateReportDTO.getDriverId()));
        reportFromDB.setPlate(createPlate(updateReportDTO.getPlateId()));
        reportFromDB.setDiscounts(updateDiscounts(reportFromDB, updateReportDTO.getDiscounts()));
        reportFromDB.setMonies(updateMonies(reportFromDB, updateReportDTO.getMonies()));
        return reportRepository.save(reportFromDB);
    }

    private Driver createDriver(Long driverId) {
        Driver driver = new Driver();
        driver.setId(driverId);
        return driver;
    }

    private Plate createPlate(Long plateId) {
        Plate plate = new Plate();
        plate.setId(plateId);
        return plate;
    }
    private List<Discount> updateDiscounts(Report report, List<DiscountDTO> discountDTOS) {
        List<Discount> discounts = report.getDiscounts();
        discountDTOS.forEach((discountDto) -> {

            Discount discount = discountRepository.findById(discountDto.getId())
                    .orElseThrow(() -> new ObjectNotFoundException("No existe el descuento con el ID " + discountDto.getId()));
            discount.setAmounts(discountDto.getAmounts());
            discount.setReport(report);
            discount.setAmounts(discountDto.getAmounts());
        });
        return discounts;
    }

    private List<Discount> createDiscounts(Report report, List<DiscountDTO> discountDTOS) {
        List<Discount> discounts = new ArrayList<>();
        discountDTOS.forEach((discountDto) -> {

            Discount discount = new Discount();
            String name = discountDto.getDiscountType();
            try {
                DiscountType type = DiscountType.valueOf(name.toUpperCase());
                discount.setDiscountType(type);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Tipo de descuento invalido: " + name);
            }

            discount.setReport(report);

            discount.setAmounts(discountDto.getAmounts());
            discounts.add(discount);

        });
        return discounts;
    }

    private List<Money> createMoneis(Report report, List<MoneyDTO> moniMoneyDTOS) {
        List<Money> monies = new ArrayList<>();
        if (moniMoneyDTOS != null) {
            moniMoneyDTOS.forEach(moneyDto -> {
                Money money = new Money();
                money.setId(moneyDto.getId());
                String value = moneyDto.getDenomination();

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
        return monies;
    }

    private List<Money> updateMonies(Report report, List<MoneyDTO> moniMoneyDTOS) {
        List<Money> monies = report.getMonies();
        if (moniMoneyDTOS != null) {
            moniMoneyDTOS.forEach(moneyDto -> {
                Money money = moneyRepository.findById(moneyDto.getId())
                                .orElseThrow(() -> new ObjectNotFoundException("No existe el descuento con el ID " + moneyDto.getId()));

                money.setQuantity(moneyDto.getQuantity());
                money.setReport(report);
                monies.add(money);
            });
        }
        return monies;
    }

}
