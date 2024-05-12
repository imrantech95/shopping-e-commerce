package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private UserRepository repo;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);

		User userName = new User();
		userName.setEmail("imran@gmail.com");
		userName.setPassword("aiuser");
		userName.setFirst_name("Imran");
		userName.setLast_name("Ahmad");

		userName.addRole(roleAdmin);
		User savedUser = repo.save(userName);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateNewUserWithTwoRole() {
		User userRole = new User("arqam@gmail.com", "abcde", "Mohd", "Arqam");

		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);

		userRole.addRole(roleEditor);
		userRole.addRole(roleAssistant);
		User savedUser = repo.save(userRole);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testAllUsersRoles() {
		Iterable<User> listuser = repo.findAll();
		listuser.forEach(user -> System.out.println(user));
	}

	@Test
	public void testGetUserFindById() {
		User usern = repo.findById(1).get();
		System.out.println(usern);
		assertThat(usern).isNotNull();
	}

	@Test
	public void testUpdateUserDetails() {
		User userna = repo.findById(5).get();
		userna.setEnabled(true);
		userna.setEmail("ait@gmail.com");

		repo.save(userna);
	}

	@Test
	public void testUpdateRole() {
		User userImran = repo.findById(3).get();
		Role roleEditor = new Role(3);
		Role saleperson = new Role(2);

		userImran.getRoles().remove(roleEditor);
		userImran.addRole(saleperson);

		repo.save(userImran);
	}

	@Test
	public void testDeleteUser() {
		Integer userId = 3;
		repo.deleteById(userId);
	}
}
