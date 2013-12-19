package util.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

import org.apache.tools.zip.ZipEntry;

public class ZipUtil {
	
	public boolean zip(List<String> inputFileNameList, String outputPath) throws Exception {
		
		if(inputFileNameList.size()>0)
		{
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(outputPath));
			for(String inputFileName : inputFileNameList)
			{
				File inFile = new File(inputFileName);
				zipFile(inFile, zos, "");
			}
	        zos.close();
	        return true;
		}
		return false;
	}
	public void zip(String inputFileName, String outputPath) throws Exception {
		
		File inFile = new File(inputFileName);
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(outputPath));
        zipFile(inFile, zos, "");
        zos.close();
	}

	public  void zipFile(File inFile, ZipOutputStream zos, String dir)
			throws IOException {
		if (inFile.isDirectory()) {
			File[] files = inFile.listFiles();
			for (File file : files)
				zipFile(file, zos, dir + "\\" + inFile.getName());
		} else {
			String entryName = null;
			if (!"".equals(dir))
				entryName = dir + "\\" + inFile.getName();
			else
				entryName = inFile.getName();
			ZipEntry entry = new ZipEntry(entryName);
			zos.putNextEntry(entry);
			InputStream is = new FileInputStream(inFile);
			int len = 0;
			while ((len = is.read()) != -1)
				zos.write(len);
			is.close();
		}

	}

	public static void main(String[] temp) throws Exception {
		/*ZipUtil book = new ZipUtil();
		try {
			book.zip("C:\\Users\\Administrator\\Desktop\\IMG_0916.JPG",
					"C:\\Users\\Administrator\\Desktop\\zzz.zip");
			book.zip("C:\\Users\\Administrator\\Desktop\\IMG_0915.JPG",
					"C:\\Users\\Administrator\\Desktop\\zzz.zip");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		*/ 
		List<String> inList = new ArrayList<String>();
		inList.add("C:\\Users\\Administrator\\Desktop\\IMG_0916.JPG");
		inList.add("C:\\Users\\Administrator\\Desktop\\IMG_0915.JPG");
		ZipUtil z = new ZipUtil();
		z.zip(inList, "C:\\Users\\Administrator\\Desktop\\22.zip");
	}
}