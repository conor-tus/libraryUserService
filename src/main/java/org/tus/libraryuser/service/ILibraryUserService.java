package org.tus.libraryuser.service;

import org.tus.libraryuser.dto.LibraryUserDto;

public interface ILibraryUserService {
    void addLibraryUser(LibraryUserDto libraryUserDto);

    LibraryUserDto fetchUserByUsername(String username);

    LibraryUserDto fetchUserById(int id, boolean isReversed);

    boolean updateLibraryUser(int id, LibraryUserDto libraryUserDto);

    boolean deleteLibraryUserByUsername(String username);

    boolean deleteLibraryUserById(int id);
}
