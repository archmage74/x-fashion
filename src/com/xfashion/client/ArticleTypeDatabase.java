package com.xfashion.client;

import java.util.ArrayList;

import com.google.gwt.user.client.Random;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;

public class ArticleTypeDatabase {

	public static final String[] CATEGORIES = { "Damenhose", "Herrenhose", "Damenoberteil", "Herrenoberteil", "Kleider", "Strumpfwaren", "Gürtel",
			"Bademode", "Accessoirs" };

	public static final String[] NAMES = { "Abbie", "Abigail", "Aimee", "Alexandra", "Alice", "Alicia", "Alisha", "Amber", "Amelia", "Amelie", "Amy",
			"Anna", "Ava", "Bethany", "Brooke", "Caitlin", "Charlotte", "Chloe", "Courtney", "Daisy", "Eleanor", "Elizabeth", "Ella", "Ellie",
			"Emilia", "Emily", "Emma", "Erin", "Esme", "Eva", "Eve", "Evelyn", "Evie", "Faith", "Florence", "Francesca", "Freya", "Georgia", "Grace",
			"Gracie", "Hannah", "Harriet", "Hollie", "Holly", "Imogen", "Isabel", "Isabella", "Isabelle", "Isla", "Isobel", "Jasmine", "Jessica",
			"Julia", "Katie", "Keira", "Lacey", "Lauren", "Layla", "Leah", "Lexi", "Lexie", "Libby", "Lilly", "Lily", "Lola", "Lucy", "Lydia",
			"Maddison", "Madison", "Maisie", "Maria", "Martha", "Maryam", "Matilda", "Maya", "Megan", "Mia", "Millie", "Molly", "Niamh", "Nicole",
			"Olivia", "Paige", "Phoebe", "Poppy", "Rebecca", "Rosie", "Ruby", "Sarah", "Scarlett", "Sienna", "Skye", "Sofia", "Sophia", "Sophie",
			"Summer", "Tia", "Tilly", "Zara", "Zoe", "Gertrud", "Anna", "Martha", "Frieda", "Erna", "Margarethe", "Elisabeth", "Herta", "Else",
			"Käthe", "Helene", "Marie", "Emma", "Maria", "Luise", "Charlotte", "Elsa", "Hedwig", "Johanna", "Berta", "Ella", "Klara", "Elfriede",
			"Paula", "Elli", "Anni", "Hildegard", "Alma", "Minna", "Irma", "Ida", "Dora", "Olga", "Ilse", "Auguste", "Emmi", "Wilhelmine", "Irmgard",
			"Erika", "Grete", "Meta", "Agnes", "Alice", "Gretchen", "Dorothea", "Katharina", "Anita", "Margaretha ", "Annemarie", "Henny", "Elise",
			"Emilie", "Rosa", "Wilma", "Carla", "Sophie", "Matilde", "Magda", "Anne", "Lina", "Lucy", "Gerda", "Edith", "Lilly", "Magdalene",
			"Alwine", "Henriette", "Anneliese", "Franziska", "Hilda", "Hermine", "Caroline", "Marianne", "Lisbeth", "Antonie", "Elsbeth", "Amanda",
			"Lieselotte", "Walli", "Eva", "Walter", "Karl", "Hans", "Wilhelm", "Heinrich", "Otto", "Paul", "Hermann", "Ernst", "Willi", "Friedrich",
			"Kurt", "Erich", "Alfred", "Herbert", "Franz", "Fritz", "Rudolf", "Richard", "Johannes", "Max", "Gustav", "Werner", "Adolf", "Johann",
			"Albert", "Georg", "Emil", "Artur", "Bruno", "August", "Helmut", "Josef", "Robert", "Erwin", "Bernhard", "Gerhard", "Henri", "Ludwig",
			"Hugo", "Julius", "Heinz", "Peter", "Theodor", "Oskar", "Martin", "Eduard", "Waldemar", "Alwin", "Ewald", "Günter", "Jonni", "Konrad",
			"Arnold", "Ferdinand", "Reinhold", "John", "Klaus", "Harry", "Berthold", "Christian", "Hinrich", "Edmund", "Alfons", "Joachim", "Toni",
			"Anton", "Alexander", "Edgar", "Wolfgang", "Eugen", "Arno", "Jakob", "Rolf", "Felix", "Horst", "Hubert", "Andreas", "Leo", "Stefan"
	};

