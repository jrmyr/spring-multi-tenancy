package de.myrnet.multitenancydemo.controllers;

import de.myrnet.multitenancydemo.Tenant;
import de.myrnet.multitenancydemo.TenantContext;
import de.myrnet.multitenancydemo.domain.Product;
import de.myrnet.multitenancydemo.domain.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(value = "/{tenant}/getDesc/{id}",
            produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity fetDesc(
            @PathVariable("tenant") String tenantStr,
            @PathVariable("id") Integer id) {

        Tenant tenant = Tenant.getTenantFromString(tenantStr);
        if (tenant == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Unknown tenant: " + tenantStr);
        }
        TenantContext.setCurrentTenant(tenant);

        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get().getDescription());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
