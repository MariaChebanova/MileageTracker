package test;

import scraper.WebsiteScraper;

public class TestScraper {
	public static void main(String[] args) {
		WebsiteScraper w = new WebsiteScraper();
		
		w.login("russellp211@yahoo.com", "");
		System.out.println("points: " + w.getPoints() );
	}
}