	public static final String[] STYLES = { "Jeans", "Jeggings", "Jeanos", "Capri", "Bermuda", "Short", "Hot Pants" };

	public static final String[] BRANDS = { "Mustang", "Lee", "Rose Player", "Miss Sexy", "XFashion", "VS-Miss", "Daysie", "Sissy", "Tyra",
			"Girls Oregon", "Indiana" };

	public static final String[] SIZES = { "24/32", "25/32", "26/32", "27/32", "28/32", "29/32", "30/32", "31/32", "32/32", "33/32", "34/32",
			"24/34", "25/34", "26/34", "27/34", "28/34", "29/34", "30/34", "31/34", "32/34", "33/34", "34/34", "XXS", "XS", "S", "M", "L", "XL",
			"XXL", "XXXL" };

	public static final String[] COLORS = { "Schwarz", "Jeansblau", "Dunkelblau", "Hellblau", "Weiss", "Türkis", "Gelb" };

	private ArrayList<ArticleType> articleTypes;
	private ArrayList<ArticleType> filteredArticleTypes;

	private ListDataProvider<String> categoryProvider;

	private ListDataProvider<String> styleProvider;

	private ListDataProvider<ArticleType> articleTypeProvider;

	private String categoryFilter = null;

	private String styleFilter = null;

	public ArticleTypeDatabase() {
		init();
	}

	public void init() {
		createArticleTypes();
		createCategoryProvider();
		createStyleProvider();
		createArticleTypeProvider();
	}

	private void createCategoryProvider() {
		ArrayList<String> categories = new ArrayList<String>();
		for (String category : CATEGORIES) {
			categories.add(category);
		}
		categoryProvider = new ListDataProvider<String>(categories);
	}

	private void createStyleProvider() {
		ArrayList<String> styles = new ArrayList<String>();
		for (String category : STYLES) {
			styles.add(category);
		}
		styleProvider = new ListDataProvider<String>(styles);
	}

	private void createArticleTypeProvider() {
		articleTypeProvider = new ListDataProvider<ArticleType>(filteredArticleTypes);
	}

	private void createArticleTypes() {
		articleTypes = new ArrayList<ArticleType>();
		for (String name : NAMES) {
			ArticleType at = new ArticleType();
			at.setName(name);
			at.setCategory(CATEGORIES[Random.nextInt(CATEGORIES.length)]);
			at.setStyle(STYLES[Random.nextInt(STYLES.length)]);
			at.setBrand(BRANDS[Random.nextInt(BRANDS.length)]);
			at.setSize(SIZES[Random.nextInt(SIZES.length)]);
			at.setColor(COLORS[Random.nextInt(COLORS.length)]);
			articleTypes.add(at);
		}
		filteredArticleTypes = new ArrayList<ArticleType>(articleTypes);
	}

	private void applyFilters() {
		ArrayList<ArticleType> result = new ArrayList<ArticleType>(articleTypes);
		ArrayList<ArticleType> temp = new ArrayList<ArticleType>();

		if (categoryFilter != null) {
			for (ArticleType at : result) {
				if (at.getCategory().equals(categoryFilter)) {
					temp.add(at);
				}
			}
			result.retainAll(temp);
		}

		if (styleFilter != null) {
			temp.clear();
			for (ArticleType at : result) {
				if (at.getStyle().equals(styleFilter)) {
					temp.add(at);
				}
			}
			result.retainAll(temp);
		}

		filteredArticleTypes = result;
		articleTypeProvider.setList(filteredArticleTypes);
	}

	public void setCategoryFilter(String category) {
		categoryFilter = category;
		applyFilters();
	}

	public void setStyleFilter(String style) {
		styleFilter = style;
		applyFilters();
	}

	public void addCategoryDisplay(HasData<String> display) {
		categoryProvider.addDataDisplay(display);
	}

	public void addStyleDisplay(HasData<String> display) {
		styleProvider.addDataDisplay(display);
	}

	public void addArticleTypeDisplay(HasData<ArticleType> display) {
		articleTypeProvider.addDataDisplay(display);
	}

}
