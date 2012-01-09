package org.simplesql.cache;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class FileCache {

	
	final String fileName;
	final String indexFileName;
	
	RandomAccessFile file;
	FileChannel channel;
	
	MappedByteBuffer mbb;
	
	HashFunction hashFunction;
	
	
	/**
	 * A record contains 4 bytes, start row
	 */
	final int recordSize = 4;

	int maxPos;
	
	int size;
	
	public FileCache(String fileName, int size) throws IOException {
		super();
		this.fileName = fileName;
		this.indexFileName = "_" + fileName;
		
		new File(indexFileName).createNewFile();
		file = new RandomAccessFile(new File(indexFileName), "rw");
		channel = file.getChannel();
		
		this.size = size;
		this.maxPos = size - recordSize;
		
		mbb = channel.map(MapMode.READ_WRITE, 0, size);
		mbb.load();
		
		hashFunction = Hashing.murmur3_128();
	}
	
	
	public final void put(byte[] key, byte[] value) throws IOException{
		int row = 1 - hashFunction.hashBytes(key).asInt() % size + recordSize;
		if(row < 0){
			row = row * -1;
		}

		if(row > maxPos){
			
			row = maxPos;
		}
		
		//add to store file.
		int start = 100;
		//here start should point to the starting location
		//of the storage bucket
		
		
		mbb.putInt(row, 100);
		
	}
	
	public byte[] get(byte[] key){
		int row = hashFunction.hashBytes(key).asInt() % size * recordSize;
		
		int start = mbb.getInt(row);

		//here we should use start to retreive the value from the storage bucket located
		//at position == start
		
		return new byte[]{(byte)start};
	}
	
	public void remove(){
		new File(indexFileName).delete();
	}
	
}
