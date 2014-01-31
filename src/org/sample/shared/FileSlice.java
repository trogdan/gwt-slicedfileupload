package org.sample.shared;

import java.io.Serializable;

public class FileSlice implements Serializable {

	private long sequenceID;        //Sequence ID of this slice, starts at 1
	private String fileName;		//Filename of the source file
	private long fileSize;          //Total size of the file in bytes
	private long fileOffset;        //Offset of this slice in bytes from the beginning of the file
	private long sliceSize;         //Size of this slice in bytes
	private String sliceData;       //Data of the slice

 	public FileSlice()
 	{
    		sequenceID = 0;
    		fileName = "";
        	fileSize = 0;
        	fileOffset = 0;
        	sliceData = "";
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public long getSequenceID() {
		return sequenceID;
	}

	public void setSequenceID(long sequenceID) {
		this.sequenceID = sequenceID;
	}

	public long getFileOffset() {
		return fileOffset;
	}

	public void setFileOffset(long fileOffset) {
		this.fileOffset = fileOffset;
	}

	public long getSliceSize() {
		return sliceSize;
	}

	public void setSliceSize(long sliceSize) {
		this.sliceSize = sliceSize;
	}

	public String getSliceData() {
		return sliceData;
	}

	public void setSliceData(String sliceData) {
		this.sliceData = sliceData;
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
	private static final long serialVersionUID = 4989573836144297655L;

}
