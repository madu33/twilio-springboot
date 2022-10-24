package com.schedule.twilio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MessageAndTime {
        private Integer month;
        private Integer day;
        private Integer hour;
        private Integer minute;
        private String message;
}
