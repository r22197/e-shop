package eshop.backend.utils;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Function;

public class EntityUtils {

    public static  <T> T findByIdOrElseThrow(Long id, JpaRepository<T, Long> repository, Function<Long, RuntimeException> exceptionSupplier) {
        return repository.findById(id)
                .orElseThrow(() -> exceptionSupplier.apply(id));
    }
}
