package com.kinoticket.backend.model;

import javax.persistence.*;
import org.springframework.lang.NonNull;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Entity
@Table(name = "Authorization")
@Data
public class Authorization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @NonNull
    private String email;

    @Column
    @NonNull
    private String password;
    //maybe better char[] or with an hash value

    @Column
    private boolean confirmed;

    @Column
    private String emailCode;

    @Column
    private Date timeStampRegistration;
    
    @Column
    private Date timeStampForgotPassword;

    @PrePersist
    protected void onCreate(){
        timeStampRegistration = new Date();
    }

    public Long getId() {
        return id;
    }
}
