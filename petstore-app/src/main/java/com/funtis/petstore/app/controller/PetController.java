package com.funtis.petstore.app.controller;

import com.funtis.petstore.app.model.Pet;
import com.funtis.petstore.app.exceptions.ResourceNotFoundException;
import com.funtis.petstore.app.repository.PetRepository;
import com.funtis.petstore.app.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Sławomir Kluz on 12/09/2017.
 */
@RestController
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping("/pets")
    public Iterable<Pet> listPets() {
        return petService.listPets();
    }

    @GetMapping("/pet/{petId}")
    public Pet getPet(@PathVariable Long petId) {
        return petService.getPet(petId);
    }

    @PostMapping("/pets")
    public Pet createPet(@RequestBody Pet pet) {
        return petService.createPet(pet);
    }

}
