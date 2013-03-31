package org.sample.client;

import org.sample.shared.FileSlice;
import org.sample.shared.FileSliceResponse;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SlicedFileServiceWrapper {
	
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	
	private SlicedFileServiceAsync uploadService;

    public void uploadSlice(FileSlice slice)
    {
	    if( uploadService == null )
	    {
	            uploadService = GWT.create(SlicedFileService.class);
	    }
	
	    AsyncCallback<FileSliceResponse> callback = new AsyncCallback<FileSliceResponse>() {
	
	            public void onFailure(Throwable caught) {
	                    GWT.log("Upload failure: " + caught.toString());
	            }
	
	            @Override
	            public void onSuccess(FileSliceResponse response) {
	
	                    if( response == null ) {
	                    	GWT.log("Empty response");
	                    }
	                    else
	                    {
	                    	GWT.log("Received response " + response.getSequenceID() + " for file " + response.getFileName());
	
	                    	SlicedUploadDemo.instance.setResponseHTML(response.getResponseMessage());
	                    	SlicedUploadDemo.instance.setProgress(Integer.valueOf(response.getSliceProgress()));
	                    }
	           }
	    };
	            
	    uploadService.sendSlice(slice, callback);
    }
}
