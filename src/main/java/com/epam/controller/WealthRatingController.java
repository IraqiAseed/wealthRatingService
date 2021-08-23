package com.epam.controller;


import com.epam.model.Person;
import com.epam.model.RichPerson;
import com.epam.services.impl.WealthRatingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class WealthRatingController {

    @Autowired
    private WealthRatingManager wealthRatingManager;

    @PostMapping("wealth-rating")
    public String wealthRating(@RequestBody Person person) {
        return wealthRatingManager.handlePerson(person);

    }

    @GetMapping("/allRich")
    public List<RichPerson> getAllRichPeopleInDb() {

        return wealthRatingManager.getAllRich();
    }

    @GetMapping("/rich")
    public Optional<RichPerson> getRichPersonByHisId(@RequestParam Long id) {

        return wealthRatingManager.getRichPerson(id);
    }

    @GetMapping("/clean")
    public void deleteDb() {
        wealthRatingManager.deleteRichPersonDb();
    }

}
