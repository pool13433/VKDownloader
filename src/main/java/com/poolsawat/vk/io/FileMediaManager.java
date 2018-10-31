package com.poolsawat.vk.io;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class FileMediaManager {

	private final String HOST_URL = "https://vk.com";

	private void saveFileOnCouldToLocalDisk(String httpURL, String name) throws Exception {
		try {
			File resultFile = new File("./resources/images/" + name);
			FileUtils.copyURLToFile(new URL(httpURL), resultFile);
			System.out.println("Clone To Local Drive Success");
		} catch (Exception e) {
			throw e;
		}
	}

	private List<String> getImagesSource(String galleryURL) throws IOException{
		//String galleryURL = "https://vk.com/album-169522879_256553843";
		 try {
			 Document doc = Jsoup.connect(galleryURL).get();
			 doc.select("#photos_container_photos").forEach(el -> {
				 //System.out.println("section: " + el);
				 el.getElementsByTag("div").forEach(div ->{
					 String href  = div.getElementsByTag("a").attr("href");					 
					 System.out.println("href ::=="+href);								 
					 try {
						 Document _doc = Jsoup.connect(galleryURL).get();
						FileUtils.writeStringToFile(new File("./resources/htmls"+href), _doc.toString());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
					/* try {
						List<String> imagesURL = this.getElementImages(HOST_URL+href);
						for (String imageURL : imagesURL) {
							System.out.println("imgURL ::=="+imageURL);
							String imageName  = this.getURLEndFileName(imageURL);
							this.saveFileOnCouldToLocalDisk(imageURL,imageName);
						}						
					} catch (Exception e) {
						e.printStackTrace();
					}*/
				 });				
			 });
			 return null;
		} catch (IOException e) {
			throw e;
		}
	}

	private String getURLEndFileName(String imageURL) throws MalformedURLException {
		try {
			System.out.println("imageURL::=="+imageURL);
			URL url = new URL(imageURL);
			System.out.println(FilenameUtils.getBaseName(url.getPath())); // -> file
			System.out.println(FilenameUtils.getExtension(url.getPath())); // -> xml
			System.out.println(FilenameUtils.getName(url.getPath())); // -> file.xml
			return FilenameUtils.getName(url.getPath());
		} catch (MalformedURLException e) {
			throw e;
		}
	}

	private List<String> getElementImages(String imageURL) throws Exception {
		
		List<String> images = new ArrayList<>();
		try {
			System.out.println("imageURL ::=="+imageURL);
			Document doc = Jsoup.connect(imageURL).get();
			doc.getElementsByTag("a").forEach(img->{
				System.out.println("img=>html::=="+img);
				String imgHREF = img.attr("src");
				if(imgHREF.indexOf(".jpg") != -1) {					
					images.add(imgHREF);
				}				
			});
			return images;
		} catch (Exception e) {
			throw e;
		}
	}

	public static void main(String[] args) {
		FileMediaManager fmManage = new FileMediaManager();
		try {
			String sourceURL = "https://vk.com/album-169522879_256553843";
			fmManage.getImagesSource(sourceURL);
			/*String sampleURL = "https://pp.userapi.com/c845219/v845219783/e0a55/fmt-U4yCnKI.jpg";
			String sampleName = fmManage.getURLEndFileName(sampleURL);
			fmManage.saveFileOnCouldToLocalDisk(sampleURL, sampleName);*/
			System.out.println("Clone To Local Drive Success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
