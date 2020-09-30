package ar.seguroestaaca.store.shoppingservice.service;

import ar.seguroestaaca.store.shoppingservice.client.CustomerClient;
import ar.seguroestaaca.store.shoppingservice.client.ProductClient;
import ar.seguroestaaca.store.shoppingservice.modelo.Customer;
import ar.seguroestaaca.store.shoppingservice.modelo.Product;
import ar.seguroestaaca.store.shoppingservice.repository.InvoiceItemsRepository;
import ar.seguroestaaca.store.shoppingservice.repository.InvoiceRepository;
import ar.seguroestaaca.store.shoppingservice.repository.entity.Invoice;
import ar.seguroestaaca.store.shoppingservice.repository.entity.InvoiceItem;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    InvoiceItemsRepository invoiceItemsRepository;

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private ProductClient productClient;

    @Override
    public List<Invoice> findInvoiceAll() {
        return  invoiceRepository.findAll();
    }


    @Override
    public Invoice createInvoice(Invoice invoice) {
        Invoice invoiceDB = invoiceRepository.findByNumberInvoice ( invoice.getNumberInvoice () );
        if (invoiceDB !=null){
            return  invoiceDB;
        }
        invoice.setState("CREATED");
        invoiceDB = invoiceRepository.save(invoice);

        invoiceDB.getItems().forEach(x -> {
            this.productClient.updateStockProduct(x.getProductId(), x.getQuantity() * -1);
        });

        return invoiceDB;
    }


    @Override
    public Invoice updateInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null){
            return  null;
        }
        invoiceDB.setCustomerId(invoice.getCustomerId());
        invoiceDB.setDescription(invoice.getDescription());
        invoiceDB.setNumberInvoice(invoice.getNumberInvoice());
        invoiceDB.getItems().clear();
        invoiceDB.setItems(invoice.getItems());
        return invoiceRepository.save(invoiceDB);
    }


    @Override
    public Invoice deleteInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null){
            return  null;
        }
        invoiceDB.setState("DELETED");
        return invoiceRepository.save(invoiceDB);
    }

    @Override
    public Invoice getInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        if(invoice != null){
            Customer customer = this.customerClient.getCustomer(invoice.getCustomerId()).getBody();
            invoice.setCustomer(customer);
            List<InvoiceItem> invoiceItems = invoice.getItems().stream().map(invoiceItem -> {
               Product product = this.productClient.getProduct(invoiceItem.getProductId()).getBody();
               invoiceItem.setProduct(product);
               return invoiceItem;
            }).collect(Collectors.toList());
            invoice.setItems(invoiceItems);
        }

        return invoice;
    }
}
