package ar.seguroestaaca.store.shoppingservice.repository;

import ar.seguroestaaca.store.shoppingservice.repository.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemsRepository extends JpaRepository<InvoiceItem,Long> {
}
