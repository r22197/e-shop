package eshop.backend.repository;

import eshop.backend.model.Price;
import eshop.backend.model.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    Price findTopByVariantOrderByDateOfChangeDesc(Variant variant);
    List<Price> findByVariantOrderByDateOfChangeAsc(Variant variant);
}
