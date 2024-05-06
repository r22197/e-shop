package eshop.backend.AXideas;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

public class ideas {

    //var user = utils.getByIdFromRepositoryOrThrow(userRepository, request.getUserId());
    //var product = utils.getByIdFromRepositoryOrThrow(productRepository, request.getProductId());

    public static <T> T getByIdFromRepositoryOrThrow(@NotNull JpaRepository<T, Long> repository, Long id) throws ResourceNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("The object with Id %d was not found.", id))
                );
    }

}
