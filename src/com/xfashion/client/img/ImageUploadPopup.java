package com.xfashion.client.img;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.ArticleTypeImageDTO;

public class ImageUploadPopup implements HasSelectionHandlers<ArticleTypeImageDTO> {

	public static final String PANEL_WIDTH = "300px"; 
	
	private ImageUploadServiceAsync imageUploadService = (ImageUploadServiceAsync) GWT.create(ImageUploadService.class);
	
	private List<SelectionHandler<ArticleTypeImageDTO>> selectionHandlers = null;

	DecoratedPopupPanel popup;
	
	protected FormPanel uploadForm;
	protected FileUpload fileUpload;
	protected TextBox imageNameTextBox;
	protected Button uploadButton;

	protected TextMessages textMessages;
	
	public ImageUploadPopup() {
		textMessages = GWT.create(TextMessages.class);
	}
	
	public void show() {
		if (popup == null) {
			createPopup();
		}
		if (popup.isShowing()) {
			return;
		}
		clear();
		popup.center();
	}
	
	public void hide() {
		if (popup != null && popup.isShowing()) {
			popup.hide();
		}
	}
	
	public void clear() {
		requestUploadUrl();
		// imageNameFromFileName();
		uploadButton.setEnabled(true);
	}
	
	private DecoratedPopupPanel createPopup() {
		popup = new DecoratedPopupPanel();
		popup.add(createImageUploadPanel());
		
		return popup;
	}
	
	private Panel createImageUploadPanel() {
		
		uploadForm = new FormPanel();
		uploadForm.setAction("/img/imageupload");
		uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		uploadForm.setMethod(FormPanel.METHOD_POST);

		VerticalPanel vp = new VerticalPanel();
		uploadForm.add(vp);

		vp.add(createFileUpload());
		vp.add(createImageNamePanel());
		vp.add(createNavPanel());

		uploadForm.addSubmitHandler(new FormPanel.SubmitHandler() {
			@Override
			public void onSubmit(SubmitEvent event) {
				if (imageNameTextBox.getValue() != null && imageNameTextBox.getValue().length() > 0) {
					uploadButton.setEnabled(false);
				}
			}
		});
		
		uploadForm.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				String key = event.getResults();
				ArticleTypeImageDTO ati = new ArticleTypeImageDTO();
				ati.setBlobKey(key);
				ati.setName(imageNameTextBox.getValue());
				
				AsyncCallback<ArticleTypeImageDTO> callback = new AsyncCallback<ArticleTypeImageDTO>() {
					public void onSuccess(ArticleTypeImageDTO result) {
						selectImage(result);
					}
					@Override
					public void onFailure(Throwable caught) {
						Xfashion.fireError(caught.getMessage());
					}
				};
				imageUploadService.createArticleTypeImage(ati, callback);
			}
		});

		return uploadForm;
	}

	private void selectImage(ArticleTypeImageDTO image) {
		SelectionEvent.fire(this, image);
		popup.hide();
	}

	private void requestUploadUrl() {
		imageUploadService.createUploadUrl(new AsyncCallback<String>() {
			public void onSuccess(String result) {
				uploadForm.setAction(result);
				uploadButton.setEnabled(true);
			}
			@Override
			public void onFailure(Throwable caught) {
				// We probably want to do something here
			}
		});
	}

	private void imageNameFromFileName() {
		String filename = extractFilename(fileUpload.getFilename());
		imageNameTextBox.setValue(filename);
	}

	private String extractFilename(String path) {
		if (path == null || path.length() == 0) {
			return "";
		}
		int idx = path.lastIndexOf("\\");
		if (idx == -1) {
			idx = path.lastIndexOf("/");
		}
		if (idx == -1) {
			return path;
		} else {
			return path.substring(idx + 1);
		}
	}
	
	private Panel createNavPanel() {
		HorizontalPanel hp = new HorizontalPanel();
		hp.setWidth(PANEL_WIDTH);
		hp.add(createUploadButton());
		hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		hp.add(createCancelButton());
		return hp;
	}
		
	private Button createCancelButton() {
		Button cancelButton = new Button(textMessages.cancel(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		return cancelButton;
	}
	
	private Button createUploadButton() {
		uploadButton = new Button(textMessages.uploadImage(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (imageNameTextBox.getValue() != null && imageNameTextBox.getValue().length() > 0) {
					uploadButton.setEnabled(false);
					uploadForm.submit();
				}
			}
		});
		uploadButton.setEnabled(false);
		return uploadButton;
	}

	private HorizontalPanel createImageNamePanel() {
		HorizontalPanel namePanel = new HorizontalPanel();
		namePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		Label imageNameLabel = new Label(textMessages.imageName() + ":");
		imageNameTextBox = new TextBox();
		imageNameTextBox.setName("imageName");
		imageNameTextBox.setWidth("200px");
		namePanel.add(imageNameLabel);
		namePanel.add(imageNameTextBox);
		return namePanel;
	}

	private FileUpload createFileUpload() {
		fileUpload = new FileUpload();
		fileUpload.setName("upload");
		fileUpload.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				imageNameFromFileName();
			}
		});
		fileUpload.setWidth(PANEL_WIDTH);
		
		return fileUpload;
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		if (event instanceof SelectionEvent<?>) {
			@SuppressWarnings("unchecked")
			SelectionEvent<ArticleTypeImageDTO> e = (SelectionEvent<ArticleTypeImageDTO>) event;
			if (selectionHandlers != null) {
				for (SelectionHandler<ArticleTypeImageDTO> h : selectionHandlers) {
					h.onSelection(e);
				}
			}
		}
	}

	@Override
	public HandlerRegistration addSelectionHandler(final SelectionHandler<ArticleTypeImageDTO> handler) {
		if (selectionHandlers == null) {
			selectionHandlers = new ArrayList<SelectionHandler<ArticleTypeImageDTO>>();
		}
		selectionHandlers.add(handler);
		return new HandlerRegistration() {
			@Override
			public void removeHandler() {
				selectionHandlers.remove(handler);
			}
		};
	}
}
