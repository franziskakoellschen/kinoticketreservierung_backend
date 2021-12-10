package com.kinoticket.backend.model;

import java.io.Serializable;

public class FilmshowSeatPK implements Serializable {

        long seat;
        long filmShow;

        public boolean equals(Object object) {
            if (object instanceof FilmshowSeatPK) {
                FilmshowSeatPK pk = (FilmshowSeatPK) object;
                return seat == pk.seat && filmShow == pk.filmShow;
            } else {
                return false;
            }
        }

        public int hashCode() {
            int hash = 23;
            hash = hash * 31 + (int) seat;
            hash = hash * 31 + (int) filmShow;
            return hash;
        }
}
