package com.xfashion.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.StyleDTO;

public class ArticleTypeDatabase {

	private ArticleTypeServiceAsync articleTypeService = (ArticleTypeServiceAsync) GWT.create(ArticleTypeService.class);

	public String[] categories = null;

	public static final String[] CATEGORIES = { "Damenhose", "Herrenhose", "Damenoberteil", "Herrenoberteil", "Kleider", "Strumpfwaren", "Gürtel",
			"Bademode", "Accessoirs" };

//	public static final String[] NAMES = { "Abbie", "Abigail", "Aimee", "Alexandra", "Alice", "Alicia", "Alisha", "Amber", "Amelia", "Amelie", "Amy",
//			"Anna", "Ava", "Bethany", "Brooke", "Caitlin", "Charlotte", "Chloe", "Courtney", "Daisy", "Eleanor", "Elizabeth", "Ella", "Ellie",
//			"Emilia", "Emily", "Emma", "Erin", "Esme", "Eva", "Eve", "Evelyn", "Evie", "Faith", "Florence", "Francesca", "Freya", "Georgia", "Grace",
//			"Gracie", "Hannah", "Harriet", "Hollie", "Holly", "Imogen", "Isabel", "Isabella", "Isabelle", "Isla", "Isobel", "Jasmine", "Jessica",
//			"Julia", "Katie", "Keira", "Lacey", "Lauren", "Layla", "Leah", "Lexi", "Lexie", "Libby", "Lilly", "Lily", "Lola", "Lucy", "Lydia",
//			"Maddison", "Madison", "Maisie", "Maria", "Martha", "Maryam", "Matilda", "Maya", "Megan", "Mia", "Millie", "Molly", "Niamh", "Nicole",
//			"Olivia", "Paige", "Phoebe", "Poppy", "Rebecca", "Rosie", "Ruby", "Sarah", "Scarlett", "Sienna", "Skye", "Sofia", "Sophia", "Sophie",
//			"Summer", "Tia", "Tilly", "Zara", "Zoe", "Gertrud", "Anna", "Martha", "Frieda", "Erna", "Margarethe", "Elisabeth", "Herta", "Else",
//			"Käthe", "Helene", "Marie", "Emma", "Maria", "Luise", "Charlotte", "Elsa", "Hedwig", "Johanna", "Berta", "Ella", "Klara", "Elfriede",
//			"Paula", "Elli", "Anni", "Hildegard", "Alma", "Minna", "Irma", "Ida", "Dora", "Olga", "Ilse", "Auguste", "Emmi", "Wilhelmine", "Irmgard",
//			"Erika", "Grete", "Meta", "Agnes", "Alice", "Gretchen", "Dorothea", "Katharina", "Anita", "Margaretha ", "Annemarie", "Henny", "Elise",
//			"Emilie", "Rosa", "Wilma", "Carla", "Sophie", "Matilde", "Magda", "Anne", "Lina", "Lucy", "Gerda", "Edith", "Lilly", "Magdalene",
//			"Alwine", "Henriette", "Anneliese", "Franziska", "Hilda", "Hermine", "Caroline", "Marianne", "Lisbeth", "Antonie", "Elsbeth", "Amanda",
//			"Lieselotte", "Walli", "Eva", "Walter", "Karl", "Hans", "Wilhelm", "Heinrich", "Otto", "Paul", "Hermann", "Ernst", "Willi", "Friedrich",
//			"Kurt", "Erich", "Alfred", "Herbert", "Franz", "Fritz", "Rudolf", "Richard", "Johannes", "Max", "Gustav", "Werner", "Adolf", "Johann",
//			"Albert", "Georg", "Emil", "Artur", "Bruno", "August", "Helmut", "Josef", "Robert", "Erwin", "Bernhard", "Gerhard", "Henri", "Ludwig",
//			"Hugo", "Julius", "Heinz", "Peter", "Theodor", "Oskar", "Martin", "Eduard", "Waldemar", "Alwin", "Ewald", "Günter", "Jonni", "Konrad",
//			"Arnold", "Ferdinand", "Reinhold", "John", "Klaus", "Harry", "Berthold", "Christian", "Hinrich", "Edmund", "Alfons", "Joachim", "Toni",
//			"Anton", "Alexander", "Edgar", "Wolfgang", "Eugen", "Arno", "Jakob", "Rolf", "Felix", "Horst", "Hubert", "Andreas", "Leo", "Stefan" };

