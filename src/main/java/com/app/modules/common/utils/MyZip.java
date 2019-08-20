package com.app.modules.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class MyZip {

	public static class MemoryFile {
		private String fileName;
		private byte[] contents;

		public MemoryFile(String fileName, byte[] contents) {
			this.fileName = fileName;
			this.contents = contents;
		}
	}


	public static byte[] createZipByteArray(List<MemoryFile> memoryFiles) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
		try {
			for (MemoryFile memoryFile : memoryFiles) {
				ZipEntry zipEntry = new ZipEntry(memoryFile.fileName);
				zipOutputStream.putNextEntry(zipEntry);
				zipOutputStream.write(memoryFile.contents);
				zipOutputStream.closeEntry();
			}
		} finally {
			zipOutputStream.close();
		}
		return byteArrayOutputStream.toByteArray();
	}

}