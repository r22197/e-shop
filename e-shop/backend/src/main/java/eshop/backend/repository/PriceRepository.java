package eshop.backend.repository;

import eshop.backend.model.PriceHistory;
import eshop.backend.model.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<PriceHistory, Long> {
    PriceHistory findTopByVariantOrderByCreateDateDesc(Variant variant);
    List<PriceHistory> findByVariantOrderByCreateDateAsc(Variant variant);
    void deleteAllByVariant(Variant variant);
}
