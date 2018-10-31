package com.poolsawat.vk.io;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class PhantomJsUtils {
	public static Document renderPage(String filePath) {
        WebDriver ghostDriver = new PhantomJSDriver();
        try {
            ghostDriver.get(filePath);
            return Jsoup.parse(ghostDriver.getPageSource());
        } finally {
            ghostDriver.quit();
        }
    }
}
