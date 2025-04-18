package org.tus.libraryuser.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.tus.libraryuser.dto.CheckedBooksDto;
import org.tus.libraryuser.dto.LibraryUserDto;
import org.tus.libraryuser.entity.LibraryUser;
import org.tus.libraryuser.exception.ResourceAlreadyExistsException;
import org.tus.libraryuser.exception.ResourceNotFoundException;
import org.tus.libraryuser.mapper.LibraryUserMapper;
import org.tus.libraryuser.repository.LibraryUserRepository;
import org.tus.libraryuser.service.ILibraryUserService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LibraryUserServiceImpl implements ILibraryUserService {

    private LibraryUserRepository libraryUserRepository;
    //private CheckedBooksRepository checkedBooksRepository;

    @Override
    public void addLibraryUser(LibraryUserDto libraryUserDto) {
        LibraryUser libraryUser = LibraryUserMapper.mapToLibraryUser(libraryUserDto, new LibraryUser());
        Optional<LibraryUser> optionalLibraryUser = libraryUserRepository.findLibraryUserByUsername(libraryUserDto.getUsername());
        if (optionalLibraryUser.isPresent()) {
            throw new ResourceAlreadyExistsException("Customer already registered with given username "+libraryUserDto.getUsername());
        }
        libraryUserRepository.save(libraryUser);
    }

/*    @Override
    public CheckedBooksDto checkoutLibraryBook(int libraryUserId, String bookName, LibraryUserDto libraryUserDto) {
        libraryUserRepository.findByLibraryUserId(libraryUserId).orElseThrow(
                () -> new ResourceNotFoundException("LibraryUser","libraryUserId",String.valueOf(libraryUserId))
        );

        LibraryUser libraryUser = libraryUserRepository.findLibraryUserByUsername(libraryUserDto.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("LibraryUser","username",libraryUserDto.getUsername())
        );

        Books book = booksRepository.findBookByBookName(bookName).orElseThrow(
                () -> new ResourceNotFoundException("Book","bookName",bookName)
        );

        CheckedBooks checkedBook = new CheckedBooks();
        checkedBook.setBook_name(bookName);
        checkedBook.setLibraryUserId(libraryUser.getLibraryUserId());
        checkedBook.setCheckedStatus("ON_TIME");
        checkedBooksRepository.save(checkedBook);
        return mapToCheckedBooksDto(checkedBook, new CheckedBooksDto());
    }*/

    @Override
    public LibraryUserDto fetchUserById(int id, boolean isReversed) {
        LibraryUser libraryUser = libraryUserRepository.findByLibraryUserId(id).orElseThrow(
                () -> new ResourceNotFoundException("LibraryUser","libraryUserId",String.valueOf(id))
        );

/*        List<CheckedBooks> usersCheckBooks = checkedBooksRepository.findByLibraryUserId(libraryUser.getLibraryUserId());

        List<CheckedBooksDto> checkedBooksDtos;
        if (isReversed) {
            checkedBooksDtos = usersCheckBooks.stream()
                    .map(checkedBookRecord -> mapToCheckedBooksDto(checkedBookRecord, new CheckedBooksDto()))
                    .sorted(Comparator.comparing(CheckedBooksDto::getCheckoutDate).reversed())
                    .toList();
        }
        else{
            checkedBooksDtos = usersCheckBooks.stream()
                    .map(checkedBookRecord -> mapToCheckedBooksDto(checkedBookRecord, new CheckedBooksDto()))
                    .toList();
        }*/

        LibraryUserDto libraryUserDto = LibraryUserMapper.mapToLibraryUserDto(libraryUser, new LibraryUserDto());
        libraryUserDto.setLibrary_user_id(libraryUser.getLibraryUserId());
        //libraryUserDto.setCheckedBooks(checkedBooksDtos);
        return libraryUserDto;
    }

    @Override
    public LibraryUserDto fetchUserByUsername(String username) {

        LibraryUser libraryUser = libraryUserRepository.findLibraryUserByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("LibraryUser","username",username)
        );

/*        List<CheckedBooks> usersCheckBooks = checkedBooksRepository.findByLibraryUserId(libraryUser.getLibraryUserId());

        List<CheckedBooksDto> checkedBooksDtos = usersCheckBooks.stream()
                .map(checkedBookRecord -> mapToCheckedBooksDto(checkedBookRecord, new CheckedBooksDto()))
                .sorted(Comparator.comparing(CheckedBooksDto::getCheckoutDate).reversed())
                .toList();*/

        LibraryUserDto libraryUserDto = LibraryUserMapper.mapToLibraryUserDto(libraryUser, new LibraryUserDto());
        libraryUserDto.setLibrary_user_id(libraryUser.getLibraryUserId());
        //libraryUserDto.setCheckedBooks(checkedBooksDtos);
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

//        List<CheckedBooks> usersCheckBooks = checkedBooksRepository.findByLibraryUserId(libraryUser.getLibraryUserId());
//
//        if(!usersCheckBooks.isEmpty()) {
//            checkedBooksRepository.deleteAll(usersCheckBooks);
//        }

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
