package com.geum.mvcServer.apis.event.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter @Setter
public class Event {

    @Id @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull @Column(length = 500)
    private String body;

    @Column(nullable = true)
    private String imageUrl;

    @CreationTimestamp
    private Timestamp regDate;

    @Column(nullable = true)
    private Timestamp updateDta;
}
