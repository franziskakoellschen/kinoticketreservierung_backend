package com.kinoticket.backend.util;

import java.util.Comparator;

import com.kinoticket.backend.model.FilmShow;

public class FilmShowComparator implements Comparator<FilmShow> {

    @Override
    public int compare(FilmShow o1, FilmShow o2) {

        if (o1.getDate() == null) return 1;
        if (o2.getDate() == null) return -1;

        if (o1.getDate().equals(o2.getDate())) {

            if (o1.getTime() == null) return 1;
            if (o2.getTime() == null) return -1;

            return o1.getTime().compareTo(o2.getTime());
        }
    
        return o1.getDate().compareTo(o2.getDate());
    }
}
