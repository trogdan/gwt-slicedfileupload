package org.sample.client;

import org.vectomatic.file.FileUploadExt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Widget;

public class FileUploadComposite extends Composite {

	private static FileUploadCompositeUiBinder uiBinder = GWT
			.create(FileUploadCompositeUiBinder.class);

	interface FileUploadCompositeUiBinder extends
			UiBinder<Widget, FileUploadComposite> {
	}

	public FileUploadComposite() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField Button sendButton;
	@UiField FileUploadExt fileUpload;
	@UiField HTMLPanel htmlPanel;
	@UiField HTML responseHTML;
	@UiField IntegerBox progressBox;

	public Integer getProgress() {
		return progressBox.getValue();
	}

	public void setProgress(Integer progress) {
		this.progressBox.setValue(progress);
	}

	public String getResponseHTMLText() {
		return responseHTML.getText();
	}

	public void setResponseHTMLText(String html) {
		this.responseHTML.setHTML(html);
	}

	@UiHandler("sendButton")
	void onSendButtonClick(ClickEvent event) {
		SlicedUploadDemo.instance.sliceFiles(fileUpload.getFiles());
	}
}
