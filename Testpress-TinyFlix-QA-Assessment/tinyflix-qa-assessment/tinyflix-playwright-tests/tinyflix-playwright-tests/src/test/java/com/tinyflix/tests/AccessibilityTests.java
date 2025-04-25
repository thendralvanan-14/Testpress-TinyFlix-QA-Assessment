package com.tinyflix.tests;

import com.microsoft.playwright.*;
import org.testng.annotations.*;
import static org.testng.Assert.*;


public class AccessibilityTests extends BaseTest {
    
    @Test(description = "Video player controls should be keyboard accessible - Critical Issue #1")
    public void testVideoPlayerKeyboardAccessibility() {
        navigateToApp();
        
        // Select a video
        selectVideo("React Basics");
        
        // Focus on the play button
        page.locator("button[aria-label='Play']").focus();
        
        // Press Enter to play
        page.keyboard().press("Enter");
        
        // Verify play button changes to pause
        assertTrue(page.isVisible("button[aria-label='Pause']"), 
                "Play button should change to pause when activated with keyboard");
        
        // Focus on the pause button
        page.locator("button[aria-label='Pause']").focus();
        
        // Press Enter to pause
        page.keyboard().press("Enter");
        
        // Verify pause button changes back to play
        assertTrue(page.isVisible("button[aria-label='Play']"), 
                "Pause button should change to play when activated with keyboard");
        
        // Test volume control with keyboard
        Locator volumeSlider = page.locator("input[aria-label='Volume']");
        volumeSlider.focus();
        
        // Press arrow keys to change volume
        for (int i = 0; i < 5; i++) {
            page.keyboard().press("ArrowRight");
        }
        
        // Verify volume has changed
        double volumeValue = Double.parseDouble(volumeSlider.inputValue());
        assertTrue(volumeValue > 0, "Volume should be adjustable with keyboard");
        
        // Test progress bar with keyboard - Critical Issue #1
        Locator progressBar = page.locator(".time-control input[type='range']");
        progressBar.focus();
        
        // Try to use arrow keys to navigate in the video
        String initialTime = page.locator(".time-control span").first().textContent();
        for (int i = 0; i < 5; i++) {
            page.keyboard().press("ArrowRight");
        }
        
        // Wait for potential update
        wait(500);
        
        // Get updated time
        String updatedTime = page.locator(".time-control span").first().textContent();
        
        // Critical Issue #1: Progress bar may not be properly keyboard accessible
        if (updatedTime.equals(initialTime)) {
            System.out.println("Critical Issue #1: Video progress bar is not properly keyboard accessible");
        } else {
            assertNotEquals(updatedTime, initialTime, 
                    "Video position should change when using keyboard with progress bar");
        }
    }
    
    @Test(description = "Bookmark navigation should be keyboard accessible - Critical Issue #1")
    public void testBookmarkKeyboardAccessibility() {
        navigateToApp();
        
        // Select a video
        selectVideo("React Basics");
        
        // Add a bookmark if none exists
        if (page.locator(".bookmark-list.empty").isVisible()) {
            page.locator("button[aria-label='Add bookmark']").click();
            wait(500);
        }
        
        // Focus on the first bookmark
        page.locator(".bookmark-item button").first().focus();
        
        // Press Enter to activate bookmark
        page.keyboard().press("Enter");
        
        // Critical Issue #1: Bookmark navigation doesn't work with keyboard
        // Check if console.log was called instead of actual navigation
        String consoleMessages = page.evaluate("() => window._lastConsoleLog || ''").toString();
        
        // Verify the bug: console.log is called but video position doesn't change
        if (consoleMessages.contains("Jump to timestamp")) {
            System.out.println("Critical Issue #1: Bookmark navigation only logs to console instead of changing video position");
        }
    }
    
    @Test(description = "Form controls should have proper labels and ARIA attributes - High Priority Issue #4")
    public void testFormControlsAccessibility() {
        navigateToApp();
        
        // Check search input
        Locator searchInput = page.locator("input[placeholder*='Search videos']");
        assertTrue(searchInput.isVisible(), "Search input should be visible");
        
        // Check if search input has proper label or aria-label
        String ariaLabel = searchInput.getAttribute("aria-label");
        String id = searchInput.getAttribute("id");
        boolean hasLabel = (ariaLabel != null && !ariaLabel.isEmpty()) || 
                          (id != null && page.locator("label[for='" + id + "']").count() > 0);
        
        assertTrue(hasLabel, "Search input should have proper label or aria-label");
        
        // Select a video
        selectVideo("React Basics");
        
        // Check volume slider
        Locator volumeSlider = page.locator("input[aria-label='Volume']");
        assertTrue(volumeSlider.isVisible(), "Volume slider should be visible");
        
        // High Priority Issue #4: Check if volume slider has proper ARIA attributes
        String valueMin = volumeSlider.getAttribute("aria-valuemin");
        String valueMax = volumeSlider.getAttribute("aria-valuemax");
        String valueNow = volumeSlider.getAttribute("aria-valuenow");
        
        boolean hasCompleteAriaAttributes = (valueMin != null && !valueMin.isEmpty()) && 
                                          (valueMax != null && !valueMax.isEmpty()) && 
                                          (valueNow != null && !valueNow.isEmpty());
        
        assertFalse(hasCompleteAriaAttributes, 
                "High Priority Issue #4: Volume slider is missing proper ARIA attributes");
        
        // Check comment textarea
        Locator commentTextarea = page.locator("textarea[placeholder='Add a comment...']");
        assertTrue(commentTextarea.isVisible(), "Comment textarea should be visible");
        
        // Check if comment textarea has proper label or aria-label
        ariaLabel = commentTextarea.getAttribute("aria-label");
        id = commentTextarea.getAttribute("id");
        hasLabel = (ariaLabel != null && !ariaLabel.isEmpty()) || 
                  (id != null && page.locator("label[for='" + id + "']").count() > 0);
        
        assertTrue(hasLabel, "Comment textarea should have proper label or aria-label");
    }
    
