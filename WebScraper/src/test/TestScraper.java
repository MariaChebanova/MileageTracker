package test;

import scraper.WebsiteScraper;

public class TestScraper {
	public static void main(String[] args) {
		WebsiteScraper w = new WebsiteScraper("");
		
		w.login("johndoe38577", "passwordA1.");
		System.out.println("points: " + w.getPoints() );
	}
}
