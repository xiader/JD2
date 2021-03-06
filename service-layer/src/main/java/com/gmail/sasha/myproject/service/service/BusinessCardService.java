package com.gmail.sasha.myproject.service.service;

import com.gmail.sasha.myproject.service.model.BusinessCardDTO;

import java.util.List;

public interface BusinessCardService {

    List<BusinessCardDTO> getAllBusinessCards();

    List<BusinessCardDTO> getAllByUserId(Long id);

    BusinessCardDTO getOneById(Long id);

    void removeById(Long id);

    List<BusinessCardDTO> findAllByCurrentUserEmail();

    void save(BusinessCardDTO businessCardDTO);

}
