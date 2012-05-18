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

	public static final long MULT_TYPE = 100000000000L;
	public static final long MULT_CAT = 1000000000L;
	public static final long MULT_BUYP = 1000000L;

	// *************
	// categories
	// *************
	@Override
	public CategoryDTO readCategory(String key) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		CategoryDTO dto = null;
		try {
			Category category = readCategory(pm, key);
			dto = category.createDTO();
		} finally {
			pm.close();
		}
		return dto;
	}

	private Category readCategory(PersistenceManager pm, String keyString) {
		Category category = pm.getObjectById(Category.class, KeyFactory.stringToKey(keyString));
		return category;
	}

	@Override
	public List<CategoryDTO> readCategories() throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<CategoryDTO> dtos;
		try {
			Categories items = readCategories(pm);
			dtos = items.getDtos();
		} finally {
			pm.close();
		}
		return dtos;
	}

	@SuppressWarnings("unchecked")
	private Categories readCategories(PersistenceManager pm) {
		Query query = pm.newQuery(Categories.class);
		Categories item;
		List<Categories> items = (List<Categories>) query.execute();
		if (items.size() == 0) {
			item = new Categories();
			item = pm.makePersistent(item);
		} else {
			item = items.get(0);
		}
		return item;
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			updateCategory(pm, dto);
		} finally {
			pm.close();
		}
		return readCategory(dto.getKey());
	}

	private Category updateCategory(PersistenceManager pm, CategoryDTO dto) {
		Category category = readCategory(pm, dto.getKey());
		category.updateFromDTO(dto);
		return pm.makePersistent(category);
	}

	@Override
	public List<CategoryDTO> updateCategories(List<CategoryDTO> dtos) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			updateCategories(pm, dtos);
		} finally {
			pm.close();
		}
		return readCategories();
	}

	public void updateCategories(PersistenceManager pm, List<CategoryDTO> dtos) {
		Categories categories = readCategories(pm);
		categories.update(dtos);
		pm.makePersistent(categories);
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
		Category category = readCategory(pm, dto.getKey());
		pm.deletePersistent(category);
	}

	// *************
	// styles
	// *************
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
		Style item = readStyle(pm, dto.getKey());
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
		Style item = readStyle(pm, dto.getKey());
		pm.deletePersistent(item);
	}

	// *************
	// brands
	// *************
	@Override
	public BrandDTO readBrand(String key) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		BrandDTO dto = null;
		try {
			Brand brand = readBrand(pm, key);
			dto = brand.createDTO();
		} finally {
			pm.close();
		}
		return dto;
	}

	private Brand readBrand(PersistenceManager pm, String key) {
		Brand item = pm.getObjectById(Brand.class, KeyFactory.stringToKey(key));
		return item;
	}

	@Override
	public List<BrandDTO> readBrands() throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<BrandDTO> dtos;
		try {
			Brands items = readBrands(pm);
			dtos = items.getDtos();
		} finally {
			pm.close();
		}
		return dtos;
	}

	@SuppressWarnings("unchecked")
	private Brands readBrands(PersistenceManager pm) {
		Query query = pm.newQuery(Brands.class);
		Brands item;
		List<Brands> items = (List<Brands>) query.execute();
		if (items.size() == 0) {
			item = new Brands();
			item = pm.makePersistent(item);
		} else {
			item = items.get(0);
		}
		return item;
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
		Brand item = readBrand(pm, dto.getKey());
		item.updateFromDTO(dto);
		pm.makePersistent(item);
	}

	@Override
	public List<BrandDTO> updateBrands(List<BrandDTO> dtos) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			updateBrands(pm, dtos);
		} finally {
			pm.close();
		}
		return readBrands();
	}

	public void updateBrands(PersistenceManager pm, List<BrandDTO> dtos) {
		Brands brands = readBrands(pm);
		brands.update(dtos);
		pm.makePersistent(brands);
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
		Brand item = readBrand(pm, dto.getKey());
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
			Sizes items = readSizes(pm);
			dtos = items.getDtos();
		} finally {
			pm.close();
		}
		return dtos;
	}

	@SuppressWarnings("unchecked")
	private Sizes readSizes(PersistenceManager pm) {
		Query query = pm.newQuery(Sizes.class);
		Sizes item;
		List<Sizes> items = (List<Sizes>) query.execute();
		if (items.size() == 0) {
			item = new Sizes();
			item = pm.makePersistent(item);
		} else {
			item = items.get(0);
		}
		return item;
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
	public ColorDTO readColor(String key) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ColorDTO dto = null;
		try {
			Color color = readColor(pm, key);
			dto = color.createDTO();
		} finally {
			pm.close();
		}
		return dto;
	}

	private Color readColor(PersistenceManager pm, String key) {
		Color item = pm.getObjectById(Color.class, KeyFactory.stringToKey(key));
		return item;
	}

	@Override
	public List<ColorDTO> readColors() throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<ColorDTO> dtos;
		try {
			Colors items = readColors(pm);
			dtos = items.getDtos();
		} finally {
			pm.close();
		}
		return dtos;
	}

	@SuppressWarnings("unchecked")
	private Colors readColors(PersistenceManager pm) {
		Query query = pm.newQuery(Colors.class);
		Colors item;
		List<Colors> items = (List<Colors>) query.execute();
		if (items.size() == 0) {
			item = new Colors();
			item = pm.makePersistent(item);
		} else {
			item = items.get(0);
		}
		return item;
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
		Color item = readColor(pm, dto.getKey());
		item.updateFromDTO(dto);
		pm.makePersistent(item);
	}

	@Override
	public List<ColorDTO> updateColors(List<ColorDTO> dtos) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			updateColors(pm, dtos);
		} finally {
			pm.close();
		}
		return readColors();
	}

	private void updateColors(PersistenceManager pm, List<ColorDTO> dtos) {
		Colors colors = readColors(pm);
		colors.update(dtos);
		pm.makePersistent(colors);
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
		Color item = readColor(pm, dto.getKey());
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
		Long id = generateArticleId(pm);
		Category category = readCategory(pm, dto.getCategoryKey());
		Long pn = 1 * MULT_TYPE + dto.getBuyPrice() / 100 * MULT_BUYP + category.getCategoryNumber() * MULT_CAT + id;
		at.setProductNumber(pn);
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
