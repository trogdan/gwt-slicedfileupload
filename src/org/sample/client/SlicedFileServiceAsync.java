package org.sample.client;

import org.sample.shared.FileSlice;
import org.sample.shared.FileSliceResponse;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>SlicedFileService</code>.
 */
public interface SlicedFileServiceAsync {
	void sendSlice(FileSlice input, AsyncCallback<FileSliceResponse> callback)
			throws IllegalArgumentException;
}
