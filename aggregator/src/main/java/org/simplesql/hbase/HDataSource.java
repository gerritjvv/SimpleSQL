package org.simplesql.hbase;

import java.util.Iterator;

import org.simplesql.data.Cell;
import org.simplesql.data.DataSource;
import org.simplesql.schema.TableDef;
import org.simplesql.util.Bytes;

public class HDataSource implements DataSource{

	@Override
	public Iterator<Object[]> iterator() {
		return null;
	}

	@Override
	public long getEstimatedSize() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static final String createJavaSyntax(TableDef schema){
		
		
		
	}
	
	
	private static final String getKeyRead(Cell cell){
		Cell.SCHEMA schema = cell.getSchema();
		
		if(schema.equals(Cell.SCHEMA.BOOLEAN))
			return "Bytes.readBoolean(key, from)";
		else if(schema.equals(Cell.SCHEMA.BYTE))
			return "key[from]";
		else if(schema.equals(Cell.SCHEMA.DOUBLE))
			return "Bytes.readDouble(key, from)";
		else if(schema.equals(Cell.SCHEMA.LONG))
			return "Bytes.readLong(key, from)";
		else if(schema.equals(Cell.SCHEMA.INT))
			return "Bytes.readInt(key, from)";
		else if(schema.equals(Cell.SCHEMA.BOOLEAN))
			return "Bytes.readBoolean(key, from)";
		else if(schema.equals(Cell.SCHEMA.FLOAT))
			return "new byte[(byte)(from & 0xff), (byte)((from+1 >> 8) & 0xff)]";
		else if(schema.equals(Cell.SCHEMA.STRING)){
			int len = cell.getDefinedWidth();
			return "Bytes.readString(key, from, " + len + ")";
		}else{
			throw new RuntimeException("Cell type: " + schema + " not supported ");
		}
		
	}

	public static byte readByte(byte[] arr, int from){
		return arr[from];
	}
	
}
