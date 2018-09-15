package com.gmail.sasha.myproject.converter.impl.toDto;



import com.gmail.sasha.myproject.converter.DTOConverter;
import com.gmail.sasha.myproject.model.Discount;
import com.gmail.sasha.myproject.model.DiscountDTO;

import java.util.List;
import java.util.stream.Collectors;

public class DiscountDTOConverter implements DTOConverter<DiscountDTO, Discount> {
    @Override
    public DiscountDTO toDTO(Discount entity) {
        DiscountDTO discountDTO = new DiscountDTO();
        discountDTO.setName(entity.getName());
        discountDTO.setInterestRate(entity.getInterestRate());
        discountDTO.setExpirationDate(entity.getExpirationDate());
        discountDTO.setName(entity.getName());
        discountDTO.setItems(entity.getItems());
        return discountDTO;
    }


}