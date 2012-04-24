package com.xfashion.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.xfashion.client.at.ArticleTypeService;
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
			createCategory(pm, new CategoryDTO("Damenhose", 0, "#76A573", "#466944"));
			createCategory(pm, new CategoryDTO("Herrenhose", 1, "#6F77AA", "#2B3781"));
			createCategory(pm, new CategoryDTO("Damenoberteil", 2, "#9EC1AA", "#74A886"));
			createCategory(pm, new CategoryDTO("Herrenoberteil", 3, "#B2B7D9", "#919BCA"));
			createCategory(pm, new CategoryDTO("Kleider", 4, "#CEAFD0", "#B98DBC"));
			createCategory(pm, new CategoryDTO("Strumpfwaren", 5, "#C1AEA5", "#A78C7E"));
			createCategory(pm, new CategoryDTO("Gürtel", 6, "#9F7F79", "#71433A"));
			createCategory(pm, new CategoryDTO("Bademode", 7, "#8AADB8", "#578C9B"));
			createCategory(pm, new CategoryDTO("Accessoirs", 8, "#A26E7B", "#7C3044"));
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
	public void updateCategory(CategoryDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			updateCategory(pm, dto);
		} finally {
			pm.close();
		}
	}
	
	private void updateCategory(PersistenceManager pm, CategoryDTO dto) {
		Category category = readCategory(pm, dto.getId());
		category.updateFromDTO(dto);
		pm.makePersistent(category);
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
			dto.setId(style.getId());
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

	private Style readStyle(PersistenceManager pm, Long id) {
		Style item = pm.getObjectById(Style.class, id);
		return item;
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

	private Brand readBrand(PersistenceManager pm, Long id) {
		Brand item = pm.getObjectById(Brand.class, id);
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
	public SizeDTO createSize(SizeDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Size size = createSize(pm, dto);
			dto.setId(size.getId());
		} finally {
			pm.close();
		}
		return dto;
	}
	
	private Size createSize(PersistenceManager pm, SizeDTO dto) {
		Size size = new Size(dto);
		return pm.makePersistent(size);
	}

	@Override
	public List<SizeDTO> readSizes() throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<SizeDTO> sizes = new ArrayList<SizeDTO>(); 
		try {
			List<Size> szs = readSizes(pm);
			for (Size o : szs) {
				SizeDTO dto = o.createDTO();
				sizes.add(dto);
			}
		} finally {
			pm.close();
		}
		return sizes;
	}

	@SuppressWarnings("unchecked")
	private List<Size> readSizes(PersistenceManager pm) {
		Query query = pm.newQuery(Size.class);
		List<Size> sizes = (List<Size>) query.execute();
		return sizes;
	}

	private Size readSize(PersistenceManager pm, Long id) {
		Size item = pm.getObjectById(Size.class, id);
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
		Size item = readSize(pm, dto.getId());
		item.updateFromDTO(dto);
		pm.makePersistent(item);
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
		Size item = readSize(pm, dto.getId());
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

	private Color readColor(PersistenceManager pm, Long id) {
		Color item = pm.getObjectById(Color.class, id);
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
	public List<ArticleTypeDTO> readArticleTypes() throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<ArticleTypeDTO> dtos = new ArrayList<ArticleTypeDTO>(); 
		try {
			List<ArticleType> ats = readArticleTypes(pm);
			for (ArticleType at : ats) {
				ArticleTypeDTO dto = new ArticleTypeDTO();
				dto.setName(at.getName());
				dto.setCategory(at.getCategory());
				dto.setStyle(at.getStyle());
				dto.setBrand(at.getBrand());
				dto.setColor(at.getColor());
				dto.setSize(at.getSize());
				dto.setPrice(at.getPrice());
				dto.setProductNumber(at.getProductNumber());
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
	public void addArticleType(ArticleTypeDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			ArticleType articleType = new ArticleType();
			articleType.setName(dto.getName());
			articleType.setCategory(dto.getCategory());
			articleType.setStyle(dto.getStyle());
			articleType.setBrand(dto.getBrand());
			articleType.setColor(dto.getColor());
			articleType.setSize(dto.getSize());
			articleType.setPrice(dto.getPrice());
			articleType.setProductNumber(dto.getProductNumber());
			pm.makePersistent(articleType);
		} finally {
			pm.close();
		}
	}

}
