package org.sample.client;

import org.sample.shared.FileSlice;
import org.sample.shared.FileSliceResponse;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("slice")
public interface SlicedFileService extends RemoteService {
	FileSliceResponse sendSlice(FileSlice slice) throws IllegalArgumentException;
}
