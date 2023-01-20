package com.pequla.model.rest;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CachedData {
    private Integer id;
    private String name;
    private String uuid;
    private String discordId;
    private String tag;
    private String avatar;
    private String guildId;
    private LocalDateTime createdAt;
}
