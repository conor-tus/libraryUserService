package org.tus.libraryuser.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tus.libraryuser.dto.CheckedBooksDto;
import org.tus.libraryuser.dto.LibraryUserDto;
import org.tus.libraryuser.dto.ResponseDto;

import java.util.List;

@FeignClient("CHECKED-BOOKS-SERVICE")
public interface CheckedBookInterface {
    @PostMapping("api/user/{userId}/checkout/{bookName}")
    public ResponseEntity<CheckedBooksDto> checkoutBook(@PathVariable int userId, @PathVariable String bookName, @RequestBody LibraryUserDto libraryUserDto);

    @GetMapping("api/user/{userid}/checkout/")
    public ResponseEntity<List<CheckedBooksDto>> getCheckedBooks(@PathVariable int userid);

    @GetMapping("api/user/{userid}/checkout/{checkedBookid}")
    public ResponseEntity<CheckedBooksDto> getCheckedBook(@PathVariable int userid, @PathVariable int checkedBookid);

    @PutMapping("api/user/{userid}/checkout/{checkedBookid}")
    public ResponseEntity<ResponseDto> updateCheckoutBook(@PathVariable int userid, @PathVariable int checkedBookid, @RequestBody CheckedBooksDto checkedBooksDto);

    @DeleteMapping("api/user/{userid}/checkout/{checkedBookid}")
    public ResponseEntity<ResponseDto> deleteCheckoutBook(@PathVariable int userid, @PathVariable int checkedBookid);

}
