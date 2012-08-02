package com.xfashion.client.img;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
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
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.shared.ArticleTypeImageDTO;

public class ImageManagementPopup implements HasSelectionHandlers<ArticleTypeImageDTO> {

    private final int BATCH_SIZE = 15;

	private ImageUploadServiceAsync imageUploadService = (ImageUploadServiceAsync) GWT.create(ImageUploadService.class);
	
	private List<SelectionHandler<ArticleTypeImageDTO>> selectionHandlers = null;
	
	private DecoratedPopupPanel popup;
	
	private ListBox imagesListBox;
	
	private FormPanel uploadForm;
	
	private Button uploadButton;
	
	private TextBox imageNameTextBox;
	
	private Image articleImage;
	
	private List<ArticleTypeImageDTO> articleTypeImages;
	
	protected ErrorMessages errorMessages = GWT.create(ErrorMessages.class);
	
	public ImageManagementPopup() {
		articleTypeImages = new ArrayList<ArticleTypeImageDTO>();
	}
	
	public void show() {
		if (popup != null && popup.isShowing()) {
			return;
		}
		popup = createPopup();
		readArticleTypeImages();
		popup.center();
	}
	
	public void hide() {
		if (popup != null && popup.isShowing()) {
			popup.hide();
			popup.clear();
			articleTypeImages.clear();
			popup = null;
		}
	}
	
	private void readArticleTypeImages() {
		articleTypeImages.clear();
		imagesListBox.clear();
		readArticleTypeImages(0);
	}
	
	private void readArticleTypeImages(final int from) {
		AsyncCallback<List<ArticleTypeImageDTO>> callback = new AsyncCallback<List<ArticleTypeImageDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage() + " - " + caught.getStackTrace());
			}
			@Override
			public void onSuccess(List<ArticleTypeImageDTO> result) {
				if (result != null && result.size() != 0) {
					articleTypeImages.addAll(result);
					for (ArticleTypeImageDTO dto : result) {
						imagesListBox.addItem(dto.getName());
					}
					imagesListBox.setEnabled(true);
					Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand () {
						public void execute () {
				        	readArticleTypeImages(from + BATCH_SIZE);
				        }
				    });
				}
			}
		};
		imageUploadService.readArticleTypeImages(from, from + BATCH_SIZE, callback);
	}
	
	private DecoratedPopupPanel createPopup() {
		final DecoratedPopupPanel p = new DecoratedPopupPanel();

		DockPanel mainPanel = new DockPanel();
		
		Panel headerPanel = createHeaderPanel();
		Panel leftPanel = createLeftPanel();
		Panel rightPanel = createRightPanel();
		Panel navPanel = createNavPanel();
		mainPanel.add(headerPanel, DockPanel.NORTH);
		mainPanel.add(navPanel, DockPanel.SOUTH);
		mainPanel.add(leftPanel, DockPanel.WEST);
		mainPanel.add(rightPanel, DockPanel.EAST);

		p.add(mainPanel);

		return p;
	}

	private Panel createLeftPanel() {
		VerticalPanel vp = new VerticalPanel();
		Panel imageList = createImageListPanel();
		vp.add(imageList);
		return vp;
	}

	private Panel createRightPanel() {
		final VerticalPanel vp = new VerticalPanel();
		Panel imagePanel = createImagePanel();
		Panel imageUpload = createImageUploadPanel();
		vp.add(imagePanel);
		vp.add(imageUpload);
		return vp;
	}
	
	private Panel createHeaderPanel() {
		final SimplePanel sp = new SimplePanel();
		Label header = new Label("Artikel Bilder");
		header.setStyleName("dialogHeader");
		sp.add(header);
		return sp;
	}
	
	private Panel createImageListPanel() {
		VerticalPanel vp = new VerticalPanel();
		imagesListBox = new ListBox(false);
		imagesListBox.setVisibleItemCount(30);
		imagesListBox.setWidth("400px");
		imagesListBox.setEnabled(false);
		imagesListBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				String url = articleTypeImages.get(imagesListBox.getSelectedIndex()).getImageUrl();
				articleImage.setUrl(url + ArticleTypeImageDTO.IMAGE_OPTIONS_BIG);
			}
		});
		vp.add(imagesListBox);
		return vp;
	}

	private Panel createImageUploadPanel() {
		uploadForm = new FormPanel();
		uploadForm.setAction("/img/imageupload");
		uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		uploadForm.setMethod(FormPanel.METHOD_POST);

		VerticalPanel vp = new VerticalPanel();
		uploadForm.add(vp);

		final FileUpload fileUpload = new FileUpload();
		fileUpload.setName("upload");
		vp.add(fileUpload);
		
		HorizontalPanel namePanel = new HorizontalPanel();
		Label imageNameLabel = new Label("Bildname:");
		imageNameTextBox = new TextBox();
		imageNameTextBox.setName("imageName");
		imageNameTextBox.setStyleName("baseInput");
		namePanel.add(imageNameLabel);
		namePanel.add(imageNameTextBox);
		vp.add(namePanel);

		uploadButton = new Button("Hochladen", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (imageNameTextBox.getValue() != null && imageNameTextBox.getValue().length() > 0) {
					uploadButton.setEnabled(false);
					uploadForm.submit();
				}
			}
		});
		uploadButton.setEnabled(false);
		vp.add(uploadButton);

		requestUploadUrl();

		fileUpload.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				String filename = extractFilename(fileUpload.getFilename());
				imageNameTextBox.setValue(filename);
			}
		});
		
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
						articleTypeImages.add(0, result);
						imagesListBox.insertItem(result.getName(), 0);
						imageNameTextBox.setValue("");
						imagesListBox.setSelectedIndex(0);
						articleImage.setUrl(result.getImageUrl() + ArticleTypeImageDTO.IMAGE_OPTIONS_BIG);
						requestUploadUrl();
					}
					@Override
					public void onFailure(Throwable caught) {
						// We probably want to do something here
					}
				};
				imageUploadService.createArticleTypeImage(ati, callback);
			}
		});

		return uploadForm;
	}
	
	private String extractFilename(String path) {
		if (path == null || path.length() == 0) {
			return null;
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

	private Panel createNavPanel() {
		HorizontalPanel nav = new HorizontalPanel();

		Button closeButton = new Button("Abbrechen", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		nav.add(closeButton);

		Button takeOverButton = new Button("Bild übernehmen", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				handleImageSelection();
			}
		});
		nav.add(takeOverButton);

		return nav;
	}
	
	private void handleImageSelection() {
		if (imagesListBox.getSelectedIndex() < 0) {
			return;
		}
		ArticleTypeImageDTO ati = articleTypeImages.get(imagesListBox.getSelectedIndex());
		SelectionEvent.fire(this, ati);
		popup.hide();
	}

	private Panel createImagePanel() {
		SimplePanel sp = new SimplePanel();
		articleImage = new Image();
		articleImage.setWidth(400 + "px");
		articleImage.setHeight(400 + "px");
		sp.add(articleImage);
		return sp;
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
