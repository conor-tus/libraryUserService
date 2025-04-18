package org.tus.libraryuser.dto;

import lombok.Data;

@Data
public class CheckedBooksDto {

    private int checkedBookId;
    private String bookName;
    private int libraryUserId;
    private String checkedStatus;
    private String checkoutDate;
}
