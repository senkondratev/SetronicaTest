package senkondratev.demos.DemoSetronica.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import senkondratev.demos.DemoSetronica.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByIdAndPrice_CurrencyAndInfo_Language(Long id, String currency, String language);

    List<Product> findAllByPrice_CurrencyAndInfo_Language(String currency, String language, Pageable pageable);

    List<Product> findAllByPrice_CurrencyAndInfo_LanguageAndInfo_NameContaining(String currency, String language,
                                                                                String name, Pageable pageable);

    List<Product> findAllByPrice_CurrencyAndInfo_LanguageAndInfo_DescriptionContaining(String currency, String language,
                                                                                String description, Pageable pageable);
}
