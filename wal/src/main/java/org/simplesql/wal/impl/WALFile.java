package org.simplesql.wal.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.SyncFailedException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.simplesql.wal.EachClosure;
import org.simplesql.wal.WAL;

/**
 * 
 * Implements the WAL interface using a File System.<br/>
 * One single file is written and kept in hourly partitions.<br/>
 * This means that for each hour there will be one single unique file.<br/>
 */
public class WALFile implements WAL {

	private static final Logger LOG = Logger.getLogger(WALFile.class);

	CurrentFile writer = null;
	File file = null;
	final String baseDir;
	String baseName = "request.";
	DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH");

	public WALFile(String baseDir) throws IOException {
		this.baseDir = baseDir;
		newFile();
	}


	@Override
	public int eachLineFrom(int linePos, EachClosure<String> closure) throws IOException {
		
		BufferedReader buffReader = new BufferedReader(new FileReader(file));
		String line = null;
		int currentLine = 0;
		
		while( (line = buffReader.readLine()) != null ){
			if(currentLine >= linePos)
				closure.call(line, currentLine);
			
			currentLine++;
		}
		
		return currentLine;
	}
	
	/**
	 * Returns the current file name.
	 * 
	 * @return File
	 */
	public File getFile() {
		return file;
	}

	public void write(String[] lines) throws IOException {
		if (writer.isCurrentHour())
			newFile();

		writer.writeToCurrent(lines);
	}

	public void flush() throws IOException {
		writer.flush();
	}

	public void close() throws IOException {
		writer.close();
	}

	public void write(String line) throws IOException {
		if (writer.isCurrentHour())
			newFile();

		writer.writeToCurrent(line);
	}

	private final void newFile() throws IOException {

		Date date = new Date();
		String fileName = baseName + format.format(date) + ".log";

		file = new File(baseDir, fileName);
		
		file.getParentFile().mkdirs();

		if (!file.exists())
			file.createNewFile();
		
		if (!FileUtils.waitFor(file, 10)) {
			throw new IOException("Failed to create file "
					+ file.getAbsolutePath() + " in 10 seconds");
		}

				
		if (writer == null)
			writer = new CurrentFile(date.getTime(), new FileOutputStream(file,
					true));
		else {
			writer.changePrintWriter(new FileOutputStream(file, true));
		}

	}

	/**
	 * 
	 * Keeps the current PrintWriter object
	 * 
	 */
	private static final class CurrentFile {
		final TimeUnit MILLISECONDS = TimeUnit.MILLISECONDS;

		long time;
		FileOutputStream fileOut;

		FileDescriptor fd;

		public CurrentFile(long time, FileOutputStream fileOut)
				throws IOException {
			super();
			this.time = time;
			fd = fileOut.getFD();
			this.fileOut = fileOut;
		}

		public final void flush() throws IOException {
			fileOut.flush();
			try {
				fd.sync();
			} catch (SyncFailedException syncFailed) {
				;// ignore
			}
		}

		public final void close() throws IOException {
			fileOut.close();
		}

		public final void writeToCurrent(String[] lines) throws IOException {
			StringBuilder buff = new StringBuilder(100);
			for (int i = 0; i < lines.length; i++) {
				buff.append(lines[i]);
				buff.append('\n');
			}

			fileOut.write(buff.toString().getBytes("UTF-8"));
		}

		public final void writeToCurrent(String line) throws IOException {
			fileOut.write((line + '\n').getBytes("UTF-8"));
		}

		/**
		 * Change the print writer for the current one. The current PrintWriter
		 * will be flushed and closed.
		 * 
		 * @param updateWriter
		 * @throws IOException
		 */
		public final void changePrintWriter(FileOutputStream newFileOut)
				throws IOException {
			if (fileOut != null) {
				try {
					close();
				} catch (Throwable t) {
					LOG.error(t.toString(), t);
				}
			}

			fd = fileOut.getFD();
			time = System.currentTimeMillis();
			fileOut = newFileOut;
		}

		/**
		 * Return true if the
		 * 
		 * @return
		 */
		public final boolean isCurrentHour() {
			return (MILLISECONDS.toHours(System.currentTimeMillis()) - TimeUnit.MILLISECONDS
					.toHours(time)) == 0;
		}

	}


}
