package com.omerfurkan.studentforum.exceptions;


import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class StuNetError {
    private String message;
    private int status;
    private ZonedDateTime timestamp;

}
