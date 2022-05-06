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
@Table(name = "categories")
public class Category {

	//ID is unique and auto-generated; It is used as the Primary key
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 50, nullable = false, unique = true)
	private String name;

	/*
 	Source learning material:
		https://www.baeldung.com/spring-data-rest-relationships
		https://www.baeldung.com/jpa-many-to-many
		https://www.baeldung.com/hibernate-lazy-eager-loading
		https://www.baeldung.com/jpa-cascade-types

	Many to many  			- Many Categories can have Many Books;
	Fetch Type LAZY			- Object's initialization is defered for as long as possible;
	Cascade Type PERSIST	- The persist operation makes a transient instance persistent
	Cascade Type MERGE 		- The merge operation copies the state of the given object onto the persistent object with the same identifier
	MappedBy 				- This side is the Referencing side (non-owning)
	*/
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "categories")
	private Set<Book> books = new HashSet<Book>();

	//Constructor
	public Category(String name) {
		this.name = name;
	}

}
