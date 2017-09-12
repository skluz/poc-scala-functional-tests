package com.funtis.petstore.app.controller;

import com.funtis.petstore.app.domain.Pet;
import com.funtis.petstore.app.exceptions.ResourceNotFoundException;
import com.funtis.petstore.app.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Sławomir Kluz on 12/09/2017.
 */
@RestController
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @GetMapping("/pets")
    public Iterable<Pet> findAll() {
        return petRepository.findAll();
    }

    @GetMapping("/pet/{petId}")
    public Pet find(@PathVariable Long petId) {
        Pet pet = petRepository.findOne(petId);
        if(pet == null) throw new ResourceNotFoundException("Pet with id: " + petId + " doesn't exist");
        return pet;
    }

    @PostMapping("/pets")
    public Pet save(@RequestBody Pet pet) {
        Pet created = petRepository.save(pet);
        return petRepository.findOne(created.getId());
    }

}
