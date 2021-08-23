package com.epam.services.impl;

import com.epam.model.FinancialInfo;
import com.epam.model.Person;
import com.epam.model.PersonalInfo;
import com.epam.model.RichPerson;
import com.epam.repo.RichPersonRepository;
import com.epam.services.interfaces.Asset;
import com.epam.services.interfaces.Fortune;
import com.epam.services.interfaces.WealthThreshold;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class WealthRatingManagerTest {

    public WealthRatingManagerTest() {
        System.out.println("Test for Wealth Rating Service");
    }

    @Autowired
    WealthRatingManager wealthRatingManager;

    @Autowired
    private Fortune fortuneFormula;

    @Autowired
    private RichPersonRepository richPersonRepo;

    @Autowired
    private WealthThreshold threshold;

    @Autowired
    private Asset asset;

    private final long testId1 = 987654321;
    private final long testId2 = 123456789;
    private final int assetsNumber = 50;
    private final double wealthCash = 16100000000.0;
    private final double normalCash = 100000.0;
    private final Person wealthyPerson = Person.builder()
            .id(testId1)
            .personalInfo(PersonalInfo.builder().city("Washington").firstName("Bill").lastName("Gates").build())
            .financialInfo(FinancialInfo.builder().cash(wealthCash).numberOfAssets(assetsNumber).build())
            .build();
    private final Person normalPerson = Person.builder()
            .id(testId2)
            .personalInfo(PersonalInfo.builder().city("Washington").firstName("John").lastName("Doe").build())
            .financialInfo(FinancialInfo.builder().cash(normalCash).numberOfAssets(0).build())
            .build();


    @Test
    void handleRichPerson() {

        double wealthThreshold = threshold.wealthThreshold().get();
        wealthRatingManager.handlePerson(wealthyPerson);
        RichPerson richPerson = richPersonRepo.findById(wealthyPerson.getId());

        assertEquals(true, richPerson.getFortune() > wealthThreshold);
        assertEquals(richPerson.getFirstName(), wealthyPerson.getPersonalInfo().getFirstName());
        assertEquals(richPerson.getLastName(), wealthyPerson.getPersonalInfo().getLastName());
        assertEquals(richPerson.getId(), wealthyPerson.getId());

        richPersonRepo.deleteById(wealthyPerson.getId());

    }

    @Test
    void handleNormalPerson() {

        wealthRatingManager.handlePerson(normalPerson);
        RichPerson richPerson = richPersonRepo.findById(normalPerson.getId());
        assertEquals(null, richPerson);

    }

    @Test
    void fortuneTest() {

        double evaluateResponse = asset.evaluateAssetPerCity("Washington").get();
        Double personFortune = fortuneFormula.fortune(wealthyPerson.getFinancialInfo().getCash(), wealthyPerson.getFinancialInfo().getNumberOfAssets(), evaluateResponse);
        assertEquals(wealthCash + assetsNumber * evaluateResponse, personFortune);
    }

    @Test
    void getAllRich() {
        List<RichPerson> beforeRichPeople = richPersonRepo.findAll();
        int size = beforeRichPeople.size();
        Person Alice = Person.builder()
                .id(999888)
                .personalInfo(PersonalInfo.builder().city("Washington").firstName("Alice").lastName("Bob").build())
                .financialInfo(FinancialInfo.builder().cash(wealthCash).numberOfAssets(assetsNumber).build())
                .build();
        wealthRatingManager.handlePerson(Alice);
        wealthRatingManager.handlePerson(wealthyPerson);
        wealthRatingManager.handlePerson(normalPerson);

        List<RichPerson> allRichPeople = richPersonRepo.findAll();

        RichPerson p1 = richPersonRepo.findById(Alice.getId());
        RichPerson p2 = richPersonRepo.findById(wealthyPerson.getId());

        allRichPeople.forEach(System.out::println);
        assertEquals(size+2,allRichPeople.size());

        assertEquals(allRichPeople.contains(p1),true);
        assertEquals(allRichPeople.contains(p2),true);

        richPersonRepo.deleteById(Alice.getId());
        richPersonRepo.deleteById(wealthyPerson.getId());



    }


}