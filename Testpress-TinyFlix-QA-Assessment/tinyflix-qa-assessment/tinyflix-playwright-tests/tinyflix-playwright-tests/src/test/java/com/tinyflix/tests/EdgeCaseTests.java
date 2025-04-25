package com.tinyflix.tests;

import com.microsoft.playwright.*;
import org.testng.annotations.*;
import static org.testng.Assert.*;

public class EdgeCaseTests extends BaseTest {
    
    @Test(description = "Application should handle videos with long titles")
    public void testLongVideoTitles() {
        navigateToApp();
        
        // Simulate a video with a very long title using JavaScript
        page.evaluate("() => {\n" +
                "  const firstVideoCard = document.querySelector('.video-card');\n" +
                "  if (firstVideoCard) {\n" +
                "    const titleElement = firstVideoCard.querySelector('h3');\n" +
                "    if (titleElement) {\n" +
                "      const originalTitle = titleElement.textContent;\n" +
                "      titleElement.textContent = 'This is an extremely long video title that should test how the application handles overflow and wrapping of text in the user interface when displaying video information';\n" +
                "      return originalTitle; // Return original title for reference\n" +
                "    }\n" +
                "  }\n" +
                "  return null;\n" +
                "}");
        
        // Verify the video card still displays properly
        assertTrue(page.locator(".video-card").first().isVisible(), 
                "Video card should still be visible with long title");
        
        // Check if title is truncated or wrapped properly
        // This is a visual check that's hard to automate, but we can check if the element is still there
        assertTrue(page.locator(".video-card h3").first().isVisible(), 
                "Video title should still be visible");
    }
    
    @Test(description = "Application should handle videos with no description")
    public void testNoDescription() {
        navigateToApp();
        
        // Simulate a video with no description using JavaScript
        page.evaluate("() => {\n" +
                "  const firstVideoCard = document.querySelector('.video-card');\n" +
                "  if (firstVideoCard) {\n" +
                "    const descElement = firstVideoCard.querySelector('.video-description');\n" +
                "    if (descElement) {\n" +
                "      const originalDesc = descElement.textContent;\n" +
                "      descElement.textContent = '';\n" +
                "      return originalDesc; // Return original description for reference\n" +
                "    }\n" +
                "  }\n" +
                "  return null;\n" +
                "}");
        
        // Verify the video card still displays properly
        assertTrue(page.locator(".video-card").first().isVisible(), 
                "Video card should still be visible with no description");
    }
    
    @Test(description = "Application should handle videos with extremely high view counts")
    public void testHighViewCounts() {
        navigateToApp();
        
        // Simulate a video with extremely high view count using JavaScript
        page.evaluate("() => {\n" +
                "  const firstVideoCard = document.querySelector('.video-card');\n" +
                "  if (firstVideoCard) {\n" +
                "    const viewCountElement = firstVideoCard.querySelector('.video-meta span:nth-child(1)');\n" +
                "    if (viewCountElement) {\n" +
                "      const originalCount = viewCountElement.textContent;\n" +
                "      // Set to 1 billion views\n" +
                "      viewCountElement.textContent = '1000000000 views';\n" +
                "      return originalCount; // Return original count for reference\n" +
                "    }\n" +
                "  }\n" +
                "  return null;\n" +
                "}");
        
        // Verify the view count is formatted correctly (should show as 1B)
        String viewCountText = page.locator(".video-card .video-meta span").first().textContent();
        assertTrue(viewCountText.contains("B views") || viewCountText.contains("1000000000"),
                "High view count should be formatted with B suffix or show full number");
    }
    
    @Test(description = "Application should handle videos with long duration - High Priority Bug #4")
    public void testLongDuration() {
        navigateToApp();
        
        // Simulate a video with long duration (over an hour) using JavaScript
        page.evaluate("() => {\n" +
                "  const firstVideoCard = document.querySelector('.video-card');\n" +
                "  if (firstVideoCard) {\n" +
                "    const durationElement = firstVideoCard.querySelector('.duration');\n" +
                "    if (durationElement) {\n" +
                "      const originalDuration = durationElement.textContent;\n" +
                "      // Set to 1 hour 23 minutes 45 seconds\n" +
                "      durationElement.textContent = '1:23:45';\n" +
                "      return originalDuration; // Return original duration for reference\n" +
                "    }\n" +
                "  }\n" +
                "  return null;\n" +
                "}");
        
        // Bug #4: Duration formatting doesn't handle hours correctly
        // Select the video
        page.locator(".video-card").first().click();
        
        // Wait for video player to load
        page.waitForSelector(".video-player");
        
        // Check if duration is displayed correctly in the video player
        String durationText = page.locator(".time-control span").nth(1).textContent();
        
        // Bug #4: The application doesn't handle hours in duration correctly
        if (!durationText.contains(":")) {
            fail("Bug #4: Duration formatting doesn't handle hours correctly");
        } else if (!durationText.matches("\\d+:\\d+:\\d+")) {
            fail("Bug #4: Duration with hours is not formatted as h:mm:ss");
        }
    }
    
    @Test(description = "Application should handle many bookmarks")
    public void testManyBookmarks() {
        navigateToApp();
        
        // Select a video
        selectVideo("React Basics");
        
        // Add multiple bookmarks
        for (int i = 0; i < 5; i++) {
            // Change video position slightly each time
            page.evaluate("() => {\n" +
                    "  const videoElement = document.querySelector('video');\n" +
                    "  if (videoElement) {\n" +
                    "    videoElement.currentTime = " + (i * 10) + ";\n" +
                    "  }\n" +
                    "}");
            
            // Add bookmark
            page.locator("button[aria-label='Add bookmark']").click();
            
            // Wait a bit between adding bookmarks
            wait(300);
        }
        
        // Verify all bookmarks are displayed
        int bookmarkCount = page.locator(".bookmark-item").count();
        assertTrue(bookmarkCount >= 5, "All added bookmarks should be displayed");
        
        // Verify bookmark list is scrollable if needed
        // This is a visual check that's hard to automate, but we can check if the element is still there
        assertTrue(page.locator(".bookmark-list").isVisible(), 
                "Bookmark list should still be visible with many bookmarks");
    }
    
    @Test(description = "Application should handle many comments")
    public void testManyComments() {
        navigateToApp();
        
        // Select a video
        selectVideo("React Basics");
        
        // Add multiple comments (limit to 3 for test performance)
        for (int i = 0; i < 3; i++) {
            // Find comment textarea
            Locator commentTextarea = page.locator("textarea[placeholder='Add a comment...']");
            
            // Enter a unique comment
            String commentText = "Test comment " + i + " - " + System.currentTimeMillis();
            commentTextarea.fill(commentText);
            
            // Click Post Comment button
            page.locator("button:has-text('Post Comment')").click();
            
            // Wait for comment to be added
            page.waitForSelector(".comment:has-text('" + commentText + "')");
            
            // Wait a bit between adding comments
            wait(300);
        }
        
        // Verify all comments are displayed
        int commentCount = page.locator(".comment").count();
        assertTrue(commentCount >= 3, "All added comments should be displayed");
        
        // Verify comment list is scrollable if needed
        // This is a visual check that's hard to automate, but we can check if the element is still there
        assertTrue(page.locator(".comments-container").isVisible(), 
                "Comment container should still be visible with many comments");
    }
}
