package org.tus.libraryuser.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.tus.libraryuser.dto.BookDto;
import org.tus.libraryuser.dto.ResponseDto;

import java.util.List;

@FeignClient("BOOK-SERVICE")
public interface BookInterface {

    @GetMapping("api/book/{bookName}")
    public ResponseEntity<BookDto> getBook(@PathVariable String bookName);

    @GetMapping("api/book")
    public ResponseEntity<List<BookDto>> getAllBooks();


}
