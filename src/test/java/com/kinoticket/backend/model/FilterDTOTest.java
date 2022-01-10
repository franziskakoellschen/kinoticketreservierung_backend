package com.kinoticket.backend.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FilterDTOTest {

    @Test
    void testFilterDTO() {

        FilterDTO filterDTO = new FilterDTO();
        filterDTO.setSearchString("Harry Potter");
        filterDTO.setDate1(new Date());
        filterDTO.setDate2(new Date());
        filterDTO.setLanguage("DE");
        filterDTO.setGenre("Comedy");

        assertNotNull(
                filterDTO
        );
    }

}
