package com.xfashion.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.xfashion.client.ArticleTypeService;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.ColorDTO;
import com.xfashion.shared.SizeDTO;
import com.xfashion.shared.StyleDTO;

public class ArticleTypeServiceImpl extends RemoteServiceServlet implements ArticleTypeService {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void createCategories() throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			deleteCategories(pm);
			createCategory(pm, new CategoryDTO("Ftrs", "Damenhose", "#76A573", "#466944"));
			createCategory(pm, new CategoryDTO("Mtrs", "Herrenhose", "#6F77AA", "#2B3781"));
			createCategory(pm, new CategoryDTO("Ftop", "Damenoberteil", "#9EC1AA", "#74A886"));
			createCategory(pm, new CategoryDTO("Mtop", "Herrenoberteil", "#B2B7D9", "#919BCA"));
			createCategory(pm, new CategoryDTO("Skrt", "Kleider", "#CEAFD0", "#B98DBC"));
			createCategory(pm, new CategoryDTO("Stks", "Strumpfwaren", "#C1AEA5", "#A78C7E"));
			createCategory(pm, new CategoryDTO("Belt", "Gürtel", "#9F7F79", "#71433A"));
			createCategory(pm, new CategoryDTO("Bath", "Bademode", "#8AADB8", "#578C9B"));
			createCategory(pm, new CategoryDTO("Accs", "Accessoirs", "#A26E7B", "#7C3044"));
		} finally {
			pm.close();
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
	public void saveCategory(CategoryDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			saveCategory(pm, dto);
		} finally {
			pm.close();
		}
	}
	
	private void saveCategory(PersistenceManager pm, CategoryDTO dto) {
		Category category = readCategory(pm, dto.getCss());
		category.updateFromDTO(dto);
		pm.makePersistent(category);
	}
	
	private void createCategory(PersistenceManager pm, CategoryDTO dto) {
		Category c = new Category(dto);
		pm.makePersistent(c);
	}

	private Category readCategory(PersistenceManager pm, String categoryId) {
		Category category = pm.getObjectById(Category.class, categoryId);
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
	public void addBrand(BrandDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Brand brand = new Brand(dto);
			pm.makePersistent(brand);
		} finally {
			pm.close();
		}
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

	@Override
	public void addSize(SizeDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Size o = new Size(dto);
			pm.makePersistent(o);
		} finally {
			pm.close();
		}
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
	public void addColor(ColorDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Color o = new Color(dto);
			pm.makePersistent(o);
		} finally {
			pm.close();
		}
	}

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
