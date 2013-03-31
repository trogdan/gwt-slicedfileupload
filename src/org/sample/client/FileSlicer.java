package org.sample.client;

import java.util.ArrayList;
import java.util.List;

import org.sample.shared.FileSlice;
import org.vectomatic.file.Blob;
import org.vectomatic.file.File;
import org.vectomatic.file.FileList;
import org.vectomatic.file.FileReader;
import org.vectomatic.file.events.ErrorEvent;
import org.vectomatic.file.events.ErrorHandler;
import org.vectomatic.file.events.LoadEndEvent;
import org.vectomatic.file.events.LoadEndHandler;

public class FileSlicer {

	protected List<File> sliceQueue;
	protected FileReader reader;
	protected boolean isSlicing;
	
	protected long maxSliceSize = 0x100000l; //in bytes
	protected long sliceCurrentOffset = 0; //in bytes
	protected long currentSliceID = 0;
	
	protected FileSlice currentSlice;
	
	FileSlicer()
	{
		isSlicing = false;
		sliceQueue = new ArrayList<File>();
		
		// Create a file reader a and queue of files to read.
		// UI event handler will populate this queue by calling queueFiles()
		reader = new FileReader();
		reader.addLoadEndHandler(new LoadEndHandler() {
			/**
			 * This handler is invoked when FileReader.readAsText(),
			 * FileReader.readAsBinaryString() or FileReader.readAsArrayBuffer()
			 * successfully completes
			 */
			@Override
			public void onLoadEnd(LoadEndEvent event) {
				if (reader.getError() == null) {
					currentSlice.setSliceData(reader.getStringResult());
					
					SlicedUploadDemo.instance.uploadSlice(currentSlice);
					
					File currentFile = sliceQueue.get(0);
					
					if( sliceCurrentOffset == currentFile.getSize() )
					{
						isSlicing = false;
					}
					else
					{
						isSlicing = doNextSlice(sliceQueue.get(0));
					}
				
					if (!isSlicing) {
						sliceQueue.remove(0);
						sliceNextFile();
					}
					
				} else {
	                    System.out.println("FileReader error: " + reader.getError().toString());
	            }
			}
		});

		reader.addErrorHandler(new ErrorHandler() {
			/**
			 * This handler is invoked when FileReader.readAsText(),
			 * FileReader.readAsBinaryString() or FileReader.readAsArrayBuffer()
			 * fails
			 */
			@Override
			public void onError(ErrorEvent event) {
				if (sliceQueue.size() > 0) {
					
					//Stop slicing this file and remove it from the queue, go on
					//to the next file
					isSlicing = false;
					
					sliceQueue.remove(0);
					sliceNextFile();
				}
			}
		});
	}
	
	/**
	 * Adds a collection of file the queue and begin processing them
	 * @param files
	 * The file to process
	 */
	public void sliceFiles(FileList files) {
		for (File file : files) {
			sliceQueue.add(file);
		}
		// Start processing the queue
		sliceNextFile();
	}
	
	/**
	 * Slices the next file in the queue. 
	 */
	private void sliceNextFile() {
		if (sliceQueue.size() > 0) {
			
			File file = sliceQueue.get(0);
			
			sliceCurrentOffset = 0;
        	currentSliceID = 0;
        	
			if( doNextSlice(file) == false )
			{
				sliceNextFile();
			}
		}
	}
	
	private boolean doNextSlice(File file)
	{
		currentSliceID++;
		
		//Make currentSlice
		long sliceEndOffset = 0x0;

        //If the remainder of the file is smaller than the max currentSlice, read the rest
        if( sliceCurrentOffset + maxSliceSize >= file.getSize()  )
        {
        	sliceEndOffset = (long)file.getSize();
        }
        else
        {
        	sliceEndOffset = sliceCurrentOffset + maxSliceSize;
        }

		currentSlice = new FileSlice();
        currentSlice.setFileName(file.getName());
        currentSlice.setSequenceID(currentSliceID);
        currentSlice.setFileOffset(sliceCurrentOffset);
		currentSlice.setFileSize(file.getSize());
		currentSlice.setSliceSize(sliceEndOffset - sliceCurrentOffset);
		
		Blob blob = file.slice(sliceCurrentOffset, sliceEndOffset);

        if( blob != null ) {
			reader.readAsBinaryString(blob);

			// Send currentSlice
			sliceCurrentOffset = sliceEndOffset;

			return true;
		} 
        
        return false;    
	}
}
