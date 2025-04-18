package org.tus.libraryuser.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tus.libraryuser.constants.LibraryUserConstants;
import org.tus.libraryuser.dto.LibraryUserDto;
import org.tus.libraryuser.dto.ResponseDto;
import org.tus.libraryuser.service.ILibraryUserService;

@RestController
@RequestMapping(path="/api", produces= MediaType.APPLICATION_JSON_VALUE)
@Validated
public class LibraryUserController {

    private final ILibraryUserService iLibraryUserService;

    @Autowired
    public LibraryUserController(ILibraryUserService iLibraryUserService) {
        this.iLibraryUserService = iLibraryUserService;
    }

    @PostMapping("/user")
    public ResponseEntity<ResponseDto> createNewUser(@Valid @RequestBody LibraryUserDto libraryUserDto) {
        iLibraryUserService.addLibraryUser(libraryUserDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(LibraryUserConstants.STATUS_201, LibraryUserConstants.LIBRARY_USER_CREATED_MESSAGE_201));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<LibraryUserDto> getLibraryUserDetails(@PathVariable int id, @RequestParam(required = false) boolean isReversed) {
        LibraryUserDto libraryUserDto = iLibraryUserService.fetchUserById(id,isReversed);
        return ResponseEntity.status(HttpStatus.OK).body(libraryUserDto);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @PathVariable int id, @RequestBody LibraryUserDto libraryUserDto) {
        boolean isUpdated = iLibraryUserService.updateLibraryUser(id,libraryUserDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(LibraryUserConstants.STATUS_200, LibraryUserConstants.LIBRARY_USER_UPDATED_MESSAGE));
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(LibraryUserConstants.STATUS_500, LibraryUserConstants.MESSAGE_500));
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<ResponseDto> deleteAccount(@PathVariable int id) {
        boolean isDeleted = iLibraryUserService.deleteLibraryUserById(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(LibraryUserConstants.STATUS_200, LibraryUserConstants.LIBRARY_USER_DELETED_MESSAGE));
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(LibraryUserConstants.STATUS_500, LibraryUserConstants.MESSAGE_500));
        }
    }
}
