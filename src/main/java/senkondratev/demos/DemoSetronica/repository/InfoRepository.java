package senkondratev.demos.DemoSetronica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senkondratev.demos.DemoSetronica.entity.Info;

public interface InfoRepository extends JpaRepository<Info, Long> {
}
