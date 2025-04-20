package org.tus.libraryuser.dto;

import lombok.Data;

@Data
public class BookDto {

    private int BookId;
    private String bookName;
    private String author;
    private String pageCount;
}
