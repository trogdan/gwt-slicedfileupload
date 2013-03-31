package org.sample.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.sample.client.SlicedFileService;
import org.sample.shared.FileSlice;
import org.sample.shared.FileSliceResponse;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SlicedFileServiceImpl extends RemoteServiceServlet implements
		SlicedFileService {

	private RandomAccessFile file;
	
	private long totalBytesReceived = 0;
	
	public synchronized FileSliceResponse sendSlice(FileSlice slice) throws IllegalArgumentException {
		GWT.log("Received slice " + slice.getSequenceID() + " for file " + slice.getFileName());
		
		try {
			// If this is the first slice, create (or erase) the existing file
			if (slice.getSequenceID() == 1) {
				totalBytesReceived = 0;
				
				File newFile = new File(slice.getFileName());

				if (newFile.exists()) {
					newFile.delete();
				}

				file = new RandomAccessFile(slice.getFileName(), "rw");
			}

			if (slice.getSliceSize() > 0) {
				// Reset the file length to allow for this chunk received.
				file.setLength(file.length() + slice.getSliceSize());
				// Seek to the offset and write the chunk data
				file.seek(slice.getFileOffset());
				file.writeBytes(slice.getSliceData());

				GWT.log("Wrote " + slice.getSliceSize() + " bytes to file "
						+ slice.getFileName());

				//And form a response
				FileSliceResponse response = new FileSliceResponse();
				response.setFileName(slice.getFileName());
				response.setSequenceID(slice.getSequenceID());
				
				if( file.length() != 0 )
				{
					response.setSliceProgress((int)Math.floor( ((double)file.length() / (double)slice.getFileSize()) * 100.0));
				}
				
				// If this is the last slice, close the file
				if (file.length() == slice.getFileSize()) 
				{
					file.close();
					
					response.setResponseMessage("<html><head /><body>Successful upload!!!</body></html>");
				}
				else
				{
					response.setResponseMessage("<html><head /><body>Working...</body></html>");
				}
				
				return response;
			}

		} 
		catch (FileNotFoundException e) {
			GWT.log(e.toString());
		}
		catch (IOException e) {
			GWT.log(e.toString());
		}
		
		return getErrorResponse(slice);
	}

	protected FileSliceResponse getErrorResponse(FileSlice slice)
	{
		FileSliceResponse response = new FileSliceResponse();
		response.setFileName(slice.getFileName());
		response.setSequenceID(slice.getSequenceID());
		response.setSliceProgress(-1);
		response.setResponseMessage("<html><head /><body>Failed upload</body></html>");
		
		return response;	
	}
}
