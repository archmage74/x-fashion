package com.xfashion.pdf;

import java.io.InputStream;

import com.google.gdata.aeuploader.BlobProvider;

public class ByteArrayBlobProvider implements BlobProvider {

	private byte[] bytes;
	
	private String type;
	
	public ByteArrayBlobProvider(byte[] bytes, String contentType) {
		this.bytes = bytes;
		this.type = contentType;
	}
	
	@Override
	public long getSize() {
		return bytes.length;
	}

	@Override
	public InputStream getStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() {
		return type;
	}

}
