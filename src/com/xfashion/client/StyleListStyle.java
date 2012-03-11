package com.xfashion.client;

import com.google.gwt.resources.client.CssResource.ImportedWithPrefix;
import com.google.gwt.user.cellview.client.CellList;

@ImportedWithPrefix("style")
public interface StyleListStyle extends CellList.Style {

   String DEFAULT_CSS = "styleList.css";

   String cellListCell();

}