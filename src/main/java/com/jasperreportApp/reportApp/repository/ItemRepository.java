package com.jasperreportApp.reportApp.repository;

import com.jasperreportApp.reportApp.entity.Item;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends ListCrudRepository<Item, Long> {
}
