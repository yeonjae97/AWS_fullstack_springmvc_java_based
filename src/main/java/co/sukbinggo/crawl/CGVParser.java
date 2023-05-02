package co.sukbinggo.crawl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator.AttributeKeyPair;

public class CGVParser {

	// dom탐색은
	public static void main(String[] args) throws MalformedURLException, IOException {
		String 슬램덩크 = "http://www.cgv.co.kr/movies/detail-view/default.aspx?midx=86720";
		String 매트릭스 = "http://www.cgv.co.kr/movies/detail-view/?midx=85541";
		// doc 형식으로 초기화
		Document doc = Jsoup.parse(new URL(매트릭스), 2000);

		String info = doc.selectFirst(".sect-story-movie").text();
		String engtitle = doc.selectFirst(".sect-base-movie .title p").text();

		Element ele = doc.selectFirst("#ctl00_PlaceHolderContent_Section_Still_Cut");
		Elements els = doc.select(".sect-base-movie .spec dt");

		// Document doc = Jsoup.parse(new
		// URL("http://www.cgv.co.kr/movies/"),2000);
		// Document doc = Jsoup.parse(new
		// URL("http://www.cgv.co.kr/movies/"),2000);
		// Document doc = Jsoup.parse(new
		// URL("http://www.cgv.co.kr/movies/detail-view/?midx=86720"),2000);
		// Document doc = Jsoup.parse(new
		// URL("http://www.cgv.co.kr/movies/detail-view/?midx=85541"),2000);
		// System.out.println(doc.selectFirst(".sect-base-movie .title
		// p").text());
		// System.out.println(doc.selectFirst(".sect-story-movie").text());
		// System.out.println(doc.select(".sect-story-movie").size());

		// Element ele = doc.selectFirst(".sect-movie-chart li");

		// System.out.println(els.contains("감독"));

		for (Element e : els) {
			// e.getElementsContainingOwnText(searchText)
			// System.out.println(e.getElementsContainingText("감독"));
			Elements es = e.getElementsContainingText("감독").next().select("a");
			for (Element e2 : es) {
				String director = e2.text();
				String directorHref = e2.attr("href");
				String pidx = directorHref.substring(directorHref.lastIndexOf("=") + 1);
			}

			System.out.println("==========================================");
			Elements es2 = e.getElementsContainingText("배우").next().select("a");
			for (Element e2 : es2) {
				String actor = e2.text();
				String actorHref = e2.attr("href");
				String pidx = actorHref.substring(actorHref.lastIndexOf("=") + 1);
			}
			Elements es3 = e.getElementsContainingText("장르");
			for (Element e2 : es3) {
				String genre = e2.text();
			}
			Elements es4 = e.getElementsContainingText("기본").next();
			for (Element e2 : es4) {
				String runningTime = e2.text().split(", ")[1];
				String nation = e2.text().split(", ")[2];

			}
		}
		// System.out.println(es);
		// System.out.println(e.text());
		// String str = e.text();
		// if(str.trim().length() != 0){
		// System.out.println(str);
		// }

		// li 복수 갯수를 통해서 이미지 데이터들을 크롤링 해온다.
		Elements lis = ele.select("img");
		for (Element e : lis) {
			// System.out.println(e.attr("data-src"));

			// // 링크
			// String href = e.selectFirst("a").attr("href");
			//
			// // 날짜
			// String date =
			// e.selectFirst(".txt-info").text().replaceAll("개봉","").trim();
			//
			// // 이미지
			// String imgSrc = e.selectFirst(".thumb-image img").attr("src");
			// String imgAlt = e.selectFirst(".thumb-image img").attr("alt");
			//
			// // 관람등급
			// String age = e.selectFirst("i.cgvIcon").text();
			//
			//
			// // 영화 제목
			// String title = e.selectFirst(".box-contents
			// strong.title").text();
			//
			// System.out.println(href);
			// System.out.println(date);
			// System.out.println(imgSrc);
			// System.out.println(imgAlt);
			// System.out.println(age);
			// System.out.println(title);
			// System.out.println( e.selectFirst(".thumb-image
			// img").attr("alt"));
			// System.out.println( e.selectFirst(".thumb-image
			// img").attr("src"));
			// System.out.println(e.selectFirst(".txt-info").text().replaceAll("개봉","").trim());
			// doc = Jsoup.parse(new URL("http://www.cgv.co.kr/movies/" +
			// e.selectFirst("a").attr("href")),2000);
			// System.out.println(doc);
			// break;
		}

		// Elements els= doc.select(".sect-movie-chart");
		// // dom의 갯수
		// System.out.println(els.size());
		//
		//
		// for(int i = 0 ; i < els.size() ; i++){
		// Element e = els.get(i);
		// System.out.println(e);
		// }

		// URL url = new URL("http://www.cgv.co.kr/movies/");
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(url.openStream(),"utf-8"));
		//
		// // 빈 문자열 준비
		// String str = "";
		//
		// // str 안의 텍스트들이 비어있을 때까지 끝까지 읽어옴
		// while((str = br.readLine()) != null){
		// System.out.println(br.readLine());
		// }
	}
}
