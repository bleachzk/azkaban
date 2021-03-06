/*
 * Copyright 2012 LinkedIn Corp.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package azkaban.webapp.servlet.hdfsviewer;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Reads a image file if the file size is not larger than {@value #MAX_IMAGE_FILE_SIZE}.
 * 
 * @author ximeng
 */

public class ImageFileViewer extends HdfsFileViewer {

	private static Logger logger = Logger.getLogger(ImageFileViewer.class);
	private static final long MAX_IMAGE_FILE_SIZE = 10485760L;

	private HashSet<String> acceptedSuffix;

	public ImageFileViewer() {
		final String[] imageSuffix = {
			".jpg", ".jpeg", ".tif", ".tiff", ".png", ".gif", ".bmp", ".svg"
		};
		acceptedSuffix = new HashSet<String>(Arrays.asList(imageSuffix));
	}

	public Set<Capability> getCapabilities(FileSystem fs, Path path) {
		String fileName = path.getName();
		int pos = fileName.lastIndexOf('.');
		if (pos < 0) {
			return EnumSet.noneOf(Capability.class);
		}

		String suffix = fileName.substring(pos).toLowerCase();
		if (acceptedSuffix.contains(suffix)) {
			long len = 0;
			try {
				len = fs.getFileStatus(path).getLen();
			}
			catch (IOException e) {
				e.printStackTrace();
				return EnumSet.noneOf(Capability.class);
			}

			if (len <= MAX_IMAGE_FILE_SIZE) {
				return EnumSet.of(Capability.READ);
			}
		}

		return EnumSet.noneOf(Capability.class);
	}

	public void displayFile(FileSystem fs,
			Path path,
			OutputStream outputStream,
			int startLine,
			int endLine) throws IOException {

		if (logger.isDebugEnabled()) {
			logger.debug("read in image file");
		}

		InputStream inputStream = null;
		try {
			inputStream = new BufferedInputStream(fs.open(path));
			BufferedOutputStream output = new BufferedOutputStream(outputStream);
			long outputSize = 0L;
			byte[] buffer = new byte[16384];
			int len;
			while ((len = inputStream.read(buffer)) != -1) {
				output.write(buffer, 0, len);
				outputSize += len;
				if (outputSize > MAX_IMAGE_FILE_SIZE) {
					break;
				}
			}
			output.flush();
		}
		finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
}
