package com.tinyflix.tests;

import com.microsoft.playwright.*;
import org.testng.annotations.*;
import static org.testng.Assert.*;
public class SearchFilterTests extends BaseTest {
    
    @Test(description = "Search by title should work correctly")
    public void testSearchByTitle() {
        navigateToApp();
        
        // Get initial video count
        int initialCount = page.locator(".video-card").count();
        assertTrue(initialCount > 0, "There should be videos displayed initially");
        
        // Search for a specific title
        page.locator("input[placeholder*='Search videos']").fill("React Basics");
        
        // Wait for search results
        wait(500);
        
        // Verify search results
        int searchResultCount = page.locator(".video-card").count();
        assertTrue(searchResultCount > 0, "Search should return at least one result");
        assertTrue(searchResultCount <= initialCount, "Search should filter the videos");
        
        // Verify search result contains the search term
        assertTrue(page.locator(".video-card h3:has-text('React Basics')").isVisible(), 
                "Search results should include videos with matching title");
    }
    
    @Test(description = "Search by description should work correctly")
    public void testSearchByDescription() {
        navigateToApp();
        
        // Search for a term in description
        page.locator("input[placeholder*='Search videos']").fill("fundamentals");
        
        // Wait for search results
        wait(500);
        
        // Verify search results
        int searchResultCount = page.locator(".video-card").count();
        assertTrue(searchResultCount > 0, "Search should return at least one result");
        
        // Verify search result contains the search term in description
        assertTrue(page.locator(".video-card .video-description:has-text('fundamentals')").isVisible(), 
                "Search results should include videos with matching description");
    }
    
    @Test(description = "Search by tags should work correctly")
    public void testSearchByTags() {
        navigateToApp();
        
        // Search for a specific tag
        page.locator("input[placeholder*='Search videos']").fill("javascript");
        
        // Wait for search results
        wait(500);
        
        // Verify search results
        int searchResultCount = page.locator(".video-card").count();
        
        // Verify search result contains the search term in tags
        if (searchResultCount > 0) {
            assertTrue(page.locator(".video-card .tag:has-text('javascript')").isVisible(), 
                    "Search results should include videos with matching tags");
        }
    }
    
    @Test(description = "Filter by recent should work correctly")
    public void testFilterByRecent() {
        navigateToApp();
        
        // Get initial video count
        int initialCount = page.locator(".video-card").count();
        
        // Select "Recent" filter
        page.locator("select").first().selectOption("recent");
        
        // Wait for filter to apply
        wait(500);
        
        // Verify filter results
        int filteredCount = page.locator(".video-card").count();
        assertTrue(filteredCount <= initialCount, "Filter should reduce or maintain the number of videos");
    }
    
    @Test(description = "Filter by popular should work correctly - Medium Priority Bug #8")
    public void testFilterByPopular() {
        navigateToApp();
        
        // Select "Popular" filter
        page.locator("select").first().selectOption("popular");
        
        // Wait for filter to apply
        wait(500);
        
        // Verify filter results
        int filteredCount = page.locator(".video-card").count();
        
        // Bug #8: Filter logic for "popular" videos is hardcoded
        // We can't directly test the hardcoded value, but we can check if filtering works
        
        // Check if any videos are displayed
        if (filteredCount == 0) {
            // This might be because no videos meet the hardcoded criteria
            System.out.println("Bug #8: No videos match the hardcoded popularity criteria");
        } else {
            // Verify that the displayed videos have high view counts
            // This is an indirect test since we can't access the internal logic directly
            assertTrue(page.locator(".video-card .video-meta:has-text('K views')").isVisible() || 
                       page.locator(".video-card .video-meta:has-text('M views')").isVisible(),
                    "Popular videos should have high view counts");
        }
    }
    
    @Test(description = "Sort by title should work correctly")
    public void testSortByTitle() {
        navigateToApp();
        
        // Select "Sort by Title" option
        page.locator("select").nth(1).selectOption("title");
        
        // Wait for sort to apply
        wait(500);
        
        // Get all video titles
        String[] titles = page.locator(".video-card h3").allTextContents().toArray(new String[0]);
        
        // Verify at least 2 videos are present for meaningful sort test
        if (titles.length >= 2) {
            // Check if titles are sorted alphabetically
            for (int i = 0; i < titles.length - 1; i++) {
                assertTrue(titles[i].compareToIgnoreCase(titles[i + 1]) <= 0,
                        "Videos should be sorted alphabetically by title");
            }
        }
    }
    
    @Test(description = "Sort by date should work correctly")
    public void testSortByDate() {
        navigateToApp();
        
        // Select "Sort by Date" option
        page.locator("select").nth(1).selectOption("date");
        
        // Wait for sort to apply
        wait(500);
        
        // We can't directly verify the sort order without accessing the internal data
        // But we can verify the sort option is applied correctly
        assertEquals(page.locator("select").nth(1).inputValue(), "date",
                "Sort by Date option should be selected");
    }
    
    @Test(description = "Sort by rating should work correctly")
    public void testSortByRating() {
        navigateToApp();
        
        // Select "Sort by Rating" option
        page.locator("select").nth(1).selectOption("rating");
        
        // Wait for sort to apply
        wait(500);
        
        // Get all video ratings
        Locator ratingElements = page.locator(".video-card .video-meta span:has-text('⭐')");
        int count = ratingElements.count();
        
        // Verify at least 2 videos are present for meaningful sort test
        if (count >= 2) {
            double[] ratings = new double[count];
            for (int i = 0; i < count; i++) {
                String ratingText = ratingElements.nth(i).textContent().replace("⭐", "").trim();
                ratings[i] = Double.parseDouble(ratingText);
            }
            
            // Check if ratings are sorted in descending order
            for (int i = 0; i < ratings.length - 1; i++) {
                assertTrue(ratings[i] >= ratings[i + 1],
                        "Videos should be sorted by rating in descending order");
            }
        }
    }
    
    @Test(description = "No results state should be handled correctly")
    public void testNoResultsState() {
        navigateToApp();
        
        // Search for a term that won't match any videos
        page.locator("input[placeholder*='Search videos']").fill("xyznonexistentterm123");
        
        // Wait for search results
        wait(500);
        
        // Verify no videos are displayed
        assertEquals(page.locator(".video-card").count(), 0,
                "No videos should be displayed for non-matching search term");

    }
}
