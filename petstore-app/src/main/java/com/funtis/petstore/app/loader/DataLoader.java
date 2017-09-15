package com.funtis.petstore.app.loader;

import com.funtis.petstore.app.model.Category;
import com.funtis.petstore.app.model.Pet;
import com.funtis.petstore.app.model.PetStatus;
import com.funtis.petstore.app.model.Tag;
import com.funtis.petstore.app.repository.CategoryRepository;
import com.funtis.petstore.app.repository.PetRepository;
import com.funtis.petstore.app.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by Sławomir Kluz on 12/09/2017.
 */
@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Tag white = new Tag("white");
        Tag black = new Tag("black");
        Tag old = new Tag("old");
        tagRepository.save(Arrays.asList(white, black, old));

        Category dogs = new Category("dogs");
        Category cats = new Category("cats");
        categoryRepository.save(Arrays.asList(dogs, cats));

        petRepository.save(new Pet("Pluto", dogs, PetStatus.available, Arrays.asList(white, old)));
        petRepository.save(new Pet("Filemon", cats, PetStatus.pending, Arrays.asList(black, old)));
        petRepository.save(new Pet("Garfield", cats, PetStatus.sold, Arrays.asList()));
    }
}
