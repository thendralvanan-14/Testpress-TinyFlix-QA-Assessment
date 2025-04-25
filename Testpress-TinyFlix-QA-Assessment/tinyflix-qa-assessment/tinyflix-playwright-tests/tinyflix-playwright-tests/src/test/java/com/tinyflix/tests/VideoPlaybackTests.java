package com.tinyflix.tests;

import com.microsoft.playwright.*;
import org.testng.annotations.*;
import static org.testng.Assert.*;

public class VideoPlaybackTests extends BaseTest {
    
    @Test(description = "Video should play and pause correctly")
    public void testVideoPlayPause() {
        navigateToApp();
        
        // Select a video
        selectVideo("React Basics");
        
        // Verify video player is displayed
        assertTrue(page.isVisible(".video-player"), "Video player should be visible");
        
        // Click play button
        page.locator("button[aria-label='Play']").click();
        
        // Verify play button changes to pause
        assertTrue(page.isVisible("button[aria-label='Pause']"), "Pause button should be visible after clicking play");
        
        // Wait for video to play a bit
        wait(1000);
        
        // Click pause button
        page.locator("button[aria-label='Pause']").click();
        
        // Verify pause button changes back to play
        assertTrue(page.isVisible("button[aria-label='Play']"), "Play button should be visible after clicking pause");
    }
    
    @Test(description = "Volume control should adjust video volume")
    public void testVolumeControl() {
        navigateToApp();
        
        // Select a video
        selectVideo("React Basics");
        
        // Get volume slider
        Locator volumeSlider = page.locator("input[aria-label='Volume']");
        
        // Verify volume slider is present
        assertTrue(volumeSlider.isVisible(), "Volume slider should be visible");
        
        // Set volume to 50%
        volumeSlider.fill("0.5");
        
        // Verify volume is set to 50%
        assertEquals(volumeSlider.inputValue(), "0.5", "Volume should be set to 0.5");
        
        // Click mute button
        page.locator("button[aria-label='Mute']").click();
        
        // Verify mute button changes to unmute
        assertTrue(page.isVisible("button[aria-label='Unmute']"), "Unmute button should be visible after clicking mute");
    }
    
    @Test(description = "Playback rate should change video speed")
    public void testPlaybackRate() {
        navigateToApp();
        
        // Select a video
        selectVideo("React Basics");
        
        // Get playback rate selector
        Locator playbackRateSelect = page.locator("select[aria-label='Playback speed']");
        
        // Verify playback rate selector is present
        assertTrue(playbackRateSelect.isVisible(), "Playback rate selector should be visible");
        
        // Change playback rate to 1.5x
        playbackRateSelect.selectOption("1.5");
        
        // Verify playback rate is set to 1.5x
        assertEquals(playbackRateSelect.inputValue(), "1.5", "Playback rate should be set to 1.5");
    }
    
    @Test(description = "Video progress bar should update during playback")
    public void testVideoProgressBar() {
        navigateToApp();
        
        // Select a video
        selectVideo("React Basics");
        
        // Get progress bar
        Locator progressBar = page.locator(".time-control input[type='range']");
        
        // Verify progress bar is present
        assertTrue(progressBar.isVisible(), "Progress bar should be visible");
        
        // Get initial value
        String initialValue = progressBar.inputValue();
        
        // Click play button
        page.locator("button[aria-label='Play']").click();
        
        // Wait for video to play a bit
        wait(2000);
        
        // Get updated value
        String updatedValue = progressBar.inputValue();
        
        // Verify progress bar value has increased
        assertTrue(Double.parseDouble(updatedValue) > Double.parseDouble(initialValue), 
                "Progress bar value should increase during playback");
        
        // Click pause button
        page.locator("button[aria-label='Pause']").click();
    }
    
    @Test(description = "Video should handle errors gracefully - Critical Bug #2")
    public void testVideoErrorHandling() {
        navigateToApp();
        
        // Select a video
        selectVideo("React Basics");
        
        // Simulate a video error by evaluating JavaScript
        page.evaluate("() => {\n" +
                "  const videoElement = document.querySelector('video');\n" +
                "  if (videoElement) {\n" +
                "    videoElement.dispatchEvent(new Event('error'));\n" +
                "  }\n" +
                "}");
        
        // Verify error message is displayed
        assertTrue(page.isVisible(".error-message"), "Error message should be displayed");
        
        // Verify error message content
        assertTrue(page.locator(".error-message").textContent().contains("Error loading video"), 
                "Error message should indicate video loading error");
        
        // Bug #2: Error handling lacks recovery options
        // Verify there are no retry buttons or alternative actions
        assertFalse(page.isVisible("button:has-text('Retry')"), 
                "Bug #2: No retry button is provided for error recovery");
    }
    
    @Test(description = "Video source should have fallback mechanisms - Critical Bug #3")
    public void testVideoSourceFallback() {
        navigateToApp();
        
        // Select a video
        selectVideo("React Basics");
        
        // Check if video element has multiple source elements
        int sourceCount = page.locator("video source").count();
        
        // Bug #3: Missing video source fallback mechanism
        assertEquals(sourceCount, 0, "Bug #3: Video element does not have multiple source elements for fallback");
        
        // Verify video element has direct src attribute instead of source elements
        assertTrue(page.locator("video").getAttribute("src").contains(".mp4"), 
                "Video element uses direct src attribute instead of source elements");
    }
}
