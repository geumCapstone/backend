package com.geum.mvcServer.apis.event.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class EventVO {
    private String title;

    private String body;

    private String imageUrl;

    private Timestamp regDate;

    private Timestamp updateDate;
}
