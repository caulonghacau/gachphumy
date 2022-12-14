package com.vn.auth.data;

import java.math.BigDecimal;
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
import com.vn.backend.model.Advantage;
import com.vn.backend.model.Category;
import com.vn.backend.model.Contact;
import com.vn.backend.model.Menu;
import com.vn.backend.model.Product;
import com.vn.backend.model.Slice;
import com.vn.backend.model.Vendor;
import com.vn.backend.repository.AdvantageRepository;
import com.vn.backend.repository.CategoryRepository;
import com.vn.backend.repository.ContactRepository;
import com.vn.backend.repository.MenuRepository;
import com.vn.backend.repository.ProductRepository;
import com.vn.backend.repository.SliceRepository;
import com.vn.backend.repository.VendorRepository;

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

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private AdvantageRepository advantageRepository;

	@Autowired
	private VendorRepository vendorRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		// Roles
		addRole();
		addUser();
		addMenu();
		addContact();
		addSlice();
		addCategory();
		addAdvantage();
		addVendor();
		addProduct();
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
			menu.setName("Trang ch???");
			menu.setLink("/");
			menu.setPositon(1);
			menu.setDeleteFlag(0);
			menuRepository.save(menu);

			Menu menu1 = new Menu();
			menu1.setName("S???n Ph???m");
			menu1.setLink("/product");
			menu1.setPositon(10);
			menu1.setDeleteFlag(0);
			menuRepository.save(menu1);

			Menu menu2 = new Menu();
			menu2.setName("V??? Ch??ng T??i");
			menu2.setLink("/about");
			menu2.setPositon(20);
			menu2.setDeleteFlag(0);
			menuRepository.save(menu2);

			Menu menu3 = new Menu();
			menu3.setName("Li??n H???");
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
			contact.setName("C??ng Ty C??? Ph???n ?????u T?? G???ch Ph?? M???");
			contact.setEmail("ctydtgachphumy@gmail.com");
			contact.setPhone("0978 414 433");
			contact.setGoogleMap("");
			// "https://www.google.com/maps/embed?pb=!1m16!1m12!1m3!1d7843.73143489679!2d107.14137119728865!3d10.589670397755814!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!2m1!1zVGjDtG4gVMOibiBDaMOidSwgWMOjIENow6J1IFBoYSwgVGjhu4sgeMOjIFBow7ogTeG7uSwgVOG7iW5oIELDoCBS4buLYSAtIFbFqW5nIFTDoHUsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1661224233538!5m2!1svi!2s"
			contact.setAddress("Th??n T??n Ch??u, X?? Ch??u Pha, Th??? x?? Ph?? M???, T???nh B?? R???a - V??ng T??u, Vi???t Nam");
			contact.setDeleteFlag(0);
			contactRepository.save(contact);

		}
	}

	private void addSlice() {
		List<Slice> list = sliceRepository.findAll();
		if (list == null || (list != null && list.size() == 0)) {
			Slice slice = new Slice();
			slice.setTitle("G???ch Ph?? M???");
			slice.setDescription("X??y V???ng ni???m tin - D???ng uy t??n v??ng");
			slice.setImage("/images/Slice01.jpg");
			slice.setPosition(1);
			slice.setDeleteFlag(0);
			sliceRepository.save(slice);

			Slice slice1 = new Slice();
			slice1.setTitle("G???ch Ph?? M???");
			slice1.setDescription("X??y V???ng ni???m tin - D???ng uy t??n v??ng");
			slice1.setImage("/images/Slice02.jpg");
			slice.setPosition(1);
			slice1.setDeleteFlag(0);
			sliceRepository.save(slice1);

			Slice slice2 = new Slice();
			slice2.setTitle("G???ch Ph?? M???");
			slice2.setDescription("X??y V???ng ni???m tin - D???ng uy t??n v??ng");
			slice2.setImage("/images/Slice03.jpg");
			slice.setPosition(1);
			slice2.setDeleteFlag(0);
			sliceRepository.save(slice2);

			Slice slice3 = new Slice();
			slice3.setTitle("LU??N C?? S???N S??? L?????NG L???N");
			slice3.setDescription("Cam k???t chi???t kh???u cao nh???t");
			slice3.setImage("/images/Slice05.jpg");
			slice.setPosition(2);
			slice3.setDeleteFlag(0);
			sliceRepository.save(slice2);

			Slice slice4 = new Slice();
			slice4.setTitle("T?? V???N THI C??NG MI???N PH??");
			slice4.setDescription("H??? tr??? g???i xe v???n chuy???n t???n n??i");
			slice4.setImage("/images/Slice06.jpg");
			slice.setPosition(2);
			slice4.setDeleteFlag(0);
			sliceRepository.save(slice4);

		}
	}

	private void addCategory() {
		List<Category> list = categoryRepository.findAll();
		if (list == null || (list != null && list.size() == 0)) {
			Category category = new Category();
			category.setName("G???ch Kh??ng Nung");
			category.setDeleteFlag(0);
			categoryRepository.save(category);

			Category category1 = new Category();
			category1.setName("V???t Li???u X??y D???ng");
			category1.setDeleteFlag(0);
			categoryRepository.save(category1);

			Category category2 = new Category();
			category2.setName("S???n Ph???m Kh??c");
			category2.setDeleteFlag(0);
			categoryRepository.save(category2);
		}

	}

	private void addAdvantage() {
		List<Advantage> list = advantageRepository.findAll();
		if (list == null || (list != null && list.size() == 0)) {
			Advantage advantage = new Advantage();
			advantage.setName("SI??U C??CH ??M");
			advantage.setDescription(
					"Kh??? n??ng c??ch ??m t??? 40db - 47db, g???ch b?? t??ng kh?? ch??ng ??p c??ch ??m t???t g???p 2 l???n g???ch x??y th??ng th?????ng.");
			advantage.setDeleteFlag(0);
			advantageRepository.save(advantage);

			Advantage advantage1 = new Advantage();
			advantage1.setName("SI??U C??CH NHI???T");
			advantage1.setDescription(
					"B???ng 1/4 - 1/5 so v???i g???ch nung, 1/6 g???ch b?? t??ng th??ng th?????ng v?? gi???m t???i 40% chi ph?? ??i???n n??ng ti??u th???");
			advantage1.setDeleteFlag(0);
			advantageRepository.save(advantage1);

			Advantage advantage2 = new Advantage();
			advantage2.setName("T??? TR???NG NH???");
			advantage2.setDescription(
					"T??? tr???ng t????ng ??????ng 1/2 g???ch ?????c, 2/3 g???ch r???ng 2 l??? gi??p gi???m k???t c???u m??ng, d???m, c???t, gi???m chi ph?? x??y th?? t??? 10 ?????n 12%.");
			advantage2.setDeleteFlag(0);
			advantageRepository.save(advantage2);

			Advantage advantage3 = new Advantage();
			advantage3.setName("CH???NG CH??Y HI???U QU???");
			advantage3.setDescription(
					"T??nh n??ng ch???ng ch??y  ?????t ti??u chu???n c???p 1  Qu???c Gia gi??p ng??n ng???a ch??y lan v?? kh??? n??ng ch???ng ch??y t??? 4 - 6 gi??? ?????ng h???.");
			advantage3.setDeleteFlag(0);
			advantageRepository.save(advantage3);

			Advantage advantage4 = new Advantage();
			advantage4.setName("CH???NG CH???N ?????NG");
			advantage4.setDescription(
					"Tr???ng l?????ng g???ch v?? t???m b??  t??ng kh?? th???p n??n gi???m tr???ng l???c ng??i nh?? l??n m???t ?????t. S???n ph???m ???????c ???ng d???ng ph??? bi???n t???i Nh???t B???n");
			advantage4.setDeleteFlag(0);
			advantageRepository.save(advantage4);

			Advantage advantage5 = new Advantage();
			advantage5.setName("THI C??NG NHANH");
			advantage5.setDescription(
					"D??? d??ng khoan, c???t, kh??ng c???n tr??t v???a... s???n ph???m b?? t??ng kh?? ???????c ????nh gi?? nhanh h??n nh?? truy???n th???ng 50%.");
			advantage5.setDeleteFlag(0);
			advantageRepository.save(advantage5);

			Advantage advantage6 = new Advantage();
			advantage6.setName("TH??N THI???N M??I TR?????NG");
			advantage6.setDescription(
					"C??ng ngh??? s???n xu???t kh??ng nung ?????t, h???n ch??? t???i ??a ph??t sinh kh?? th???i, gi???m hi???u ???ng nh?? k??nh.");
			advantage6.setDeleteFlag(0);
			advantageRepository.save(advantage6);

			Advantage advantage7 = new Advantage();
			advantage7.setName("K??CH TH?????C LINH HO???T");
			advantage7.setDescription(
					"Linh ho???t v??? chi???u d??i v?? chi???u d??y c???a t???m gi??p d??? d??ng x??? l?? c??c k??? thu???t thi c??ng. ");
			advantage7.setDeleteFlag(0);
			advantageRepository.save(advantage7);
		}

	}

	private void addVendor() {
		List<Vendor> list = vendorRepository.findAll();
		if (list == null || (list != null && list.size() == 0)) {
			Vendor vendor = new Vendor();
			vendor.setName("C??ng Ty X??y D???ng Xu??n Th??nh");
			vendor.setImage("/frontend/img/vendor-1.jpg");
			vendor.setDeleteFlag(0);
			vendorRepository.save(vendor);

			Vendor vendor1 = new Vendor();
			vendor1.setName("C??ng Ty X??y D???ng Ph????ng Nam");
			vendor1.setImage("/frontend/img/vendor-2.jpg");
			vendor1.setDeleteFlag(0);
			vendorRepository.save(vendor1);

			Vendor vendor2 = new Vendor();
			vendor2.setName("C??ng Ty X??y D???ng Th??nh ?????t");
			vendor2.setImage("/frontend/img/vendor-3.jpg");
			vendor2.setDeleteFlag(0);
			vendorRepository.save(vendor2);

			Vendor vendor3 = new Vendor();
			vendor3.setName("C??ng Ty X??y D???ng B???o T??n");
			vendor3.setImage("/frontend/img/vendor-4.jpg");
			vendor3.setDeleteFlag(0);
			vendorRepository.save(vendor3);

			Vendor vendor4 = new Vendor();
			vendor4.setName("C??ng Ty X??y D???ng Xu??n Th??nh");
			vendor4.setImage("/frontend/img/vendor-5.jpg");
			vendor4.setDeleteFlag(0);
			vendorRepository.save(vendor4);

			Vendor vendor5 = new Vendor();
			vendor5.setName("C??ng Ty X??y D???ng Ph????ng Nam");
			vendor5.setImage("/frontend/img/vendor-6.jpg");
			vendor5.setDeleteFlag(0);
			vendorRepository.save(vendor5);

			Vendor vendor6 = new Vendor();
			vendor6.setName("C??ng Ty X??y D???ng Th??nh ?????t");
			vendor6.setImage("/frontend/img/vendor-7.jpg");
			vendor6.setDeleteFlag(0);
			vendorRepository.save(vendor6);

			Vendor vendor7 = new Vendor();
			vendor7.setName("C??ng Ty X??y D???ng B???o T??n");
			vendor7.setImage("/frontend/img/vendor-8.jpg");
			vendor7.setDeleteFlag(0);
			vendorRepository.save(vendor7);

		}

	}

	private void addProduct() {
		List<Product> list = productRepository.findAll();
		BigDecimal BIG_5 = new BigDecimal("5000");
		if (list == null || (list != null && list.size() == 0)) {
			Product product = new Product();
			product.setName("G???ch xi m??ng c???t li???u");
			product.setImage("/uploads/product01.jpg");
			product.setCategoryId(1L);
			product.setProductCode("SKU01");
			product.setAmount(80000);
			product.setPrice(BIG_5);
			product.setNewProduct("1");
			product.setIngredient("???? xay, b???t ????, xi m??ng, ph??? gia");
			product.setStandard("ISO 9001");
			product.setSpecifications("C?????n ????? ch??? n??n 55");
			product.setSize("200 x 10 x 30");
			product.setWeight("2.0 kg/vi??n");
			product.setDescription1("");
			product.setDescription2("");
			product.setDeleteFlag(0);
			productRepository.save(product);

			Product product1 = new Product();
			product1.setName("G???ch babanh");
			product1.setImage("/uploads/product02.jpg");
			product1.setCategoryId(1L);
			product1.setProductCode("SKU02");
			product1.setAmount(80000);
			product1.setPrice(BIG_5);
			product1.setNewProduct("1");
			product1.setIngredient("???? xay, b???t ????, xi m??ng, ph??? gia");
			product1.setStandard("ISO 9001");
			product1.setSpecifications("C?????n ????? ch??? n??n 55");
			product1.setSize("200 x 10 x 30");
			product1.setWeight("2.0 kg/vi??n");
			product1.setDescription1("");
			product1.setDescription2("");
			product1.setDeleteFlag(0);
			productRepository.save(product1);

			Product product2 = new Product();
			product2.setName("G???ch kh??ng nung t??? nhi??n");
			product2.setImage("/uploads/product03.jpg");
			product2.setCategoryId(1L);
			product2.setProductCode("SKU03");
			product2.setAmount(80000);
			product2.setPrice(BIG_5);
			product2.setNewProduct("1");
			product2.setIngredient("???? xay, b???t ????, xi m??ng, ph??? gia");
			product2.setStandard("ISO 9001");
			product2.setSpecifications("C?????n ????? ch??? n??n 55");
			product2.setSize("200 x 10 x 30");
			product2.setWeight("2.0 kg/vi??n");
			product2.setDescription1("");
			product2.setDescription2("");
			product2.setDeleteFlag(0);
			productRepository.save(product2);

			Product product3 = new Product();
			product3.setName("G???ch b?? t??ng nh???");
			product3.setImage("/uploads/product04.jpg");
			product3.setCategoryId(1L);
			product3.setProductCode("SKU04");
			product3.setAmount(80000);
			product3.setPrice(BIG_5);
			product3.setNewProduct("1");
			product3.setIngredient("???? xay, b???t ????, xi m??ng, ph??? gia");
			product3.setStandard("ISO 9001");
			product3.setSpecifications("C?????n ????? ch??? n??n 55");
			product3.setSize("200 x 10 x 30");
			product3.setWeight("2.0 kg/vi??n");
			product3.setDescription1("");
			product3.setDescription2("");
			product3.setDeleteFlag(0);
			productRepository.save(product3);

			Product product4 = new Product();
			product4.setName("G???ch kh??ng nung r???ng 3 th??nh v??ch MT-KM100V3S");
			product4.setImage("/uploads/product05.jpg");
			product4.setCategoryId(2L);
			product4.setProductCode("SKU05");
			product4.setAmount(80000);
			product4.setPrice(BIG_5);
			product4.setNewProduct("1");
			product4.setIngredient("???? xay, b???t ????, xi m??ng, ph??? gia");
			product4.setStandard("ISO 9001");
			product4.setSpecifications("C?????n ????? ch??? n??n 55");
			product4.setSize("200 x 95 x 60");
			product4.setWeight("2.0 kg/vi??n");
			product4.setDescription1("");
			product4.setDescription2("");
			product4.setDeleteFlag(0);
			productRepository.save(product4);

			Product product5 = new Product();
			product5.setName("G???ch ?????c kh??ng nung MT-KM95DA");
			product5.setImage("/uploads/product06.jpg");
			product5.setCategoryId(2L);
			product5.setProductCode("SKU06");
			product5.setAmount(80000);
			product5.setPrice(BIG_5);
			product5.setNewProduct("1");
			product5.setIngredient("???? xay, b???t ????, xi m??ng, ph??? gia");
			product5.setStandard("ISO 9001");
			product5.setSpecifications("C?????n ????? ch??? n??n 55");
			product5.setSize("200 x 95 x 60 mm");
			product5.setWeight("2.0 kg/vi??n");
			product5.setDescription1("");
			product5.setDescription2("");
			product5.setDeleteFlag(0);
			productRepository.save(product5);

			Product product6 = new Product();
			product6.setName("G???ch kh??ng nung t??? nhi??n");
			product6.setImage("/uploads/product07.jpg");
			product6.setCategoryId(2L);
			product6.setProductCode("SKU07");
			product6.setAmount(80000);
			product6.setPrice(BIG_5);
			product6.setNewProduct("1");
			product6.setIngredient("???? xay, b???t ????, xi m??ng, ph??? gia");
			product6.setStandard("ISO 9001");
			product6.setSpecifications("C?????n ????? ch??? n??n 55");
			product6.setSize("200 x 10 x 30");
			product6.setWeight("2.0 kg/vi??n");
			product6.setDescription1("");
			product6.setDescription2("");
			product6.setDeleteFlag(0);
			productRepository.save(product6);

			Product product7 = new Product();
			product7.setName("G???ch b?? t??ng nh???");
			product7.setImage("/uploads/product08.jpg");
			product7.setCategoryId(2L);
			product7.setProductCode("SKU08");
			product7.setAmount(80000);
			product7.setPrice(BIG_5);
			product7.setNewProduct("1");
			product7.setIngredient("???? xay, b???t ????, xi m??ng, ph??? gia");
			product7.setStandard("ISO 9001");
			product7.setSpecifications("C?????n ????? ch??? n??n 55");
			product7.setSize("200 x 10 x 30");
			product7.setWeight("2.0 kg/vi??n");
			product7.setDescription1("");
			product7.setDescription2("");
			product7.setDeleteFlag(0);
			productRepository.save(product7);

		}

	}

}
