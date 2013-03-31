package org.sample.client;

import org.sample.shared.FileSlice;
import org.vectomatic.file.FileList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SlicedUploadDemo implements EntryPoint {
	
	public static SlicedUploadDemo instance;
	
	private FileSlicer slicer = new FileSlicer();
	private SlicedFileServiceWrapper service = new SlicedFileServiceWrapper();
	private FileUploadComposite composite = new FileUploadComposite();
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		instance = this;
		composite = new FileUploadComposite();
		RootPanel.get().add(composite);
	}
	
	public void sliceFiles(FileList files)
	{
		slicer.sliceFiles(files);
	}
	
	public void uploadSlice(FileSlice slice)
	{
		service.uploadSlice(slice);
	}
	
	public void setResponseHTML(String html)
	{
		composite.setResponseHTMLText(html);
	}
	
	public void setProgress(Integer progress)
	{
		composite.setProgress(progress);
	}
}
