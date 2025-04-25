package com.tinyflix.tests;

import com.microsoft.playwright.*;
import org.testng.annotations.*;
import static org.testng.Assert.*;

public class CommentTests extends BaseTest {
    
    @Test(description = "Comments should be added correctly")
    public void testAddComment() {
        navigateToApp();
        
        // Select a video
        selectVideo("React Basics");
        
        // Find comment textarea
        Locator commentTextarea = page.locator("textarea[placeholder='Add a comment...']");
        assertTrue(commentTextarea.isVisible(), "Comment textarea should be visible");
        
        // Enter a comment
        String commentText = "This is a test comment " + System.currentTimeMillis();
        commentTextarea.fill(commentText);
        
        // Click Post Comment button
        page.locator("button:has-text('Post Comment')").click();
        
        // Wait for comment to be added
        page.waitForSelector(".comment:has-text('" + commentText + "')");
        
        // Verify comment is displayed
        assertTrue(page.locator(".comment").first().textContent().contains(commentText), 
                "Added comment should be displayed");
    }
    
    @Test(description = "Comment validation should work correctly")
    public void testCommentValidation() {
        navigateToApp();
        
        // Select a video
        selectVideo("React Basics");
        
        // Find comment textarea
        Locator commentTextarea = page.locator("textarea[placeholder='Add a comment...']");
        
        // Test empty comment
        commentTextarea.fill("");
        page.locator("button:has-text('Post Comment')").click();
        assertTrue(page.locator(".error-message").isVisible(), 
                "Error message should be displayed for empty comment");
        assertTrue(page.locator(".error-message").textContent().contains("empty"), 
                "Error message should indicate comment cannot be empty");
        
        // Test too short comment
        commentTextarea.fill("ab");
        page.locator("button:has-text('Post Comment')").click();
        assertTrue(page.locator(".error-message").isVisible(), 
                "Error message should be displayed for too short comment");
        assertTrue(page.locator(".error-message").textContent().contains("at least 3 characters"), 
                "Error message should indicate minimum length");
        
        // Test too long comment
        StringBuilder longComment = new StringBuilder();
        for (int i = 0; i < 501; i++) {
            longComment.append("a");
        }
        commentTextarea.fill(longComment.toString());
        page.locator("button:has-text('Post Comment')").click();
        assertTrue(page.locator(".error-message").isVisible(), 
                "Error message should be displayed for too long comment");
        assertTrue(page.locator(".error-message").textContent().contains("less than 500"), 
                "Error message should indicate maximum length");
    }
    
    @Test(description = "Comments should be liked correctly")
    public void testLikeComment() {
        navigateToApp();
        
        // Select a video
        selectVideo("React Basics");
        
        // Add a comment if none exists
        if (page.locator(".comment").count() == 0) {
            Locator commentTextarea = page.locator("textarea[placeholder='Add a comment...']");
            commentTextarea.fill("Test comment for liking");
            page.locator("button:has-text('Post Comment')").click();
            page.waitForSelector(".comment");
        }
        
        // Get first comment
        Locator firstComment = page.locator(".comment").first();
        
        // Get initial like count
        String likeButtonText = firstComment.locator("button:has-text('👍')").textContent().trim();
        int initialLikes = 0;
        if (likeButtonText.length() > 1) {
            initialLikes = Integer.parseInt(likeButtonText.substring(1).trim());
        }
        
        // Click like button
        firstComment.locator("button:has-text('👍')").click();
        
        // Wait for like count to update
        wait(500);
        
        // Get updated like count
        String updatedLikeButtonText = firstComment.locator("button:has-text('👍')").textContent().trim();
        int updatedLikes = 0;
        if (updatedLikeButtonText.length() > 1) {
            updatedLikes = Integer.parseInt(updatedLikeButtonText.substring(1).trim());
        }
        
        // Verify like count increased
        assertEquals(updatedLikes, initialLikes + 1, 
                "Like count should increase by 1 after clicking like button");
    }
    
