package org.sample.shared;

import java.io.Serializable;

public class FileSliceResponse implements Serializable {

	private String fileName;				//Filename matching this response
	private long sequenceID;                //Sequence ID of this slice, starts at 1, maps to sending sequence ID
	private int sliceProgress;              //From 0 to 100, indicates the progress upload, 100 is complete, -1 is failure
	private String responseMessage;    		//message to sender

	public FileSliceResponse()
	{
    		fileName = "";
	    	sequenceID = 0;
	    	sliceProgress = -1;
	    	responseMessage = "<html></html>";
	}
    
	public long getSequenceID() {
		return sequenceID;
	}

	public void setSequenceID(long sequenceID) {
		this.sequenceID = sequenceID;
	}

	public int getSliceProgress() {
		return sliceProgress;
	}

	public void setSliceProgress(int sliceProgress) {
		this.sliceProgress = sliceProgress;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3353092623156561638L;

}
