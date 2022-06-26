package com.oviktor.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public
class MessageData {

    private String recipientEmail;
    private String theme;
    private String text;
}
