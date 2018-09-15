package com.gmail.sasha.myproject;


import com.gmail.sasha.myproject.converter.impl.toEntity.DiscountConverter;
import com.gmail.sasha.myproject.model.DiscountDTO;
import com.gmail.sasha.myproject.model.ItemDTO;
import com.gmail.sasha.myproject.service.DiscountService;
import com.gmail.sasha.myproject.service.ItemService;
import com.gmail.sasha.myproject.service.impl.DiscountServiceImpl;
import com.gmail.sasha.myproject.service.impl.ItemServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {

   private static final Logger logger = LogManager.getLogger(AppTest.class);

    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void createItems() {
        ItemService itemService = new ItemServiceImpl();

        List<ItemDTO> itemDTOList =new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            itemDTOList. add(   generateItems());

        }
        List<ItemDTO> receivedItems = itemService.save(itemDTOList);

        System.out.println(receivedItems);


    }

    @Test
    public void createDiscount(){


        DiscountDTO discountDTO1 =new DiscountDTO();
        discountDTO1.setExpirationDate(LocalDateTime.now().plusDays(3));
        discountDTO1.setInterestRate(new BigDecimal(30));
        discountDTO1.setName("discount 30%");
        DiscountDTO discountDTO2 =new DiscountDTO();
        discountDTO2.setExpirationDate(LocalDateTime.now().plusDays(5));
        discountDTO2.setInterestRate(new BigDecimal(10));
        discountDTO2.setName("discount 10%");
        DiscountDTO discountDTO3 =new DiscountDTO();
        discountDTO3.setExpirationDate(LocalDateTime.now().plusDays(7));
        discountDTO3.setInterestRate(new BigDecimal(20));
        discountDTO3.setName("discount 20%");

        List<DiscountDTO> discounts = new ArrayList<>();
        discounts.add(discountDTO1);
        discounts.add(discountDTO3);
        discounts.add(discountDTO2);

        DiscountService discountService =new DiscountServiceImpl();
        discountService.save(discounts);




    }

    @Test
    public void assignDiscountsToItems(){
          /*  for(ItemDTO itemDTO:itemsBetweenTwoHundredAndThreeHundred){
            itemDTO.setDiscounts(sicountconverter.toEntityList(discounts));
        }*/
         /*   List<ItemDTO> itemsBetweenTwoHundredAndThreeHundred = itemService.getItemsInPriceRange(200, 299);
        System.out.println(itemsBetweenTwoHundredAndThreeHundred);*/
    }



        private String[] arrayDescription = {"уникальное описание 1",
                "описание 2",
                "описание 3",
                "описание 4",
                "описание 5",
                "описание 6",
                "описание 7",
                "описание 8",
                "описание 9",
                "описание 10",

        };
        private List<String> list = new ArrayList<>(Arrays.asList(arrayDescription));


    private static String getRandomValue(Set<String> someSet) {
        int rd = new Random().nextInt(someSet.size());
        int index = 0;
        for (String element : someSet) {
            if (index == rd) {
                return element;
            }
            index++;
        }
        return null;

    }

    /**
     * Rigorous Test :-)
     */




    private ItemDTO generateItems() {

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setUniqueNumber(generateUniqNumber());
        itemDTO.setName(getRandomValue(generateRandomName()));
        itemDTO.setDescription(generateRandomDescription());
        itemDTO.setPrice(getRandomPrice());
        return itemDTO;

      /*  */
    }

    @Test
    public void getItems(){
        ItemService itemService = new ItemServiceImpl();
        itemService.getAllItems();
    }

    private Set<String> generateRandomName() {

        Set<String> names = new HashSet<>();
        try (Stream<String> stream = Files.lines(Paths.get("D://items"))) {
            stream.forEach(names::add);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return names;


    }

    private String generateUniqNumber() {
        return UUID.randomUUID().toString();
    }

    private String generateRandomDescription() {

        int randindex = new Random().nextInt(list.size());
        //list.remove(randindex);
        return list.get(randindex);

    }

    private BigDecimal getRandomPrice() {
       String range = String.valueOf(new Random().nextInt(300-100) + 100);
        return new BigDecimal(range + ".0");

    }


}