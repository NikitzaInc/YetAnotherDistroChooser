package com.nikitzainc.distrochooser.service;

import com.nikitzainc.distrochooser.model.Distro;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Primary
@Profile("load-test")
public class MockDistroWatchScraperService extends DistroWatchScraperService {

    @Override
    public List<String> searchDistros(String searchUrl) {
        try {
            File input = new File("src/test/java/com/nikitzainc/distrochooser/resources/search_page.html");
            Document doc = Jsoup.parse(input, "UTF-8", "https://distrowatch.com/");

            List<String> distroLinks = new ArrayList<>();
            var links = doc.select("#simpleresults ~ b > a");
            links.forEach(link -> distroLinks.add(link.absUrl("href")));

            return distroLinks;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load mock HTML file", e);
        }
    }

    @Override
    public Distro getDistro(String url) {
        File input = new File("src/test/java/com/nikitzainc/distrochooser/resources/cachyos_page.html");
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8", "https://distrowatch.com/");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load mock HTML file", e);
        }
        Distro distro = new Distro();

        // Name
        String fullTitle = doc.select("h1").text();
        distro.setName(fullTitle.replace("DistroWatch.com: ", ""));

        // Homepage
        Element homePage = doc.select("th:contains(Home Page) + td a").first();
        if (homePage != null) {
            String homepageUrl = homePage.attr("href");
            distro.setHomepage(homepageUrl);
        }

        // Description
        Element container = doc.select("td.TablesTitle").first();
        String description = "";
        int maxLength = 0;
        for (TextNode node : container.textNodes()) {
            String text = node.text().trim();

            if (text.length() > maxLength) {
                maxLength = text.length();
                description = text;
            }
        }
        distro.setDescription(description);

        // Distrowatch URL
        distro.setDistrowatchUrl(url);

        // Image
        String shortname = url.substring(url.lastIndexOf('/') + 1);
        distro.setImageUrl("/assets/distro_logos/" + shortname + ".png");

        // Based On
        distro.setBasedOn(doc.select("b:contains(Based on:) + a").text());

        // Architecture
        Elements architectures = doc.select("b:contains(Architecture:) ~ a");
        List<String> archList = new ArrayList<>();
        for (Element architecture : architectures) {
            archList.add(architecture.text());
        }
        distro.setArchitectures(archList);

        // Category
        Elements categories = doc.select("b:contains(Category:) ~ a");
        List<String> catList = new ArrayList<>();
        for (Element category : categories) {
            catList.add(category.text());
        }
        distro.setCategories(catList);

        // Popularity
        distro.setPopularity(doc.select("b:contains(Popularity:) + a").text());

        // Package Management
        distro.setPackageManagement(doc.select("th:contains(Package Management) + td").text());

        // Documentation
        Element docLink = doc.select("th:contains(Documentation) + td > a").first();
        if (docLink != null && !docLink.text().equals("--")) {
            distro.setDocumentation(docLink.attr("href"));
        } else {
            distro.setDocumentation("--");
        }

        // User Forum
        Element forumLink = doc.select("th:contains(User Forums) + td > a").first();
        if (forumLink != null && !forumLink.text().equals("--")) {
            distro.setUserForum(forumLink.attr("href"));
        } else {
            distro.setUserForum("--");
        }

        // Alternative User Forums
        List<String> altForums = new ArrayList<>();
        Elements altForumLinks = doc.select("th:contains(Alternative User Forums) + td > a");
        for (Element link : altForumLinks) {
            altForums.add(link.attr("href"));
        }
        if (altForumLinks.isEmpty()) {
            altForums.add("--");
        }
        distro.setAltUserForums(altForums);

        // Bug Tracker
        Element bugLink = doc.select("th:contains(Bug Tracker) + td > a").first();
        if (bugLink != null && !bugLink.text().equals("--")) {
            distro.setBugTracker(bugLink.attr("href"));
        } else {
            distro.setBugTracker("--");
        }

        // Last Version
        distro.setLastVersion(doc.select("th:contains(Feature) + td").text());

        // Release Date
        distro.setReleaseDate(doc.select("th:contains(Release Date) + td").text());

        // Origin
        distro.setOrigin(doc.select("b:contains(Origin:) + a").text());

        // Status
        distro.setStatus(doc.select("b:contains(Status:) + font").text());

        return distro;
    }
}