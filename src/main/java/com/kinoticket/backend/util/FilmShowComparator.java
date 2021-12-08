package com.kinoticket.backend.util;

import java.util.Comparator;

import com.kinoticket.backend.model.FilmShow;

public class FilmShowComparator implements Comparator<FilmShow> {

    @Override
    public int compare(FilmShow o1, FilmShow o2) {
        if (
            o1.getDate().equals(o2.getDate())
        ) {
            return o1.getTime().compareTo(o2.getTime());
        }
        return o1.getDate().compareTo(o2.getDate());
    }
}