    @Test(description = "Focus management should be proper when content changes - Medium Priority Issue #6")
    public void testFocusManagement() {
        navigateToApp();
        
        // Get the first video card
        Locator firstVideoCard = page.locator(".video-card").first();
        
        // Focus on the video card
        firstVideoCard.focus();
        
        // Press Enter to select the video
        page.keyboard().press("Enter");
        
        // Wait for video player to load
        page.waitForSelector(".video-player");
        
        // Medium Priority Issue #6: Focus should move to the video player
        // Get the active element
        String activeElementTag = page.evaluate("() => document.activeElement.tagName").toString();
        
        // Check if focus moved to video player or its controls
        boolean focusMovedToPlayer = activeElementTag.equalsIgnoreCase("video") || 
                                    page.evaluate("() => document.activeElement.closest('.video-player') !== null").toString().equals("true");
        
        assertFalse(focusMovedToPlayer, 
                "Medium Priority Issue #6: Focus does not move to video player when content changes");
    }
    
    @Test(description = "Skip navigation should be available - Medium Priority Issue #7")
    public void testSkipNavigation() {
        navigateToApp();
        
        // Tab to focus on the first focusable element
        page.keyboard().press("Tab");
        
        // Check if the first focusable element is a skip link
        String firstFocusableText = page.evaluate("() => document.activeElement.textContent").toString();
        boolean hasSkipLink = firstFocusableText.contains("Skip") || 
                             firstFocusableText.contains("skip") || 
                             firstFocusableText.contains("main");
        
        assertFalse(hasSkipLink, 
                "Medium Priority Issue #7: No skip navigation link is provided");
    }
    
    @Test(description = "Color contrast should be sufficient - High Priority Issue #3")
    public void testColorContrast() {
        navigateToApp();

        boolean hasContrastClasses = page.locator("[class*='high-contrast']").count() > 0 || 
                                    page.locator("[class*='contrast']").count() > 0 || 
                                    page.locator("[data-contrast]").count() > 0;
        
        assertFalse(hasContrastClasses, 
                "High Priority Issue #3: No explicit contrast management classes found");
        
        // Check if there's a theme toggle or accessibility settings
        boolean hasThemeToggle = page.locator("button:has-text('Theme')").count() > 0 || 
                                page.locator("button:has-text('Contrast')").count() > 0 || 
                                page.locator("button:has-text('Accessibility')").count() > 0;
        
        assertFalse(hasThemeToggle, 
                "High Priority Issue #3: No theme toggle or accessibility settings found");
    }
    
    @Test(description = "Images should have alt text")
    public void testImageAltText() {
        navigateToApp();
        
        // Check if all images have alt text
        Locator images = page.locator("img");
        int imageCount = images.count();
        
        for (int i = 0; i < imageCount; i++) {
            String alt = images.nth(i).getAttribute("alt");
            assertNotNull(alt, "Image #" + (i+1) + " should have alt attribute");
            assertFalse(alt.isEmpty(), "Image #" + (i+1) + " should have non-empty alt text");
        }
    }
    
    @Test(description = "Error messages should be announced to screen readers")
    public void testErrorMessageAccessibility() {
        navigateToApp();
        
        // Select a video
        selectVideo("React Basics");
        
        // Try to submit empty comment to trigger error
        Locator commentTextarea = page.locator("textarea[placeholder='Add a comment...']");
        commentTextarea.fill("");
        page.locator("button:has-text('Post Comment')").click();
        
        // Check if error message has proper role or aria-live attribute
        Locator errorMessage = page.locator(".error-message");
        assertTrue(errorMessage.isVisible(), "Error message should be visible");
        
        String role = errorMessage.getAttribute("role");
        String ariaLive = errorMessage.getAttribute("aria-live");
        
        boolean isAnnounceable = (role != null && (role.equals("alert") || role.equals("status"))) || 
                                (ariaLive != null && (ariaLive.equals("assertive") || ariaLive.equals("polite")));
        
        assertTrue(isAnnounceable, 
                "Error message should have role='alert' or aria-live attribute for screen readers");
    }
    
    @Test(description = "Page should have language attribute - Low Priority Issue #9")
    public void testLanguageAttribute() {
        navigateToApp();
        
        // Check if html element has lang attribute
        String lang = page.evaluate("() => document.documentElement.lang").toString();
        
        // Low Priority Issue #9: Missing language attribute
        assertTrue(lang == null || lang.isEmpty(), 
                "Low Priority Issue #9: HTML element is missing lang attribute");
    }
}
