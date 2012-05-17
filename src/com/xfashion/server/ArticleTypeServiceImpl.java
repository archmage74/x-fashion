package com.xfashion.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.xfashion.client.db.ArticleTypeService;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.ColorDTO;
import com.xfashion.shared.SizeDTO;
import com.xfashion.shared.StyleDTO;

public class ArticleTypeServiceImpl extends RemoteServiceServlet implements ArticleTypeService {

	private static final long serialVersionUID = 1L;

	// *************
	// categories
	// *************
	@Override
	public void createCategories() throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			deleteCategories(pm);
			createCategory(pm, new CategoryDTO(1L, "Damenhose", 0, "#76A573", "#466944"));
			createCategory(pm, new CategoryDTO(2L, "Herrenhose", 1, "#6F77AA", "#2B3781"));
			createCategory(pm, new CategoryDTO(3L, "Damenoberteil", 2, "#9EC1AA", "#74A886"));
			createCategory(pm, new CategoryDTO(4L, "Herrenoberteil", 3, "#B2B7D9", "#919BCA"));
			createCategory(pm, new CategoryDTO(5L, "Kleider", 4, "#CEAFD0", "#B98DBC"));
			createCategory(pm, new CategoryDTO(6L, "Strumpfwaren", 5, "#C1AEA5", "#A78C7E"));
			createCategory(pm, new CategoryDTO(7L, "Gürtel", 6, "#9F7F79", "#71433A"));
			createCategory(pm, new CategoryDTO(8L, "Bademode", 7, "#8AADB8", "#578C9B"));
			createCategory(pm, new CategoryDTO(9L, "Accessoirs", 8, "#A26E7B", "#7C3044"));
		} finally {
			pm.close();
		}
	}

	@Override
	public CategoryDTO createCategory(CategoryDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Category category = createCategory(pm, dto);
			dto.setId(category.getId());
		} finally {
			pm.close();
		}
		return dto;
	}

	private Category createCategory(PersistenceManager pm, CategoryDTO dto) {
		Category c = new Category(dto);
		return pm.makePersistent(c);
	}

	@Override
	public CategoryDTO readCategory(Long id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		CategoryDTO dto = null;
		try {
			Category category = readCategory(pm, id);
			dto = category.createDTO();
		} finally {
			pm.close();
		}
		return dto;
	}

	private Category readCategory(PersistenceManager pm, Long id) {
		Category category = pm.getObjectById(Category.class, id);
		return category;
	}

	@Override
	public List<CategoryDTO> readCategories() throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<CategoryDTO> categories = new ArrayList<CategoryDTO>();
		try {
			List<Category> cats = readCategories(pm);
			for (Category c : cats) {
				CategoryDTO dto = c.createDTO();
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
	public CategoryDTO updateCategory(CategoryDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Category category = updateCategory(pm, dto);
			dto = category.createDTO();
		} finally {
			pm.close();
		}
		return dto;
	}

	private Category updateCategory(PersistenceManager pm, CategoryDTO dto) {
		Category category = readCategory(pm, dto.getId());
		category.updateFromDTO(dto);
		return pm.makePersistent(category);
	}

	private void deleteCategories(PersistenceManager pm) {
		List<Category> all = readCategories(pm);
		pm.deletePersistentAll(all);
		pm.flush();
	}

	@Override
	public void deleteCategory(CategoryDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			deleteCategory(pm, dto);
		} finally {
			pm.close();
		}
	}

	private void deleteCategory(PersistenceManager pm, CategoryDTO dto) {
		Category category = readCategory(pm, dto.getId());
		pm.deletePersistent(category);
	}

	// *************
	// styles
	// *************
	@Override
	public StyleDTO createStyle(StyleDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Style style = createStyle(pm, dto);
			dto.setId(style.getKeyString());
		} finally {
			pm.close();
		}
		return dto;
	}

	private Style createStyle(PersistenceManager pm, StyleDTO dto) {
		Style style = new Style(dto);
		return pm.makePersistent(style);
	}

	@Override
	public StyleDTO readStyle(String keyString) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		StyleDTO dto = null;
		try {
			Style style = readStyle(pm, keyString);
			dto = style.createDTO();
		} finally {
			pm.close();
		}
		return dto;
	}

	private Style readStyle(PersistenceManager pm, String keyString) {
		Style item = pm.getObjectById(Style.class, KeyFactory.stringToKey(keyString));
		return item;
	}

	@Override
	public List<StyleDTO> readStyles() throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<StyleDTO> styles = new ArrayList<StyleDTO>();
		try {
			List<Style> stls = readStyles(pm);
			for (Style o : stls) {
				StyleDTO dto = o.createDTO();
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
	public void updateStyle(StyleDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			updateStyle(pm, dto);
		} finally {
			pm.close();
		}
	}

	private void updateStyle(PersistenceManager pm, StyleDTO dto) {
		Style item = readStyle(pm, dto.getId());
		item.updateFromDTO(dto);
		pm.makePersistent(item);
	}

	@Override
	public void deleteStyle(StyleDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			deleteStyle(pm, dto);
		} finally {
			pm.close();
		}
	}

	private void deleteStyle(PersistenceManager pm, StyleDTO dto) {
		Style item = readStyle(pm, dto.getId());
		pm.deletePersistent(item);
	}

	// *************
	// brands
	// *************
	@Override
	public BrandDTO createBrand(BrandDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Brand brand = createBrand(pm, dto);
			dto.setId(brand.getId());
		} finally {
			pm.close();
		}
		return dto;
	}

	private Brand createBrand(PersistenceManager pm, BrandDTO dto) {
		Brand brand = new Brand(dto);
		return pm.makePersistent(brand);
	}

	@Override
	public BrandDTO readBrand(Long id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		BrandDTO dto = null;
		try {
			Brand brand = readBrand(pm, id);
			dto = brand.createDTO();
		} finally {
			pm.close();
		}
		return dto;
	}

	private Brand readBrand(PersistenceManager pm, Long id) {
		Brand item = pm.getObjectById(Brand.class, id);
		return item;
	}

	@Override
	public List<BrandDTO> readBrands() throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<BrandDTO> brands = new ArrayList<BrandDTO>();
		try {
			List<Brand> brds = readBrands(pm);
			for (Brand o : brds) {
				BrandDTO dto = o.createDTO();
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
	public void updateBrand(BrandDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			updateBrand(pm, dto);
		} finally {
			pm.close();
		}
	}

	private void updateBrand(PersistenceManager pm, BrandDTO dto) {
		Brand item = readBrand(pm, dto.getId());
		item.updateFromDTO(dto);
		pm.makePersistent(item);
	}

	@Override
	public void deleteBrand(BrandDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			deleteBrand(pm, dto);
		} finally {
			pm.close();
		}
	}

	private void deleteBrand(PersistenceManager pm, BrandDTO dto) {
		Brand item = readBrand(pm, dto.getId());
		pm.deletePersistent(item);
	}

	// *************
	// sizes
	// *************
	@Override
	public SizeDTO readSize(String keyString) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		SizeDTO dto = null;
		try {
			Size size = readSize(pm, keyString);
			dto = size.createDTO();
		} finally {
			pm.close();
		}
		return dto;
	}

	private Size readSize(PersistenceManager pm, String keyString) {
		Size item = pm.getObjectById(Size.class, KeyFactory.stringToKey(keyString));
		return item;
	}

	@Override
	public List<SizeDTO> readSizes() throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<SizeDTO> dtos;
		try {
			Sizes szs = readSizes(pm);
			dtos = szs.getDtos();
		} finally {
			pm.close();
		}
		return dtos;
	}

	@SuppressWarnings("unchecked")
	private Sizes readSizes(PersistenceManager pm) {
		Query query = pm.newQuery(Sizes.class);
		Sizes szs;
		List<Sizes> sizes = (List<Sizes>) query.execute();
		if (sizes.size() == 0) {
			Sizes s = new Sizes();
			szs = pm.makePersistent(s);
		} else {
			szs = sizes.get(0);
		}
		return szs;
	}

	@Override
	public void updateSize(SizeDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			updateSize(pm, dto);
		} finally {
			pm.close();
		}
	}

	private void updateSize(PersistenceManager pm, SizeDTO dto) {
		Size item = readSize(pm, dto.getKey());
		item.updateFromDTO(dto);
		pm.makePersistent(item);
	}

	@Override
	public List<SizeDTO> updateSizes(List<SizeDTO> dtos) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			updateSizes(pm, dtos);
		} finally {
			pm.close();
		}
		return readSizes();
	}

	public void updateSizes(PersistenceManager pm, List<SizeDTO> dtos) {
		Sizes sizes = readSizes(pm);
		sizes.update(dtos);
		pm.makePersistent(sizes);
	}

	@Override
	public void deleteSize(SizeDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			deleteSize(pm, dto);
		} finally {
			pm.close();
		}
	}

	private void deleteSize(PersistenceManager pm, SizeDTO dto) {
		Size item = readSize(pm, dto.getKey());
		pm.deletePersistent(item);
	}

	// *************
	// colors
	// *************
	@Override
	public ColorDTO createColor(ColorDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Color color = createColor(pm, dto);
			dto.setId(color.getId());
		} finally {
			pm.close();
		}
		return dto;
	}

	private Color createColor(PersistenceManager pm, ColorDTO dto) {
		Color color = new Color(dto);
		return pm.makePersistent(color);
	}

	@Override
	public ColorDTO readColor(Long id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ColorDTO dto = null;
		try {
			Color color = readColor(pm, id);
			dto = color.createDTO();
		} finally {
			pm.close();
		}
		return dto;
	}

	private Color readColor(PersistenceManager pm, Long id) {
		Color item = pm.getObjectById(Color.class, id);
		return item;
	}

	@Override
	public List<ColorDTO> readColors() throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<ColorDTO> colors = new ArrayList<ColorDTO>();
		try {
			List<Color> cols = readColors(pm);
			for (Color o : cols) {
				ColorDTO dto = o.createDTO();
				colors.add(dto);
			}
		} finally {
			pm.close();
		}
		return colors;
	}

	@SuppressWarnings("unchecked")
	private List<Color> readColors(PersistenceManager pm) {
		Query query = pm.newQuery(Color.class);
		List<Color> colors = (List<Color>) query.execute();
		return colors;
	}

	@Override
	public void updateColor(ColorDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			updateColor(pm, dto);
		} finally {
			pm.close();
		}
	}

	private void updateColor(PersistenceManager pm, ColorDTO dto) {
		Color item = readColor(pm, dto.getId());
		item.updateFromDTO(dto);
		pm.makePersistent(item);
	}

	@Override
	public void deleteColor(ColorDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			deleteColor(pm, dto);
		} finally {
			pm.close();
		}
	}

	private void deleteColor(PersistenceManager pm, ColorDTO dto) {
		Color item = readColor(pm, dto.getId());
		pm.deletePersistent(item);
	}

	// *************
	// article-types
	// *************
	@Override
	synchronized public ArticleTypeDTO createArticleType(ArticleTypeDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			ArticleType articleType = createArticleType(pm, dto);
			dto.setProductNumber(articleType.getProductNumber());
		} finally {
			pm.close();
		}
		return dto;
	}

	private ArticleType createArticleType(PersistenceManager pm, ArticleTypeDTO dto) {
		ArticleType at = new ArticleType(dto);
		at.setProductNumber(generateArticleId(pm));
		return pm.makePersistent(at);
	}

	private Long generateArticleId(PersistenceManager pm) {
		IdCounter idCounter;
		try {
			idCounter = pm.getObjectById(IdCounter.class, ArticleType.ID_COUNTER_NAME);
		} catch (JDOObjectNotFoundException e) {
			idCounter = new IdCounter();
			idCounter.setId(ArticleType.ID_COUNTER_NAME);
			idCounter.setIdCounter(0L);
		}
		Long newId = (idCounter.getIdCounter() + 1L);
		idCounter.setIdCounter(newId);
		pm.makePersistent(idCounter);
		return new Long(newId);
	}

	@Override
	public List<ArticleTypeDTO> readArticleTypes() throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<ArticleTypeDTO> dtos = new ArrayList<ArticleTypeDTO>();
		try {
			List<ArticleType> ats = readArticleTypes(pm);
			for (ArticleType at : ats) {
				ArticleTypeDTO dto = at.createDTO();
				dtos.add(dto);
			}
		} finally {
			pm.close();
		}
		return dtos;
	}

	@SuppressWarnings("unchecked")
	private List<ArticleType> readArticleTypes(PersistenceManager pm) {
		Query query = pm.newQuery(ArticleType.class);
		List<ArticleType> articleTypes = (List<ArticleType>) query.execute();
		return articleTypes;
	}

	@Override
	public ArticleTypeDTO readArticleType(Long productNumber) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ArticleTypeDTO dto = null;
		try {
			ArticleType articleType = readArticleType(pm, productNumber);
			dto = articleType.createDTO();
		} finally {
			pm.close();
		}
		return dto;
	}

	private ArticleType readArticleType(PersistenceManager pm, Long id) {
		ArticleType item = pm.getObjectById(ArticleType.class, id);
		return item;
	}

	@Override
	public void updateArticleType(ArticleTypeDTO dto) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			updateArticleType(pm, dto);
		} finally {
			pm.close();
		}
	}

	private void updateArticleType(PersistenceManager pm, ArticleTypeDTO dto) {
		ArticleType item = readArticleType(pm, dto.getProductNumber());
		item.updateFromDTO(dto);
		pm.makePersistent(item);
	}

	@Override
	public void deleteArticleType(ArticleTypeDTO dto) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			deleteArticleType(pm, dto);
		} finally {
			pm.close();
		}
	}

	private void deleteArticleType(PersistenceManager pm, ArticleTypeDTO dto) {
		ArticleType item = readArticleType(pm, dto.getProductNumber());
		pm.deletePersistent(item);
	}
}
