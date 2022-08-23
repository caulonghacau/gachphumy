package com.vn.auth.data;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.vn.auth.model.ERole;
import com.vn.auth.model.Role;
import com.vn.auth.model.User;
import com.vn.auth.repository.RoleRepository;
import com.vn.auth.repository.UserRepository;
import com.vn.backend.model.Contact;
import com.vn.backend.model.Menu;
import com.vn.backend.model.Slice;
import com.vn.backend.repository.ContactRepository;
import com.vn.backend.repository.MenuRepository;
import com.vn.backend.repository.SliceRepository;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private SliceRepository sliceRepository;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		// Roles
		addRole();
		addUser();
		addMenu();
		addContact();
		addSlice();
	}

	private void addRole() {
		Optional<Role> optionalAdmin = roleRepository.findByName(ERole.ROLE_ADMIN);
		if (!optionalAdmin.isPresent()) {
			Role role = new Role(ERole.ROLE_ADMIN);
			roleRepository.save(role);
		}
		Optional<Role> optionalUser = roleRepository.findByName(ERole.ROLE_USER);
		if (!optionalUser.isPresent()) {
			Role role = new Role(ERole.ROLE_USER);
			roleRepository.save(role);
		}

	}

	private void addUser() {
		List<User> listUser = userRepository.findAll();
		// Admin account
		if (listUser == null || (listUser != null && listUser.size() == 0)) {
			User admin = new User();
			admin.setUsername("gachphumy");
			admin.setEmail("admin@gmail.com");
			admin.setPassword(passwordEncoder.encode("123456"));
			HashSet<Role> roles = new HashSet<>();
			Role roleUser = roleRepository.findByName(ERole.ROLE_USER).get();
			Role roleAdmin = roleRepository.findByName(ERole.ROLE_ADMIN).get();
			roles.add(roleUser);
			roles.add(roleAdmin);
			admin.setRoles(roles);
			userRepository.save(admin);
		}

	}

	private void addMenu() {
		List<Menu> list = menuRepository.findAll();
		if (list == null || (list != null && list.size() == 0)) {
			Menu menu = new Menu();
			menu.setName("Trang chủ");
			menu.setLink("/");
			menu.setPositon(1);
			menu.setDeleteFlag(0);
			menuRepository.save(menu);

			Menu menu1 = new Menu();
			menu1.setName("Sản Phẩm");
			menu1.setLink("/product");
			menu1.setPositon(10);
			menu1.setDeleteFlag(0);
			menuRepository.save(menu1);

			Menu menu2 = new Menu();
			menu2.setName("Về Chúng Tôi");
			menu2.setLink("/about");
			menu2.setPositon(20);
			menu2.setDeleteFlag(0);
			menuRepository.save(menu2);

			Menu menu3 = new Menu();
			menu3.setName("Liên Hệ");
			menu3.setLink("/contact");
			menu3.setPositon(30);
			menu3.setDeleteFlag(0);
			menuRepository.save(menu3);
		}

	}

	private void addContact() {
		List<Contact> list = contactRepository.findAll();
		if (list == null || (list != null && list.size() == 0)) {
			Contact contact = new Contact();
			contact.setName("Công Ty Cổ Phần Đầu Tư Gạch Phú Mỹ");
			contact.setEmail("ctydtgachphumy@gmail.com");
			contact.setPhone("0978 414 433");
			contact.setGoogelMap("");
//			"https://www.google.com/maps/embed?pb=!1m16!1m12!1m3!1d7843.73143489679!2d107.14137119728865!3d10.589670397755814!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!2m1!1zVGjDtG4gVMOibiBDaMOidSwgWMOjIENow6J1IFBoYSwgVGjhu4sgeMOjIFBow7ogTeG7uSwgVOG7iW5oIELDoCBS4buLYSAtIFbFqW5nIFTDoHUsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1661224233538!5m2!1svi!2s"
			contact.setAddress("Thôn Tân Châu, Xã Châu Pha, Thị xã Phú Mỹ, Tỉnh Bà Rịa - Vũng Tàu, Việt Nam");
			contact.setDeleteFlag(0);
			contactRepository.save(contact);

		}
	}

	private void addSlice() {
		List<Slice> list = sliceRepository.findAll();
		if (list == null || (list != null && list.size() == 0)) {
			Slice slice = new Slice();
			slice.setTitle("Gạch Phú Mỹ");
			slice.setDescription("Xây Vững niềm tin - Dựng uy tín vàng");
			slice.setImage("../img/Slice01.jpg");
			slice.setType(0);
			slice.setDeleteFlag(0);
			sliceRepository.save(slice);

			Slice slice1 = new Slice();
			slice1.setTitle("Gạch Phú Mỹ");
			slice1.setDescription("Xây Vững niềm tin - Dựng uy tín vàng");
			slice1.setImage("../img/Slice02.jpg");
			slice1.setType(0);
			slice1.setDeleteFlag(0);
			sliceRepository.save(slice1);

			Slice slice2 = new Slice();
			slice2.setTitle("Gạch Phú Mỹ");
			slice2.setDescription("Xây Vững niềm tin - Dựng uy tín vàng");
			slice2.setImage("../img/Slice03.jpg");
			slice2.setType(0);
			slice2.setDeleteFlag(0);
			sliceRepository.save(slice2);

			Slice slice3 = new Slice();
			slice3.setTitle("LUÔN CÓ SẴN SỐ LƯỢNG LỚN");
			slice3.setDescription("Cam kết chiết khấu cao nhất");
			slice3.setImage("../img/Slice05.jpg");
			slice3.setType(1);
			slice3.setDeleteFlag(0);
			sliceRepository.save(slice2);

			Slice slice4 = new Slice();
			slice4.setTitle("TƯ VẤN THI CÔNG MIỄN PHÍ");
			slice4.setDescription("Hỗ trợ gọi xe vận chuyển tận nơi");
			slice4.setImage("../img/Slice06.jpg");
			slice4.setType(1);
			slice4.setDeleteFlag(0);
			sliceRepository.save(slice4);

		}
	}
}
