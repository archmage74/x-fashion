package com.xfashion.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.xfashion.client.at.ArticleTypeService;
import com.xfashion.server.img.ImageUploadServiceImpl;
import com.xfashion.server.task.DistributePriceChangeServlet;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.ColorDTO;
import com.xfashion.shared.PriceChangeDTO;
import com.xfashion.shared.SizeDTO;
import com.xfashion.shared.StyleDTO;

public class ArticleTypeServiceImpl extends RemoteServiceServlet implements ArticleTypeService {

	private static final long serialVersionUID = 1L;

	public static final long MULT_TYPE = 100000000000L;
	public static final long MULT_CAT = 1000000000L;
	public static final long MULT_BUYP = 1000000L;

	ImageUploadServiceImpl imageUploadServiceImpl = new ImageUploadServiceImpl();
	
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
		Category category;
		try {
			category = readCategory(pm, dto.getCategoryKey());
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			pm.close();
		}

		pm = PMF.get().getPersistenceManager();
		try {
			ArticleType articleType = createArticleType(pm, dto, category);
			articleType = readArticleType(pm, articleType.getKeyString());
			dto = articleType.createDTO();
			addImageUrl(dto);
		} finally {
			pm.close();
		}
		return dto;
	}

	private ArticleType createArticleType(PersistenceManager pm, ArticleTypeDTO dto, Category category) {
		Transaction tx = pm.currentTransaction();
		ArticleType at = null;
		try {
			tx.begin();
			ArticleTypes articleTypes = readArticleTypes(pm);
			at = new ArticleType(dto);
			Long id = generateArticleId(articleTypes);
			Long productNumber = 1 * MULT_TYPE + dto.getBuyPrice() / 100 * MULT_BUYP + category.getCategoryNumber() * MULT_CAT + id;
			at.setProductNumber(productNumber);
			articleTypes.getArticleTypes().add(at);
			pm.makePersistent(articleTypes);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}

		return at;
	}

	private Long generateArticleId(ArticleTypes articleTypes) {
		Long newId = (articleTypes.getIdCounter() + 1L);
		articleTypes.setIdCounter(newId);
		return new Long(newId);
	}

	@Override
	public Set<ArticleTypeDTO> readArticleTypes() throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Set<ArticleTypeDTO> dtos = null;
		try {
			ArticleTypes articleTypes = readArticleTypes(pm);
			dtos = articleTypes.getDtos();
			HashSet<String> imageKeys = new HashSet<String>();
			for (ArticleTypeDTO dto : dtos) {
				if (dto.getImageKey() != null) {
					imageKeys.add(dto.getImageKey());
				}
			}
			addImageUrls(dtos, imageKeys);
		} finally {
			pm.close();
		}
		return dtos;
	}

	private void addImageUrl(ArticleTypeDTO dto) {
		HashSet<String> imageKeys = new HashSet<String>();
		imageKeys.add(dto.getImageKey());
		HashSet<ArticleTypeDTO> dtos = new HashSet<ArticleTypeDTO>();
		dtos.add(dto);
		addImageUrls(dtos, imageKeys);
	}

	private void addImageUrls(Set<ArticleTypeDTO> dtos, HashSet<String> imageKeys) {
		Map<String, String> urls = imageUploadServiceImpl.readImageUrls(imageKeys);
		for (ArticleTypeDTO dto : dtos) {
			if (dto.getImageKey() != null) {
				dto.setImageUrl(urls.get(dto.getImageKey()));
			}
		}
	}

	@SuppressWarnings("unchecked")
	private ArticleTypes readArticleTypes(PersistenceManager pm) {
		Query query = pm.newQuery(ArticleTypes.class);
		ArticleTypes item;
		List<ArticleTypes> items = (List<ArticleTypes>) query.execute();
		if (items.size() == 0) {
			item = new ArticleTypes();
			item = pm.makePersistent(item);
		} else {
			item = items.get(0);
		}
		return item;
	}

	@Override
	public ArticleTypeDTO readArticleType(Long productNumber) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ArticleTypeDTO dto = null;
		try {
			ArticleType articleType = readArticleType(pm, productNumber);
			dto = articleType.createDTO();
			addImageUrl(dto);
		} finally {
			pm.close();
		}
		return dto;
	}

	@SuppressWarnings("unchecked")
	private ArticleType readArticleType(PersistenceManager pm, Long id) {
		Query query = pm.newQuery(ArticleType.class);
		query.setFilter("productNumber == productNumberParam");
		query.declareParameters("Long productNumberParam");
		List<ArticleType> list = (List<ArticleType>) query.execute(id);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public ArticleTypeDTO readArticleType(String key) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ArticleTypeDTO dto = null;
		try {
			ArticleType articleType = readArticleType(pm, key);
			dto = articleType.createDTO();
			addImageUrl(dto);
		} finally {
			pm.close();
		}
		return dto;
	}

	public ArticleType readArticleType(PersistenceManager pm, String key) {
		ArticleType articleType = pm.getObjectById(ArticleType.class, key);
		return articleType;
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

	@Override
	public void updateArticleTypes(Collection<ArticleTypeDTO> dtos) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			for (ArticleTypeDTO dto : dtos) {
				updateArticleType(pm, dto);
			}
		} finally {
			pm.close();
		}
	}

	private void updateArticleType(PersistenceManager pm, ArticleTypeDTO dto) {
		ArticleType item = readArticleType(pm, dto.getKey());
		item.updateFromDTO(dto);
		pm.flush();
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
		ArticleType item = readArticleType(pm, dto.getKey());
		if (item.getUsed() != null && item.getUsed() == false) {
			pm.deletePersistent(item);
		}
	}

	@Override
	public void createPriceChanges(Collection<PriceChangeDTO> dtos) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			dtos = createPriceChanges(pm, dtos);
			Queue queue = QueueFactory.getDefaultQueue();
			for (PriceChangeDTO dto : dtos) {
				queue.add(TaskOptions.Builder.withUrl("/task/distributepricechange").param(DistributePriceChangeServlet.PARAM_PRICE_CHANGE_KEY,
						dto.getKeyString()));
			}
		} finally {
			pm.close();
		}
	}

	private Collection<PriceChangeDTO> createPriceChanges(PersistenceManager pm, Collection<PriceChangeDTO> dtos) {
		PriceChanges priceChanges = readPriceChanges(pm);
		Collection<PriceChangeDTO> stored = new ArrayList<PriceChangeDTO>();
		for (PriceChangeDTO dto : dtos) {
			PriceChange priceChange = new PriceChange(dto);
			priceChanges.getPriceChanges().add(priceChange);
			stored.add(priceChange.createDTO());
		}
		return stored;
	}

	@SuppressWarnings("unchecked")
	private PriceChanges readPriceChanges(PersistenceManager pm) {
		Query query = pm.newQuery(PriceChanges.class);
		PriceChanges item;
		List<PriceChanges> items = (List<PriceChanges>) query.execute();
		if (items.size() == 0) {
			item = new PriceChanges();
			item = pm.makePersistent(item);
		} else {
			item = items.get(0);
		}
		return item;
	}
	
	@Override
	public PriceChangeDTO readPriceChange(String priceChangeKey) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		PriceChangeDTO dto = null;
		try {
			PriceChange priceChange = readPriceChange(pm, priceChangeKey);
			dto = priceChange.createDTO();
		} finally {
			pm.close();
		}
		return dto;
	}
	
	private PriceChange readPriceChange(PersistenceManager pm, String priceChangeKey) {
		PriceChange priceChange = pm.getObjectById(PriceChange.class, KeyFactory.stringToKey(priceChangeKey));
		return priceChange;
	}

	@Override
	public void deletePriceChange(PriceChangeDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			deletePriceChange(pm, dto);
		} finally {
			pm.close();
		}
	}

	private void deletePriceChange(PersistenceManager pm, PriceChangeDTO dto) {
		PriceChange item = readPriceChange(pm, dto.getKeyString());
		pm.deletePersistent(item);
	}
	
}
