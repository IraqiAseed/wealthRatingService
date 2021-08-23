package com.epam.services.impl;

import com.epam.model.Person;
import com.epam.model.RichPerson;
import com.epam.repo.RichPersonRepository;
import com.epam.services.interfaces.Asset;
import com.epam.services.interfaces.Fortune;
import com.epam.services.interfaces.WealthThreshold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class WealthRatingManager {

    @Autowired
    private RichPersonRepository richPersonRepo;

    @Autowired
    private Asset asset;

    @Autowired
    private Fortune fortuneFormula;

    @Autowired
    private WealthThreshold wealthThreshold;

    public String handlePerson(Person person) {

        double evaluateResponse = asset.evaluateAssetPerCity(person.getPersonalInfo().getCity()).get();
        double personFortune = fortuneFormula.fortune(person.getFinancialInfo().getCash(), person.getFinancialInfo().getNumberOfAssets(), evaluateResponse);
        double wealthThresholdByCentralBank = wealthThreshold.wealthThreshold().get();

        if (personFortune > wealthThresholdByCentralBank) {
            System.out.println("person is rich ... ");
            fillRichPersonDb(person, personFortune);
        }
        return "evaluateResponse:"+evaluateResponse + ",  wealthThresholdByCentralBank:" + wealthThresholdByCentralBank + ",  personFortune:" + personFortune;
    }

    @Transactional
    private void fillRichPersonDb(Person person, Double fortune) {

        richPersonRepo.save(RichPerson.builder().
                firstName(person.getPersonalInfo().getFirstName()).
                lastName(person.getPersonalInfo().getLastName()).
                id(person.getId()).
                fortune(fortune).
                build());

    }

    public List<RichPerson> getAllRich() {
        return richPersonRepo.findAll();
    }


    public Optional<RichPerson> getRichPerson(Long id) {
        return richPersonRepo.findById(id);
    }

    public void deleteRichPersonDb() {
        richPersonRepo.deleteAll();
    }
}
