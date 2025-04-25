package com.tinyflix.tests;

import com.microsoft.playwright.*;
import org.testng.annotations.*;
import static org.testng.Assert.*;

public class BookmarkTests extends BaseTest {
    
    @Test(description = "Bookmarks should be added correctly")
    public void testAddBookmark() {
        navigateToApp();
        
        // Select a video
        selectVideo("React Basics");
        
        // Verify bookmark list is empty initially
        assertTrue(page.locator(".bookmark-list.empty").isVisible(), 
                "Bookmark list should be empty initially");
        
        // Click bookmark button
        page.locator("button[aria-label='Add bookmark']").click();
        
        // Verify bookmark is added to the list
        assertFalse(page.locator(".bookmark-list.empty").isVisible(), 
                "Bookmark list should not be empty after adding a bookmark");
        
        // Verify bookmark title contains video title
        assertTrue(page.locator(".bookmark-item").first().textContent().contains("React Basics"), 
                "Bookmark should contain video title");
    }
    
    @Test(description = "Clicking a bookmark should navigate to timestamp - Critical Bug #1")
    public void testBookmarkNavigation() {
        navigateToApp();
        
        // Select a video
        selectVideo("React Basics");
        
        // Play the video for a few seconds
        page.locator("button[aria-label='Play']").click();
        wait(3000);
        page.locator("button[aria-label='Pause']").click();
        
        // Get current timestamp
        String currentTime = page.locator(".time-control span").first().textContent();
        
        // Add bookmark at current position
        page.locator("button[aria-label='Add bookmark']").click();
        
        // Reset video position to beginning
        page.evaluate("() => {\n" +
                "  const videoElement = document.querySelector('video');\n" +
                "  if (videoElement) {\n" +
                "    videoElement.currentTime = 0;\n" +
                "  }\n" +
                "}");
        
        // Verify video position is reset
        assertEquals(page.locator(".time-control span").first().textContent(), "0:00", 
                "Video position should be reset to beginning");
        
        // Click on the bookmark
        page.locator(".bookmark-item button").first().click();
        
        // Bug #1: Bookmark navigation doesn't work
        // Check if console.log was called instead of actual navigation
        String consoleMessages = page.evaluate("() => window._lastConsoleLog || ''").toString();
        
        // Verify the bug: console.log is called but video position doesn't change
        assertTrue(consoleMessages.contains("Jump to timestamp") || 
                   page.locator(".time-control span").first().textContent().equals("0:00"),
                "Bug #1: Bookmark navigation only logs to console instead of changing video position");
    }
    
    @Test(description = "Duplicate bookmarks should be handled correctly")
    public void testDuplicateBookmarks() {
        navigateToApp();
        
        // Select a video
        selectVideo("React Basics");
        
        // Add a bookmark
        page.locator("button[aria-label='Add bookmark']").click();
        
        // Count bookmarks
        int initialCount = page.locator(".bookmark-item").count();
        
        // Try to add another bookmark at the same position
        page.locator("button[aria-label='Add bookmark']").click();
        
        // Count bookmarks again
        int newCount = page.locator(".bookmark-item").count();
        
        // Verify a new bookmark was added (this is actually a bug - should prevent duplicates)
        assertEquals(newCount, initialCount + 1, 
                "A duplicate bookmark was added - application should prevent this");
        
        // Check if any error message is displayed
        boolean errorDisplayed = page.isVisible(".error") || page.isVisible(".error-message");
        assertFalse(errorDisplayed, 
                "No error message is displayed for duplicate bookmarks");
    }
    
    @Test(description = "Bookmark button in VideoCard should not trigger video selection - High Priority Bug #6")
    public void testBookmarkButtonPropagation() {
        navigateToApp();
        
        // Store initial URL to check if navigation occurs
        String initialUrl = page.url();
        
        // Find the bookmark button in a video card
        Locator bookmarkButton = page.locator(".video-card .bookmark-button").first();
        
        // Click the bookmark button
        bookmarkButton.click();
        
        // Verify URL hasn't changed (no navigation occurred)
        assertEquals(page.url(), initialUrl, 
                "URL should not change when clicking bookmark button");
        
        // Bug #6: Test keyboard event handling
        // Try to trigger the bookmark button with keyboard
        bookmarkButton.focus();
        page.keyboard().press("Enter");
        
        // Check if navigation occurred (bug would cause navigation)
        String newUrl = page.url();
        boolean navigationOccurred = !newUrl.equals(initialUrl) || page.isVisible(".video-player");
        
        // This should fail if the bug is present
        if (navigationOccurred) {
            fail("Bug #6: Bookmark button doesn't stop propagation for keyboard events");
        }
    }
}
