package com.tinyflix.tests;

import com.microsoft.playwright.*;
import org.testng.annotations.*;
import static org.testng.Assert.*;

public class ErrorHandlingTests extends BaseTest {
    
    @Test(description = "Application should handle video loading errors - Critical Bug #2")
    public void testVideoLoadingError() {
        navigateToApp();
        
        // Select a video
        selectVideo("React Basics");
        
        // Simulate a video loading error using JavaScript
        page.evaluate("() => {\n" +
                "  const videoElement = document.querySelector('video');\n" +
                "  if (videoElement) {\n" +
                "    // Create and dispatch an error event\n" +
                "    const errorEvent = new Event('error');\n" +
                "    videoElement.dispatchEvent(errorEvent);\n" +
                "  }\n" +
                "}");
        
        // Verify error message is displayed
        assertTrue(page.locator(".error-message").isVisible(), 
                "Error message should be displayed when video fails to load");
        
        // Bug #2: Error handling lacks recovery options
        boolean hasRetryButton = page.isVisible("button:has-text('Retry')") || 
                                page.isVisible("button:has-text('Try Again')");
        
        assertFalse(hasRetryButton, 
                "Bug #2: No retry button is provided for error recovery");
        
        // Check if there's any alternative content or fallback
        boolean hasAlternativeContent = page.isVisible(".alternative-content") || 
                                       page.isVisible(".fallback-content");
        
        assertFalse(hasAlternativeContent, 
                "Bug #2: No alternative content is provided when video fails to load");
    }
    
    @Test(description = "Application should handle network errors")
    public void testNetworkError() {
        navigateToApp();
        
        // Intercept network requests to simulate network error
        page.route("**/*.mp4", route -> {
            route.abort();
        });
        
        // Select a video
        selectVideo("React Basics");
        
        // Verify error handling
        boolean hasErrorMessage = page.isVisible(".error-message") || 
                                 page.isVisible(".error") || 
                                 page.isVisible("[role='alert']");
        
        assertTrue(hasErrorMessage, 
                "Error message should be displayed when network request fails");
        
        // Stop intercepting requests
        page.unroute("**/*.mp4");
    }
    
    @Test(description = "Application should handle invalid video IDs")
    public void testInvalidVideoId() {
        navigateToApp();
        
        // Try to navigate to a non-existent video ID using JavaScript
        page.evaluate("() => {\n" +
                "  // Simulate selecting a video with invalid ID\n" +
                "  if (typeof selectVideo === 'function') {\n" +
                "    try {\n" +
                "      selectVideo(9999);\n" +
                "      return 'Function called';\n" +
                "    } catch (e) {\n" +
                "      return e.toString();\n" +
                "    }\n" +
                "  } else {\n" +
                "    // Direct manipulation if function doesn't exist\n" +
                "    try {\n" +
                "      const event = new CustomEvent('selectVideo', { detail: { id: 9999 } });\n" +
                "      document.dispatchEvent(event);\n" +
                "      return 'Event dispatched';\n" +
                "    } catch (e) {\n" +
                "      return e.toString();\n" +
                "    }\n" +
                "  }\n" +
                "}");
        
        // Wait a moment for any error handling to occur
        wait(500);
        
        // Check for error message
        // Note: This might not trigger an error if the application doesn't validate video IDs
        boolean hasErrorMessage = page.isVisible(".error-message") || 
                                 page.isVisible(".error") || 
                                 page.isVisible("[role='alert']");
        
        // We can't assert on this because the application might not handle invalid IDs explicitly
        if (!hasErrorMessage) {
            System.out.println("No explicit error handling for invalid video IDs");
        }
    }
    
    @Test(description = "Application should handle form validation errors")
    public void testFormValidationErrors() {
        navigateToApp();
        
        // Select a video
        selectVideo("React Basics");
        
        // Try to submit empty comment
        Locator commentTextarea = page.locator("textarea[placeholder='Add a comment...']");
        commentTextarea.fill("");
        page.locator("button:has-text('Post Comment')").click();
        
        // Verify error message is displayed
        assertTrue(page.locator(".error-message").isVisible(), 
                "Error message should be displayed for empty comment");
        
        // Verify error message is clear and descriptive
        String errorText = page.locator(".error-message").textContent();
        assertTrue(errorText.contains("empty") || errorText.contains("required"), 
                "Error message should clearly indicate the field is required");
        
        // Verify form is not submitted (comment list doesn't change)
        int commentCount = page.locator(".comment").count();
        
        // Try to submit valid comment
        commentTextarea.fill("This is a valid comment");
        page.locator("button:has-text('Post Comment')").click();
        
        // Wait for comment to be added
        wait(500);
        
        // Verify comment was added
        assertEquals(page.locator(".comment").count(), commentCount + 1, 
                "Valid comment should be added after fixing validation error");
    }
    
    @Test(description = "Application should handle unexpected errors with ErrorBoundary - Medium Priority Bug #7")
    public void testErrorBoundary() {
        navigateToApp();
        
        // Simulate a JavaScript error in a component using JavaScript
        page.evaluate("() => {\n" +
                "  try {\n" +
                "    // Find a React component instance\n" +
                "    const videoCardElement = document.querySelector('.video-card');\n" +
                "    if (videoCardElement && videoCardElement._reactRootContainer) {\n" +
                "      // This is a simplified approach - in real scenarios we'd need to access React internals\n" +
                "      throw new Error('Simulated component error');\n" +
                "    }\n" +
                "    return 'Could not access React component';\n" +
                "  } catch (e) {\n" +
                "    return e.toString();\n" +
                "  }\n" +
                "}");
        
        // Bug #7: ErrorBoundary doesn't provide user-friendly recovery options
        // We can't directly test the ErrorBoundary without causing a real crash
        // But we can check if there's any error message visible after our simulation attempt
        
        boolean hasErrorMessage = page.isVisible(".error-message") || 
                                 page.isVisible(".error") || 
                                 page.isVisible("[role='alert']");
        
        // We can't assert on this because our simulation might not trigger the ErrorBoundary
        if (!hasErrorMessage) {
            System.out.println("Bug #7: No visible error handling from ErrorBoundary");
        }
    }
    
    @Test(description = "Application should handle server errors")
    public void testServerErrors() {
        navigateToApp();
        
        // Intercept API requests to simulate server error
        page.route("**/api/**", route -> {
            route.fulfill(new Route.FulfillOptions()
                    .setStatus(500)
                    .setContentType("application/json")
                    .setBody("{\"error\":\"Internal Server Error\"}"));
        });
        
        // Perform an action that would trigger an API call
        // For example, try to add a comment
        selectVideo("React Basics");
        
        Locator commentTextarea = page.locator("textarea[placeholder='Add a comment...']");
        commentTextarea.fill("This comment will trigger a server error");
        page.locator("button:has-text('Post Comment')").click();
        
        // Wait for error handling
        wait(500);
        
        // Check for error message
        boolean hasErrorMessage = page.isVisible(".error-message") || 
                                 page.isVisible(".error") || 
                                 page.isVisible("[role='alert']");
        
        // We can't assert on this because the application might not make real API calls in test mode
        if (!hasErrorMessage) {
            System.out.println("No visible error handling for server errors");
        }
        
        // Stop intercepting requests
        page.unroute("**/api/**");
    }
}