	public static final String[] STYLES = { "Jeans", "Jeggings", "Jeanos", "Capri", "Bermuda", "Short", "Hot Pants" };

	public static final String[] BRANDS = { "Mustang", "Lee", "Rose Player", "Miss Sexy", "XFashion", "VS-Miss", "Daysie", "Sissy", "Tyra",
			"Girls Oregon", "Indiana" };

	public static final String[] SIZES = { "24/32", "25/32", "26/32", "27/32", "28/32", "29/32", "30/32", "31/32", "32/32", "33/32", "34/32",
			"24/34", "25/34", "26/34", "27/34", "28/34", "29/34", "30/34", "31/34", "32/34", "33/34", "34/34", "XXS", "XS", "S", "M", "L", "XL",
			"XXL", "XXXL" };

	public static final String[] COLORS = { "Schwarz", "Jeansblau", "Dunkelblau", "Hellblau", "Weiss", "Türkis", "Gelb" };

	private ArrayList<ArticleTypeDTO> articleTypes;
	private List<ArticleTypeDTO> filteredArticleTypes;

	private ListDataProvider<CategoryDTO> categoryProvider;
	private ListDataProvider<StyleCellData> styleProvider;
	private ListDataProvider<BrandCellData> brandProvider;
	private ListDataProvider<String> colorProvider;
	private ListDataProvider<String> sizeProvider;

	private ListDataProvider<ArticleTypeDTO> articleTypeProvider;

	private CategoryDTO categoryFilter = null;
	private Set<String> styleFilter = null;
	private Set<String> brandFilter = null;
	private Set<String> colorFilter = null;
	private Set<String> sizeFilter = null;

	public ArticleTypeDatabase() {
		init();
	}

	public void init() {
		createCategoryProvider();
		createStyleProvider();
		createBrandProvider();
		createColorProvider();
		createSizeProvider();
		createArticleTypeProvider();

		readCategories();
		readStyles();
		readBrands();
		readArticleTypes();

		styleFilter = new HashSet<String>();
		brandFilter = new HashSet<String>();
	}

