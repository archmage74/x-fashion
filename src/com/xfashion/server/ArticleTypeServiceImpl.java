package com.xfashion.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.xfashion.client.ArticleTypeService;
import com.xfashion.shared.BrandDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.StyleDTO;

public class ArticleTypeServiceImpl extends RemoteServiceServlet implements ArticleTypeService {

	private static final long serialVersionUID = 1L;

	public static final String[] CATEGORIES = { "Damenhose", "Herrenhose", "Damenoberteil", "Herrenoberteil", "Kleider", "Strumpfwaren", "Gürtel",
			"Bademode", "Accessoirs" };

	@Override
	public void createCategories() throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			createCategories(pm);
		} finally {
			pm.close();
		}
	}

	private void createCategories(PersistenceManager pm) {
		deleteCategories(pm);
		for (String c : CATEGORIES) {
			Category category = new Category();
			category.setName(c);
			pm.makePersistent(category);
		}
	}
	
	private void deleteCategories(PersistenceManager pm) {
		List<Category> all = readCategories(pm);
		pm.deletePersistentAll(all);
		pm.flush();
	}
	
	@Override
	public List<CategoryDTO> readCategories() throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<CategoryDTO> categories = new ArrayList<CategoryDTO>(); 
		try {
			List<Category> cats = readCategories(pm);
			for (Category c : cats) {
				CategoryDTO dto = new CategoryDTO();
				dto.setName(c.getName());
				categories.add(dto);
			}
		} finally {
			pm.close();
		}
		return categories;
	}
	
	@SuppressWarnings("unchecked")
	private List<Category> readCategories(PersistenceManager pm) {
		Query query = pm.newQuery(Category.class);
		List<Category> categories = (List<Category>) query.execute();
		return categories;
	}
	
	
	@Override
	public void saveCategory(CategoryDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Category category = readCategory(pm, dto.getName());
			category.setStyles(dto.getStyles());
			category.setBrands(dto.getBrands());
		} finally {
			pm.close();
		}
	}

	private Category readCategory(PersistenceManager pm, String name) {
		Category category = pm.getObjectById(Category.class, name);
		return category;
	}

	@Override
	public List<StyleDTO> readStyles() throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<StyleDTO> styles = new ArrayList<StyleDTO>(); 
		try {
			List<Style> stls = readStyles(pm);
			for (Style s : stls) {
				StyleDTO dto = new StyleDTO();
				dto.setName(s.getName());
				styles.add(dto);
			}
		} finally {
			pm.close();
		}
		return styles;
	}
	
	@SuppressWarnings("unchecked")
	private List<Style> readStyles(PersistenceManager pm) {
		Query query = pm.newQuery(Style.class);
		List<Style> styles = (List<Style>) query.execute();
		return styles;
	}

	@Override
	public void addStyle(StyleDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Style style = new Style();
			style.setName(dto.getName());
			pm.makePersistent(style);
		} finally {
			pm.close();
		}
	}

	@Override
	public List<BrandDTO> readBrands() throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<BrandDTO> brands = new ArrayList<BrandDTO>(); 
		try {
			List<Brand> brds = readBrands(pm);
			for (Brand s : brds) {
				BrandDTO dto = new BrandDTO();
				dto.setName(s.getName());
				brands.add(dto);
			}
		} finally {
			pm.close();
		}
		return brands;
	}

	@SuppressWarnings("unchecked")
	private List<Brand> readBrands(PersistenceManager pm) {
		Query query = pm.newQuery(Brand.class);
		List<Brand> brands = (List<Brand>) query.execute();
		return brands;
	}

	@Override
	public void addBrand(BrandDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Brand brand = new Brand();
			brand.setName(dto.getName());
			pm.makePersistent(brand);
		} finally {
			pm.close();
		}
	}

}
