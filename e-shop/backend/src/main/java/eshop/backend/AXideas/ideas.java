package eshop.backend.AXideas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

public class ideas {

    //var user = utils.verifyValidity(userRepository, request.getUserId());
    //var product = utils.verifyValidity(productRepository, request.getProductId());

    private <T> T validate(JpaRepository<T, Long> repository, Long id) throws Exception {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("The object with Id %d was not found.", id))
                );
    }

}
