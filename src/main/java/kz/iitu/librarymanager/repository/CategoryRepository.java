package kz.iitu.librarymanager.repository;

import kz.iitu.librarymanager.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
