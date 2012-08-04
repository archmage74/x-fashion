package com.xfashion.client.at.sort;

import com.xfashion.client.FilterDataProvider;
import com.xfashion.client.at.brand.BrandDataProvider;
import com.xfashion.client.at.bulk.AttributeAccessor;
import com.xfashion.client.at.bulk.BrandKeyAccessor;
import com.xfashion.client.at.bulk.CategoryKeyAccessor;
import com.xfashion.client.at.bulk.ColorKeyAccessor;
import com.xfashion.client.at.category.CategoryDataProvider;
import com.xfashion.client.at.color.ColorDataProvider;
import com.xfashion.client.at.size.SizeDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.FilterCellData;
import com.xfashion.shared.SizeDTO;
import com.xfashion.shared.StyleDTO;

/**
 * Sorts ArticleTypeDTO objects alphabetically for categories, brands, styles, names, colors, sizes 
 * @author wp
 */
public class DefaultArticleTypeComparator implements IArticleTypeComparator {

	protected CategoryDataProvider categoryProvider;
	protected BrandDataProvider brandProvider;
	protected ColorDataProvider colorProvider;
	protected SizeDataProvider sizeProvider;
	
	protected CategoryKeyAccessor categoryKeyAccessor;
	protected BrandKeyAccessor brandKeyAccessor;
	protected ColorKeyAccessor colorKeyAccessor;

	public DefaultArticleTypeComparator() {
		this.categoryKeyAccessor = new CategoryKeyAccessor();
		this.brandKeyAccessor = new BrandKeyAccessor();
		this.colorKeyAccessor = new ColorKeyAccessor();
	}
	
	public CategoryDataProvider getCategoryProvider() {
		return categoryProvider;
	}

	public void setCategoryProvider(CategoryDataProvider categoryProvider) {
		this.categoryProvider = categoryProvider;
	}

	public BrandDataProvider getBrandProvider() {
		return brandProvider;
	}

	public void setBrandProvider(BrandDataProvider brandProvider) {
		this.brandProvider = brandProvider;
	}

	public ColorDataProvider getColorProvider() {
		return colorProvider;
	}

	public void setColorProvider(ColorDataProvider colorProvider) {
		this.colorProvider = colorProvider;
	}

	public SizeDataProvider getSizeProvider() {
		return sizeProvider;
	}

	public void setSizeProvider(SizeDataProvider sizeProvider) {
		this.sizeProvider = sizeProvider;
	}

	@Override
	public int compare(ArticleTypeDTO at0, ArticleTypeDTO at1) {
		int cmp = 0;
		if ((cmp = compareWithProvider(categoryProvider, categoryKeyAccessor, at0, at1)) != 0) {
			return cmp;
		}
		if ((cmp = compareWithProvider(brandProvider, brandKeyAccessor, at0, at1)) != 0) {
			return cmp;
		}
		if ((cmp = compareStyles(at0, at1)) != 0) {
			return cmp;
		}
		if ((cmp = compareNames(at0, at1)) != 0) {
			return cmp;
		}
		if ((cmp = compareWithProvider(colorProvider, colorKeyAccessor, at0, at1)) != 0) {
			return cmp;
		}
		if ((cmp = compareSizes(at0, at1)) != 0) {
			return cmp;
		}
		return 0;
	}

	protected int compareStyles(ArticleTypeDTO at0, ArticleTypeDTO at1) {
		if (categoryProvider == null || categoryProvider.getAllItems() == null || categoryProvider.getAllItems().size() == 0) {
			return 0;
		}

		String key0 = at0.getStyleKey();
		String key1 = at1.getStyleKey();
		if (key0 == null && key1 == null) {
			return 0;
		}
		if (key0 == null) {
			return -1;
		}
		if (key1 == null) {
			return 1;
		}

		StyleDTO style0 = categoryProvider.resolveStyle(key0);
		StyleDTO style1 = categoryProvider.resolveStyle(key1);
		if (style0 == null && style1 == null) {
			return 0;
		}
		if (style0 == null) {
			return -1;
		}
		if (style1 == null) {
			return 1;
		}
		
		return style0.getName().compareTo(style1.getName());
	}

	protected int compareSizes(ArticleTypeDTO at0, ArticleTypeDTO at1) {
		if (sizeProvider == null || sizeProvider.getAllItems() == null || sizeProvider.getAllItems().size() == 0) {
			return 0;
		}
		
		String key0 = at0.getSizeKey();
		String key1 = at1.getSizeKey();
		if (key0 == null && key1 == null) {
			return 0;
		}
		if (key0 == null) {
			return -1;
		}
		if (key1 == null) {
			return 1;
		}

		SizeDTO size0 = sizeProvider.resolveData(key0);
		SizeDTO size1 = sizeProvider.resolveData(key1);
		if (size0 == null && size1 == null) {
			return 0;
		}
		if (size0 == null) {
			return -1;
		}
		if (size1 == null) {
			return 1;
		}
		
		int index0 = sizeProvider.getAllItems().indexOf(size0);
		int index1 = sizeProvider.getAllItems().indexOf(size1);
		return index0 - index1;
	}
	
	protected int compareNames(ArticleTypeDTO at0, ArticleTypeDTO at1) {
		if (at0.getName() == null && at1.getName() == null) {
			return 0;
		}
		if (at0.getName() == null) {
			return -1; 
		}
		if (at1.getName() == null) {
			return 1;
		}
		return at0.getName().compareTo(at1.getName());
	}

	protected int compareWithProvider(FilterDataProvider<? extends FilterCellData> provider, AttributeAccessor<String> accessor, ArticleTypeDTO at0, ArticleTypeDTO at1) {
		if (provider == null || provider.getAllItems() == null || provider.getAllItems().size() == 0) {
			return 0;
		}

		String key0 = accessor.getAttribute(at0);
		String key1 = accessor.getAttribute(at1);
		if (key0 == null && key1 == null) {
			return 0;
		}
		if (key0 == null) {
			return -1;
		}
		if (key1 == null) {
			return 1;
		}
		
		FilterCellData data0 = provider.resolveData(key0);
		FilterCellData data1 = provider.resolveData(key1);
		if (data0 == null && data1 == null) {
			return 0;
		}
		if (data0 == null) {
			return -1;
		}
		if (data1 == null) {
			return 1;
		}
		
		return data0.getName().compareTo(data1.getName());
	}
	
}
