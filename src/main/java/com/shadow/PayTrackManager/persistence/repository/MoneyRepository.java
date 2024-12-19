package com.shadow.PayTrackManager.persistence.repository;

import com.shadow.PayTrackManager.persistence.entity.Money;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyRepository extends JpaRepository<Money, Long> {
}
