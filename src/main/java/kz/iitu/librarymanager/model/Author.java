package kz.iitu.librarymanager.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "authors")
public class Author {

	//ID is unique and auto-generated; It is used as the Primary key
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 100, nullable = false,unique = true)
	private String name;

	@Column(name = "description", length = 250, nullable = false)
	private String description;

	/*
 	Source learning material:
		https://www.baeldung.com/spring-data-rest-relationships
		https://www.baeldung.com/jpa-many-to-many
		https://www.baeldung.com/hibernate-lazy-eager-loading
		https://www.baeldung.com/jpa-cascade-types

	Many to many  			- Many Authors can have Many Books;
	Fetch Type LAZY			- Object's initialization is defered for as long as possible;
	Cascade Type PERSIST 	- The persist operation makes a transient instance persistent
	Cascade Type MERGE 		- The merge operation copies the state of the given object onto the persistent object with the same identifier
	Cascade Type REMOVE 	- The remove operation removes the row corresponding to the entity from the database and also from the persistent context.
	MappedBy 				- This side is the Referencing side (non-owning)
	*/
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE , CascadeType.REMOVE}, mappedBy = "authors")
	private Set<Book> books = new HashSet<Book>();

	//Constructor
	public Author(String name, String description) {
		this.name = name;
		this.description = description;
	}

}
