package com.shadow.PayTrackManager.persistence.repository;

import com.shadow.PayTrackManager.persistence.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository <Discount, Long> {
}
