package project.master.fw.sh.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UpLoadFileFactory {

	public void saveFile(InputStream in, String path, String name) throws IOException {
		File file = new File(path + "/" + name);
		if (!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		byte[] b = new byte[1024];
		int len;
		while ((len = in.read(b)) != -1) {
			fileOutputStream.write(b, 0, len);
		}
		fileOutputStream.flush();
		fileOutputStream.close();
		in.close();
	}

	public void moveFile(String oldPath, String newPath) {
		File old = new File(oldPath);
		old.renameTo(new File(newPath));
	}

	public void deleteFile(String path, String... fileName) {
		if (null == fileName || null == path)
			return;
		File file;
		for (String str : fileName) {
			file = new File(path + str);
			if (file.exists())
				file.delete();
		}
	}

	public void deleteSignleFile(String path, String fileName) {
		if (null == fileName || null == path)
			return;
		File file;
		file = new File(path + fileName);
		if (file.exists())
			file.delete();
	}

	public void deleteFile(String... filePath) {
		if (null == filePath)
			return;
		File file;
		for (String str : filePath) {
			file = new File(str);
			if (file.exists())
				file.delete();
		}
	}

}
