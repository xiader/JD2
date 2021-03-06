package com.gmail.sasha.myproject.web.controllers;

import com.gmail.sasha.myproject.service.model.BusinessCardDTO;
import com.gmail.sasha.myproject.service.service.BusinessCardService;
import com.gmail.sasha.myproject.web.util.PageProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("controllerBusinessCard")
@RequestMapping("/business-card")
public class BusinessCardController {

    private static Logger logger = LogManager.getLogger(BusinessCardController.class);

    @Autowired
    @Qualifier("businessCardService")
    private BusinessCardService businessCardService;

    @Autowired
    @Qualifier("pageProperties")
    private PageProperties pageProperties;

    @Autowired
    @Qualifier("businessCardValidator")
    private Validator businessCardValidator;

    @GetMapping("/cards")
    @PreAuthorize("hasAuthority('MANAGE_BUSINESS_CARD')")
    public String getBusinessCardPage(
            ModelMap modelMap
    ) {
        List<BusinessCardDTO> businessCardDTOS = businessCardService.findAllByCurrentUserEmail();
        modelMap.addAttribute("businessCards", businessCardDTOS);
        return pageProperties.getBusinessCardPagePath();
    }

    @GetMapping("/card")
    @PreAuthorize("hasAuthority('MANAGE_BUSINESS_CARD')")
    public String getCreateBusinessCardPage(ModelMap modelMap) {
        modelMap.addAttribute("businessCard", new BusinessCardDTO());
        return pageProperties.getBusinessCardCreatePagePath();
    }

    @PostMapping("/card")
    @PreAuthorize("hasAuthority('MANAGE_BUSINESS_CARD')")
    public String createBusinessCard(
            @ModelAttribute("businessCard") BusinessCardDTO businessCard,
            ModelMap modelMap,
            BindingResult bindingResult) {
        businessCardValidator.validate(businessCard, bindingResult);
        if (bindingResult.hasErrors()) {
            modelMap.addAttribute("businessCard", businessCard);
            return pageProperties.getBusinessCardCreatePagePath();
        } else {
            businessCardService.save(businessCard);
            return "redirect:/business-card/cards";
        }
    }
}