    @Test(description = "Replies should be added correctly")
    public void testAddReply() {
        navigateToApp();
        
        // Select a video
        selectVideo("React Basics");
        
        // Add a comment if none exists
        if (page.locator(".comment").count() == 0) {
            Locator commentTextarea = page.locator("textarea[placeholder='Add a comment...']");
            commentTextarea.fill("Test comment for replying");
            page.locator("button:has-text('Post Comment')").click();
            page.waitForSelector(".comment");
        }
        
        // Get first comment
        Locator firstComment = page.locator(".comment").first();
        
        // Find reply input
        Locator replyInput = firstComment.locator("input[placeholder='Reply to this comment...']");
        assertTrue(replyInput.isVisible(), "Reply input should be visible");
        
        // Enter a reply
        String replyText = "This is a test reply " + System.currentTimeMillis();
        replyInput.fill(replyText);
        
        // Submit reply
        firstComment.locator("button:has-text('Reply')").click();
        
        // Wait for reply to be added
        page.waitForSelector(".reply:has-text('" + replyText + "')");
        
        // Verify reply is displayed
        assertTrue(firstComment.locator(".reply").first().textContent().contains(replyText), 
                "Added reply should be displayed");
    }
    
    @Test(description = "Reply likes should work correctly - High Priority Bug #5")
    public void testReplyLikes() {
        navigateToApp();
        
        // Select a video
        selectVideo("React Basics");
        
        // Add a comment if none exists
        if (page.locator(".comment").count() == 0) {
            Locator commentTextarea = page.locator("textarea[placeholder='Add a comment...']");
            commentTextarea.fill("Test comment for reply likes");
            page.locator("button:has-text('Post Comment')").click();
            page.waitForSelector(".comment");
        }
        
        // Get first comment
        Locator firstComment = page.locator(".comment").first();
        
        // Add a reply if none exists
        if (firstComment.locator(".reply").count() == 0) {
            Locator replyInput = firstComment.locator("input[placeholder='Reply to this comment...']");
            replyInput.fill("Test reply for liking");
            firstComment.locator("button:has-text('Reply')").click();
            page.waitForSelector(".reply");
        }
        
        // Get first reply
        Locator firstReply = firstComment.locator(".reply").first();
        
        // Get initial like count
        String likeButtonText = firstReply.locator("button:has-text('👍')").textContent().trim();
        int initialLikes = 0;
        if (likeButtonText.length() > 1) {
            initialLikes = Integer.parseInt(likeButtonText.substring(1).trim());
        }
        
        // Click like button
        firstReply.locator("button:has-text('👍')").click();
        
        // Wait for like count to update
        wait(500);
        
        // Get updated like count
        String updatedLikeButtonText = firstReply.locator("button:has-text('👍')").textContent().trim();
        int updatedLikes = 0;
        if (updatedLikeButtonText.length() > 1) {
            updatedLikes = Integer.parseInt(updatedLikeButtonText.substring(1).trim());
        }
        
        // Bug #5: Reply functionality doesn't update UI correctly
        if (updatedLikes == initialLikes) {
            fail("Bug #5: Reply like count did not increase after clicking like button");
        } else {
            assertEquals(updatedLikes, initialLikes + 1, 
                    "Like count should increase by 1 after clicking like button");
        }
    }
    
    @Test(description = "Reply validation should work correctly - Medium Priority Bug #9")
    public void testReplyValidation() {
        navigateToApp();
        
        // Select a video
        selectVideo("React Basics");
        
        // Add a comment if none exists
        if (page.locator(".comment").count() == 0) {
            Locator commentTextarea = page.locator("textarea[placeholder='Add a comment...']");
            commentTextarea.fill("Test comment for reply validation");
            page.locator("button:has-text('Post Comment')").click();
            page.waitForSelector(".comment");
        }
        
        // Get first comment
        Locator firstComment = page.locator(".comment").first();
        
        // Find reply input
        Locator replyInput = firstComment.locator("input[placeholder='Reply to this comment...']");
        
        // Test empty reply
        replyInput.fill("");
        firstComment.locator("button:has-text('Reply')").click();
        
        // Bug #9: Missing validation for reply form input
        // Check if empty reply was submitted (it shouldn't be)
        int replyCount = firstComment.locator(".reply").count();
        
        // Submit a valid reply
        replyInput.fill("Valid reply");
        firstComment.locator("button:has-text('Reply')").click();
        
        // Wait for reply to be added
        wait(500);
        
        // Verify a new reply was added
        assertEquals(firstComment.locator(".reply").count(), replyCount + 1, 
                "A new reply should be added after submitting valid reply");
        
        // Bug #9: The application only checks if the reply is empty using trim()
        // but doesn't enforce any other validation rules like minimum length
        // or maximum length as it does for comments
    }
}
