package senkondratev.demos.DemoSetronica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senkondratev.demos.DemoSetronica.entity.Price;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