	public void createCategories() {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
			}
			@Override
			public void onSuccess(Void result) {
			}
		};
		articleTypeService.createCategories(callback);
	}

	private void readCategories() {
		AsyncCallback<List<CategoryDTO>> callback = new AsyncCallback<List<CategoryDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
			}
			@Override
			public void onSuccess(List<CategoryDTO> result) {
				List<CategoryDTO> list = categoryProvider.getList();
				list.addAll(result);
			}
		};

		articleTypeService.readCategories(callback);
	}

	private void readStyles() {
		AsyncCallback<List<StyleDTO>> callback = new AsyncCallback<List<StyleDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
			}
			@Override
			public void onSuccess(List<StyleDTO> result) {
				List<StyleCellData> list = styleProvider.getList();
				for (StyleDTO dto : result) {
					StyleCellData scd = new StyleCellData(dto.getName(), true);
					list.add(scd);
				}
			}
		};
		articleTypeService.readStyles(callback);
	}
	
	private void readBrands() {
		AsyncCallback<List<BrandDTO>> callback = new AsyncCallback<List<BrandDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
			}
			@Override
			public void onSuccess(List<BrandDTO> result) {
				List<BrandCellData> list = brandProvider.getList();
				for (BrandDTO dto : result) {
					BrandCellData bcd = new BrandCellData(dto.getName(), true);
					list.add(bcd);
				}
			}
		};
		articleTypeService.readBrands(callback);
	}
	
	private void readArticleTypes() {
		AsyncCallback<List<ArticleTypeDTO>> callback = new AsyncCallback<List<ArticleTypeDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
			}
			@Override
			public void onSuccess(List<ArticleTypeDTO> result) {
				articleTypeProvider.getList().clear();
				articleTypes = new ArrayList<ArticleTypeDTO>(result);
				articleTypeProvider.getList().addAll(result);
			}
		};
		articleTypeService.readArticleTypes(callback);
	}
	
	private void createCategoryProvider() {
		categoryProvider = new ListDataProvider<CategoryDTO>();
	}
	
	private void createStyleProvider() {
		styleProvider = new ListDataProvider<StyleCellData>();
	}
	
	private void createBrandProvider() {
		brandProvider = new ListDataProvider<BrandCellData>();
	}
	
	private void createColorProvider() {
		ArrayList<String> colors = new ArrayList<String>();
		for (String brand : COLORS) {
			colors.add(brand);
		}
		colorProvider = new ListDataProvider<String>(colors);
	}
	
	private void createSizeProvider() {
		ArrayList<String> sizes = new ArrayList<String>();
		for (String size : SIZES) {
			sizes.add(size);
		}
		sizeProvider = new ListDataProvider<String>(sizes);
	}
	
	private void createArticleTypeProvider() {
		articleTypeProvider = new ListDataProvider<ArticleTypeDTO>();
	}
	
	public void applyFilters() {
		List<ArticleTypeDTO> result = new ArrayList<ArticleTypeDTO>(articleTypes);
		ArrayList<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>();

		result = applyCategoryFilter(result, categoryFilter);
		
		if (styleFilter != null && styleFilter.size() > 0) {
			temp.clear();
			for (ArticleTypeDTO at : result) {
				if (styleFilter.contains(at.getStyle())) {
					temp.add(at);
				}
			}
			result.retainAll(temp);
		}

		if (brandFilter != null && brandFilter.size() > 0) {
			temp.clear();
			for (ArticleTypeDTO at : result) {
				if (brandFilter.contains(at.getBrand())) {
					temp.add(at);
				}
			}
			result.retainAll(temp);
		}

		if (colorFilter != null && colorFilter.size() > 0) {
			temp.clear();
			for (ArticleTypeDTO at : result) {
				if (colorFilter.contains(at.getColor())) {
					temp.add(at);
				}
			}
			result.retainAll(temp);
		}

		if (sizeFilter != null && sizeFilter.size() > 0) {
			temp.clear();
			for (ArticleTypeDTO at : result) {
				if (sizeFilter.contains(at.getSize())) {
					temp.add(at);
				}
			}
			result.retainAll(temp);
		}

		filteredArticleTypes = result;
		articleTypeProvider.setList(filteredArticleTypes);
	}
	
	private List<ArticleTypeDTO> applyCategoryFilter(List<ArticleTypeDTO> articles, CategoryDTO categoryFilter) {
		ArrayList<ArticleTypeDTO> result = new ArrayList<ArticleTypeDTO>(articleTypes);
		ArrayList<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>();

		if (categoryFilter != null) {
			for (ArticleTypeDTO at : result) {
				if (categoryFilter.getName().equals(at.getCategory())) {
					temp.add(at);
				}
			}
			result.retainAll(temp);
		}
		return result;
	}

	public CategoryDTO getCategoryFilter() {
		return categoryFilter;
	}
	
	public void setCategoryFilter(CategoryDTO category) {
		if (category != null) {
			categoryFilter = category;
			updateStyleProvider();
			updateBrandProvider();
		} else {
			categoryFilter = null;
		}
		applyFilters();
	}

	public void updateStyleProvider() {
		List<String> stylesOfArticles = new ArrayList<String>();
		List<ArticleTypeDTO> articlesOfCategory = applyCategoryFilter(new ArrayList<ArticleTypeDTO>(articleTypes), categoryFilter);
		for (ArticleTypeDTO at : articlesOfCategory) {
			stylesOfArticles.add(at.getStyle());
		}
		List<StyleCellData> styleCells = styleProvider.getList();
		for (StyleCellData scd : styleCells) {
			scd.setAvailable(stylesOfArticles.contains(scd.getName()));
			scd.setSelected(styleFilter.contains(scd.getName()));
		}
		styleProvider.refresh();
	}

	public void updateBrandProvider() {
		List<String> brandOfArticles = new ArrayList<String>();
		List<ArticleTypeDTO> articlesOfCategory = applyCategoryFilter(new ArrayList<ArticleTypeDTO>(articleTypes), categoryFilter);
		for (ArticleTypeDTO at : articlesOfCategory) {
			brandOfArticles.add(at.getBrand());
		}
		List<BrandCellData> brandCells = brandProvider.getList();
		for (BrandCellData bcd : brandCells) {
			bcd.setAvailable(brandOfArticles.contains(bcd.getName()));
			bcd.setSelected(brandFilter.contains(bcd.getName()));
		}
		brandProvider.refresh();
	}
	
	public void updateArticleTypeProvider() {
		
	}

	public Set<String> getStyleFilter() {
		return styleFilter;
	}
	
	public void setStyleFilter(Set<String> styles) {
		styleFilter = styles;
	}

	public Set<String> getBrandFilter() {
		return brandFilter;
	}
	
	public void setBrandFilter(Set<String> brands) {
		brandFilter = brands;
	}

	public void setColorFilter(Set<String> color) {
		colorFilter = color;
		applyFilters();
	}

	public void setSizeFilter(Set<String> size) {
		sizeFilter = size;
		applyFilters();
	}

	public void addBrandDisplay(HasData<BrandCellData> display) {
		brandProvider.addDataDisplay(display);
	}

	public void addColorDisplay(HasData<String> display) {
		colorProvider.addDataDisplay(display);
	}

	public void addSizeDisplay(HasData<String> display) {
		sizeProvider.addDataDisplay(display);
	}

	public void addArticleTypeDisplay(HasData<ArticleTypeDTO> display) {
		articleTypeProvider.addDataDisplay(display);
	}

	public ListDataProvider<CategoryDTO> getCategoryProvider() {
		return categoryProvider;
	}

	public ListDataProvider<StyleCellData> getStyleProvider() {
		return styleProvider;
	}

	public ListDataProvider<BrandCellData> getBrandProvider() {
		return brandProvider;
	}

	public ListDataProvider<ArticleTypeDTO> getArticleTypeProvider() {
		return articleTypeProvider;
	}

	public void addStyle(final String style) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) { }
			@Override
			public void onSuccess(Void result) {
				StyleCellData scd = new StyleCellData(style, true);
				styleProvider.getList().add(scd);
				updateStyleProvider();
			}
		};
		StyleDTO dto = new StyleDTO();
		dto.setName(style);
		articleTypeService.addStyle(dto, callback);
	}

	public void addBrand(final String brand) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) { }
			@Override
			public void onSuccess(Void result) { 
				BrandCellData bcd = new BrandCellData(brand, true);
				brandProvider.getList().add(bcd);
				updateBrandProvider();
			}
		};
		BrandDTO dto = new BrandDTO();
		dto.setName(brand);
		articleTypeService.addBrand(dto, callback);
	}

	public void addArticleType(final ArticleTypeDTO articleType) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) { }
			@Override
			public void onSuccess(Void result) {
				articleTypes.add(articleType);
				// articleTypeProvider.getList().add(articleType);
				updateStyleProvider();
				updateBrandProvider();
				applyFilters();
			}
		};
		articleTypeService.addArticleType(articleType, callback);
	}
	
	public class StyleCell {
		public String name;
		public boolean available;
	}

}
