package org.tus.libraryuser.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tus.libraryuser.dto.CheckedBooksDto;
import org.tus.libraryuser.dto.LibraryUserDto;
import org.tus.libraryuser.entity.LibraryUser;
import org.tus.libraryuser.exception.ResourceAlreadyExistsException;
import org.tus.libraryuser.exception.ResourceNotFoundException;
import org.tus.libraryuser.feign.BookInterface;
import org.tus.libraryuser.feign.CheckedBookInterface;
import org.tus.libraryuser.mapper.LibraryUserMapper;
import org.tus.libraryuser.repository.LibraryUserRepository;
import org.tus.libraryuser.service.ILibraryUserService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LibraryUserServiceImpl implements ILibraryUserService {

    private LibraryUserRepository libraryUserRepository;

    @Autowired
    BookInterface bookInterface;

    @Autowired
    CheckedBookInterface checkedBookInterface;

    @Override
    public void addLibraryUser(LibraryUserDto libraryUserDto) {
        LibraryUser libraryUser = LibraryUserMapper.mapToLibraryUser(libraryUserDto, new LibraryUser());
        Optional<LibraryUser> optionalLibraryUser = libraryUserRepository.findLibraryUserByUsername(libraryUserDto.getUsername());
        if (optionalLibraryUser.isPresent()) {
            throw new ResourceAlreadyExistsException("Customer already registered with given username "+libraryUserDto.getUsername());
        }
        libraryUserRepository.save(libraryUser);
    }


    @Override
    public LibraryUserDto fetchUserById(int id, boolean isReversed) {
        LibraryUser libraryUser = libraryUserRepository.findByLibraryUserId(id).orElseThrow(
                () -> new ResourceNotFoundException("LibraryUser","libraryUserId",String.valueOf(id))
        );
        ResponseEntity<List<CheckedBooksDto>> response = checkedBookInterface.getCheckedBooks(id);
        List<CheckedBooksDto> userCheckedBooksDto = new ArrayList<>();
        if(response.getStatusCode().is4xxClientError())
        {
            userCheckedBooksDto = null;
        }
        else{
            userCheckedBooksDto = response.getBody();
        }

        LibraryUserDto libraryUserDto = LibraryUserMapper.mapToLibraryUserDto(libraryUser, new LibraryUserDto());
        libraryUserDto.setLibrary_user_id(libraryUser.getLibraryUserId());
        libraryUserDto.setCheckedBooks(userCheckedBooksDto);
        return libraryUserDto;
    }

    @Override
    public LibraryUserDto fetchUserByUsername(String username) {

        LibraryUser libraryUser = libraryUserRepository.findLibraryUserByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("LibraryUser","username",username)
        );

        List<CheckedBooksDto> userCheckedBooksDto = checkedBookInterface.getCheckedBooks(libraryUser.getLibraryUserId()).getBody();


        LibraryUserDto libraryUserDto = LibraryUserMapper.mapToLibraryUserDto(libraryUser, new LibraryUserDto());
        libraryUserDto.setLibrary_user_id(libraryUser.getLibraryUserId());
        libraryUserDto.setCheckedBooks(userCheckedBooksDto);
        return libraryUserDto;
    }

    @Override
    public boolean updateLibraryUser(int id, LibraryUserDto libraryUserDto) {
        boolean isUpdated = false;
        if(libraryUserDto != null) {
            LibraryUser libraryUser = libraryUserRepository.findByLibraryUserId(id).orElseThrow(
                    () -> new ResourceNotFoundException("LibraryUser","username",libraryUserDto.getUsername())
            );
            LibraryUserMapper.mapToLibraryUser(libraryUserDto, libraryUser);
            libraryUserRepository.save(libraryUser);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteLibraryUserById(int id) {
        LibraryUser libraryUser = libraryUserRepository.findByLibraryUserId(id).orElseThrow(
                () -> new ResourceNotFoundException("LibraryUser","libraryUserId",String.valueOf(id))
        );

        List<CheckedBooksDto> userCheckedBooksDto = checkedBookInterface.getCheckedBooks(id).getBody();

        if(!userCheckedBooksDto.isEmpty()) {
            for(CheckedBooksDto record: userCheckedBooksDto)
                checkedBookInterface.deleteCheckoutBook(record.getLibraryUserId(), record.getCheckedBookId());
        }

        libraryUserRepository.delete(libraryUser);
        return true;
    }

    @Override
    public boolean deleteLibraryUserByUsername(String username) {
        LibraryUser libraryUser = libraryUserRepository.findLibraryUserByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("LibraryUser","username",username)
        );
        libraryUserRepository.delete(libraryUser);
        return true;
    }
}
