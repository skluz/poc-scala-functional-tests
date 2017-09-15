package com.funtis.petstore.app.services;

import com.funtis.petstore.app.exceptions.ResourceNotFoundException;
import com.funtis.petstore.app.model.Pet;
import com.funtis.petstore.app.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by skluz@g2a.com on 14/09/2017.
 */
@Service
public class PetService {

    @Autowired
    private PetRepository repository;

    public Iterable<Pet> listPets() {
        return repository.findAll();
    }

    public Pet getPet(Long petId) {
        Pet pet = repository.findOne(petId);
        if(pet == null) throw new ResourceNotFoundException("Pet with id: " + petId + " doesn't exist");
        return pet;
    }

    public Pet createPet(Pet pet) {
        Pet created = repository.save(pet);
        return repository.findOne(created.getId());
    }
}
