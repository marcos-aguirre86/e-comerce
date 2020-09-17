package ar.seguroestaaca.store.customservices.repository;

import ar.seguroestaaca.store.customservices.repository.entity.Customer;
import ar.seguroestaaca.store.customservices.repository.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomRepository extends JpaRepository<Customer,Long> {
    public Customer findByNumberID(String numberID);
    public List<Customer> findByLastName(String lastName);
    public List<Customer> findByRegion(Region region);
}
