package com.funtis.petstore.app.repository;

import com.funtis.petstore.app.domain.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Sławomir Kluz on 12/09/2017.
 */
@Service
public interface TagRepository extends CrudRepository<Tag, Long> {

}
