package com.funtis.petstore.app.repository;

import com.funtis.petstore.app.domain.Category;
import com.funtis.petstore.app.domain.Pet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Sławomir Kluz on 12/09/2017.
 */
@Service
public interface CategoryRepository extends CrudRepository<Category, Long> {

}
