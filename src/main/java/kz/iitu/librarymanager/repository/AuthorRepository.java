package kz.iitu.librarymanager.repository;

import kz.iitu.librarymanager.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
