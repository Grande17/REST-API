package com.crud.tasks.domain;

import lombok.*;


@Builder
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Mail {
    private  String mailTo;
    private  String subject;
    private  String message;
    private  String toCc;

    public static  MailBuilder builder = new MailBuilder();
}
