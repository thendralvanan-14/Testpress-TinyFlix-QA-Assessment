# TinyFlix Test Plan

## 1. Introduction

### 1.1 Purpose
This test plan outlines the testing strategy for the TinyFlix video streaming application. It provides a comprehensive approach to verify the functionality, usability, accessibility, and performance of the application.

### 1.2 Scope
This test plan covers:
- Functional testing of all application features
- Non-functional testing (performance, accessibility)
- Edge case testing
- Error handling testing
- Test implementation guidelines

### 1.3 References
- TinyFlix Application Source Code
- Bug Report Document
- WCAG 2.1 Accessibility Guidelines

## 2. Test Strategy

### 2.1 Testing Approach
- **Manual Testing**: Initial exploration and verification of UI elements
- **Automated Testing**: Regression testing using Jest and React Testing Library
- **Accessibility Testing**: Using axe-core and manual keyboard navigation testing
- **Performance Testing**: Using Lighthouse and React Profiler

### 2.2 Test Environment
- **Browsers**: Chrome, Firefox, Safari, Edge (latest versions)
- **Devices**: Desktop, Tablet, Mobile
- **Screen Readers**: NVDA, VoiceOver
- **Network Conditions**: Fast (50+ Mbps), Average (10-20 Mbps), Slow (< 5 Mbps)

### 2.3 Test Data
- Sample videos with various durations and formats
- Comments with different lengths and special characters
- Bookmarks at different timestamps
- Search queries with special characters and edge cases

## 3. Test Cases

### 3.1 Video Playback

#### TC-001: Video Play/Pause
- **Description**: Verify that videos can be played and paused
- **Prerequisites**: Application is loaded, video is available
- **Test Steps**:
  1. Click on a video card
  2. Click the play button
  3. Verify video starts playing
  4. Click the pause button
  5. Verify video pauses
- **Expected Results**: Video plays when play button is clicked and pauses when pause button is clicked
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-002: Volume Control
- **Description**: Verify that volume can be adjusted
- **Prerequisites**: Application is loaded, video is playing
- **Test Steps**:
  1. Play a video
  2. Adjust volume slider
  3. Verify volume changes
  4. Click mute button
  5. Verify video is muted
  6. Click unmute button
  7. Verify video is unmuted at previous volume level
- **Expected Results**: Volume changes when slider is adjusted, video mutes/unmutes correctly
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-003: Playback Rate
- **Description**: Verify that playback rate can be changed
- **Prerequisites**: Application is loaded, video is playing
- **Test Steps**:
  1. Play a video
  2. Select 0.5x from playback rate dropdown
  3. Verify video plays at 0.5x speed
  4. Select 1.5x from playback rate dropdown
  5. Verify video plays at 1.5x speed
  6. Select 2x from playback rate dropdown
  7. Verify video plays at 2x speed
  8. Select 1x from playback rate dropdown
  9. Verify video plays at normal speed
- **Expected Results**: Video plays at the selected playback rate
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-004: Video Progress Bar
- **Description**: Verify that the progress bar correctly shows video progress and allows seeking
- **Prerequisites**: Application is loaded, video is playing
- **Test Steps**:
  1. Play a video
  2. Observe progress bar advancing
  3. Click on a different position on the progress bar
  4. Verify video jumps to that position
  5. Drag the progress bar handle
  6. Verify video position updates accordingly
- **Expected Results**: Progress bar accurately reflects video position and allows seeking
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-005: Video Error Handling
- **Description**: Verify that video errors are handled gracefully
- **Prerequisites**: Application is loaded
- **Test Steps**:
  1. Modify video source to an invalid URL
  2. Attempt to play the video
  3. Verify error message is displayed
- **Expected Results**: Appropriate error message is displayed when video fails to load
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

### 3.2 Search and Filter

#### TC-006: Search by Title
- **Description**: Verify that videos can be searched by title
- **Prerequisites**: Application is loaded
- **Test Steps**:
  1. Enter "React" in the search box
  2. Verify only videos with "React" in the title are displayed
  3. Enter "nonexistent" in the search box
  4. Verify no videos are displayed
  5. Clear the search box
  6. Verify all videos are displayed
- **Expected Results**: Search correctly filters videos by title
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-007: Search by Description
- **Description**: Verify that videos can be searched by description
- **Prerequisites**: Application is loaded
- **Test Steps**:
  1. Enter "fundamentals" in the search box
  2. Verify videos with "fundamentals" in the description are displayed
  3. Enter "nonexistent" in the search box
  4. Verify no videos are displayed
- **Expected Results**: Search correctly filters videos by description
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-008: Search by Tags
- **Description**: Verify that videos can be searched by tags
- **Prerequisites**: Application is loaded
- **Test Steps**:
  1. Enter "javascript" in the search box
  2. Verify videos with the "javascript" tag are displayed
  3. Enter "nonexistent" in the search box
  4. Verify no videos are displayed
- **Expected Results**: Search correctly filters videos by tags
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-009: Filter by Recent
- **Description**: Verify that videos can be filtered by recency
- **Prerequisites**: Application is loaded
- **Test Steps**:
  1. Select "Recent" from the filter dropdown
  2. Verify only videos published in the last 30 days are displayed
  3. Select "All Videos" from the filter dropdown
  4. Verify all videos are displayed
- **Expected Results**: Filter correctly shows only recent videos
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-010: Filter by Popular
- **Description**: Verify that videos can be filtered by popularity
- **Prerequisites**: Application is loaded
- **Test Steps**:
  1. Select "Popular" from the filter dropdown
  2. Verify only videos with view count > 2000 are displayed
  3. Select "All Videos" from the filter dropdown
  4. Verify all videos are displayed
- **Expected Results**: Filter correctly shows only popular videos
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-011: Sort by Title
- **Description**: Verify that videos can be sorted by title
- **Prerequisites**: Application is loaded
- **Test Steps**:
  1. Select "Sort by Title" from the sort dropdown
  2. Verify videos are sorted alphabetically by title
- **Expected Results**: Videos are sorted alphabetically by title
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-012: Sort by Date
- **Description**: Verify that videos can be sorted by date
- **Prerequisites**: Application is loaded
- **Test Steps**:
  1. Select "Sort by Date" from the sort dropdown
  2. Verify videos are sorted by publish date (newest first)
- **Expected Results**: Videos are sorted by publish date with newest first
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-013: Sort by Rating
- **Description**: Verify that videos can be sorted by rating
- **Prerequisites**: Application is loaded
- **Test Steps**:
  1. Select "Sort by Rating" from the sort dropdown
  2. Verify videos are sorted by rating (highest first)
- **Expected Results**: Videos are sorted by rating with highest first
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

### 3.3 Bookmarks

#### TC-014: Add Bookmark
- **Description**: Verify that bookmarks can be added
- **Prerequisites**: Application is loaded, video is playing
- **Test Steps**:
  1. Play a video
  2. Click the bookmark button at a specific timestamp
  3. Verify bookmark is added to the bookmark list
  4. Verify bookmark has correct timestamp and title
- **Expected Results**: Bookmark is added with correct timestamp and title
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-015: Navigate to Bookmark
- **Description**: Verify that clicking a bookmark navigates to the correct timestamp
- **Prerequisites**: Application is loaded, bookmarks exist
- **Test Steps**:
  1. Play a video with existing bookmarks
  2. Click on a bookmark in the bookmark list
  3. Verify video jumps to the correct timestamp
- **Expected Results**: Video jumps to the timestamp of the bookmark
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-016: Duplicate Bookmark Handling
- **Description**: Verify that duplicate bookmarks are handled correctly
- **Prerequisites**: Application is loaded, video has at least one bookmark
- **Test Steps**:
  1. Play a video with an existing bookmark
  2. Try to add a bookmark at the same timestamp
  3. Verify appropriate error message is displayed
- **Expected Results**: Error message indicates bookmark already exists
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-017: Bookmark Empty State
- **Description**: Verify that empty bookmark state is handled correctly
- **Prerequisites**: Application is loaded, no bookmarks exist
- **Test Steps**:
  1. Play a video
  2. Observe the bookmark list
  3. Verify empty state message is displayed
- **Expected Results**: Empty state message is displayed when no bookmarks exist
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

### 3.4 Comments

#### TC-018: Add Comment
- **Description**: Verify that comments can be added
- **Prerequisites**: Application is loaded, video is selected
- **Test Steps**:
  1. Select a video
  2. Enter a comment in the comment box
  3. Click "Post Comment"
  4. Verify comment appears in the comments list
  5. Verify comment has correct timestamp
- **Expected Results**: Comment is added to the comments list with correct timestamp
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-019: Like Comment
- **Description**: Verify that comments can be liked
- **Prerequisites**: Application is loaded, comments exist
- **Test Steps**:
  1. Select a video with existing comments
  2. Click the like button on a comment
  3. Verify like count increases
  4. Click the like button again
  5. Verify like count increases again (no toggle functionality)
- **Expected Results**: Like count increases when like button is clicked
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-020: Reply to Comment
- **Description**: Verify that replies can be added to comments
- **Prerequisites**: Application is loaded, comments exist
- **Test Steps**:
  1. Select a video with existing comments
  2. Enter a reply in the reply box
  3. Click "Reply"
  4. Verify reply appears under the comment
  5. Verify reply has correct timestamp
- **Expected Results**: Reply is added under the comment with correct timestamp
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-021: Like Reply
- **Description**: Verify that replies can be liked
- **Prerequisites**: Application is loaded, comments with replies exist
- **Test Steps**:
  1. Select a video with comments that have replies
  2. Click the like button on a reply
  3. Verify like count increases
- **Expected Results**: Like count increases when like button is clicked
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-022: Comment Validation - Empty
- **Description**: Verify that empty comments are validated
- **Prerequisites**: Application is loaded, video is selected
- **Test Steps**:
  1. Select a video
  2. Leave the comment box empty
  3. Click "Post Comment"
  4. Verify error message is displayed
- **Expected Results**: Error message indicates comment cannot be empty
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-023: Comment Validation - Too Short
- **Description**: Verify that too short comments are validated
- **Prerequisites**: Application is loaded, video is selected
- **Test Steps**:
  1. Select a video
  2. Enter a 2-character comment
  3. Click "Post Comment"
  4. Verify error message is displayed
- **Expected Results**: Error message indicates comment must be at least 3 characters
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-024: Comment Validation - Too Long
- **Description**: Verify that too long comments are validated
- **Prerequisites**: Application is loaded, video is selected
- **Test Steps**:
  1. Select a video
  2. Enter a 501-character comment
  3. Click "Post Comment"
  4. Verify error message is displayed
- **Expected Results**: Error message indicates comment must be less than 500 characters
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

### 3.5 User Preferences

#### TC-025: Autoplay Setting
- **Description**: Verify that autoplay setting works correctly
- **Prerequisites**: Application is loaded, video is selected
- **Test Steps**:
  1. Select a video
  2. Check the "Autoplay" checkbox
  3. Finish watching the current video
  4. Verify next video starts automatically
  5. Uncheck the "Autoplay" checkbox
  6. Finish watching the current video
  7. Verify next video does not start automatically
- **Expected Results**: Autoplay setting controls automatic playback of next video
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-026: Quality Setting
- **Description**: Verify that quality setting works correctly
- **Prerequisites**: Application is loaded, video is selected
- **Test Steps**:
  1. Select a video
  2. Select "Low Quality" from the quality dropdown
  3. Verify video quality changes
  4. Select "Medium Quality" from the quality dropdown
  5. Verify video quality changes
  6. Select "High Quality" from the quality dropdown
  7. Verify video quality changes
- **Expected Results**: Quality setting controls video quality
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-027: Subtitles Setting
- **Description**: Verify that subtitles setting works correctly
- **Prerequisites**: Application is loaded, video is selected
- **Test Steps**:
  1. Select a video
  2. Check the "Subtitles" checkbox
  3. Verify subtitles are displayed
  4. Uncheck the "Subtitles" checkbox
  5. Verify subtitles are not displayed
- **Expected Results**: Subtitles setting controls subtitle display
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

### 3.6 Accessibility

#### TC-028: Keyboard Navigation
- **Description**: Verify that all features can be accessed via keyboard
- **Prerequisites**: Application is loaded
- **Test Steps**:
  1. Use Tab key to navigate through the application
  2. Verify all interactive elements can be focused
  3. Use Enter key to activate buttons and links
  4. Verify all actions can be performed via keyboard
  5. Use arrow keys to adjust sliders
  6. Verify sliders can be controlled via keyboard
- **Expected Results**: All features can be accessed and operated via keyboard
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-029: Screen Reader Compatibility
- **Description**: Verify that screen readers can read all content
- **Prerequisites**: Application is loaded, screen reader is active
- **Test Steps**:
  1. Navigate through the application using screen reader
  2. Verify all content is read correctly
  3. Verify all form controls have proper labels
  4. Verify all images have alt text
  5. Verify all status messages are announced
- **Expected Results**: Screen reader reads all content correctly
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-030: Focus Indicators
- **Description**: Verify that focus indicators are visible
- **Prerequisites**: Application is loaded
- **Test Steps**:
  1. Use Tab key to navigate through the application
  2. Verify focus indicator is visible on all interactive elements
  3. Verify focus indicator is high contrast
- **Expected Results**: Focus indicators are visible and high contrast
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-031: Color Contrast
- **Description**: Verify that color contrast meets WCAG standards
- **Prerequisites**: Application is loaded
- **Test Steps**:
  1. Use a color contrast analyzer tool
  2. Check contrast of text against background
  3. Check contrast of UI controls against background
- **Expected Results**: All text and UI controls meet WCAG AA contrast requirements
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

### 3.7 Error Handling

#### TC-032: Network Error
- **Description**: Verify that network errors are handled gracefully
- **Prerequisites**: Application is loaded
- **Test Steps**:
  1. Disconnect from the internet
  2. Try to play a video
  3. Verify error message is displayed
  4. Reconnect to the internet
  5. Try to play a video
  6. Verify video plays correctly
- **Expected Results**: Appropriate error message is displayed when network is disconnected
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-033: Invalid Video Source
- **Description**: Verify that invalid video sources are handled gracefully
- **Prerequisites**: Application is loaded
- **Test Steps**:
  1. Modify video source to an invalid URL
  2. Try to play the video
  3. Verify error message is displayed
- **Expected Results**: Appropriate error message is displayed when video source is invalid
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-034: Component Error Boundary
- **Description**: Verify that component errors are caught by error boundaries
- **Prerequisites**: Application is loaded
- **Test Steps**:
  1. Modify a component to throw an error
  2. Interact with the component
  3. Verify error boundary catches the error
  4. Verify error message is displayed
  5. Verify rest of application continues to function
- **Expected Results**: Error boundary catches component errors and displays appropriate message
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

### 3.8 Edge Cases

#### TC-035: Long Video Titles
- **Description**: Verify that long video titles are handled correctly
- **Prerequisites**: Application is loaded
- **Test Steps**:
  1. Create a video with a very long title (100+ characters)
  2. View the video in the grid
  3. Verify title is truncated or wrapped appropriately
  4. Select the video
  5. Verify full title is displayed in the video player
- **Expected Results**: Long titles are displayed correctly in both grid and player views
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-036: Special Characters in Search
- **Description**: Verify that special characters in search queries are handled correctly
- **Prerequisites**: Application is loaded
- **Test Steps**:
  1. Enter search query with special characters (e.g., "React+JS")
  2. Verify search results are displayed correctly
  3. Enter search query with non-Latin characters
  4. Verify search results are displayed correctly
- **Expected Results**: Special characters in search queries are handled correctly
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-037: Many Bookmarks
- **Description**: Verify that many bookmarks are handled correctly
- **Prerequisites**: Application is loaded, video is selected
- **Test Steps**:
  1. Add 20+ bookmarks to a video
  2. Verify all bookmarks are displayed correctly
  3. Verify bookmark list is scrollable if needed
  4. Verify all bookmarks are functional
- **Expected Results**: Many bookmarks are displayed and function correctly
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-038: Many Comments
- **Description**: Verify that many comments are handled correctly
- **Prerequisites**: Application is loaded, video is selected
- **Test Steps**:
  1. Add 50+ comments to a video
  2. Verify all comments are displayed correctly
  3. Verify comment list is scrollable
  4. Verify all comments are functional
- **Expected Results**: Many comments are displayed and function correctly
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-039: No Videos Match Filter
- **Description**: Verify that empty search/filter results are handled correctly
- **Prerequisites**: Application is loaded
- **Test Steps**:
  1. Enter search query that matches no videos
  2. Verify appropriate empty state message is displayed
  3. Apply filter that matches no videos
  4. Verify appropriate empty state message is displayed
- **Expected Results**: Empty state message is displayed when no videos match search/filter
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

#### TC-040: Rapid User Interactions
- **Description**: Verify that rapid user interactions are handled correctly
- **Prerequisites**: Application is loaded
- **Test Steps**:
  1. Rapidly click multiple video cards
  2. Verify application handles rapid selection correctly
  3. Rapidly change playback rate multiple times
  4. Verify application handles rapid changes correctly
  5. Rapidly add multiple comments
  6. Verify all comments are added correctly
- **Expected Results**: Application handles rapid user interactions without errors
- **Actual Results**: [To be filled during testing]
- **Status**: [Pass/Fail]

## 4. Test Implementation

### 4.1 Automated Testing Framework
- Jest for unit and integration testing
- React Testing Library for component testing
- Cypress for end-to-end testing
- axe-core for accessibility testing

### 4.2 Test Organization
- Unit tests for individual functions and utilities
- Component tests for React components
- Integration tests for component interactions
- End-to-end tests for critical user flows

### 4.3 Test Coverage Goals
- 80%+ code coverage for core functionality
- 100% coverage for critical components (VideoPlayer, CommentBox)
- All identified bugs must have regression tests

### 4.4 Test Implementation Guidelines
- Follow AAA pattern (Arrange, Act, Assert)
- Mock external dependencies
- Use data-testid attributes for test selectors
- Write descriptive test names
- Group related tests together

## 5. Test Execution

### 5.1 Test Execution Schedule
- Unit and Component Tests: Daily during development
- Integration Tests: After feature completion
- End-to-End Tests: Before release
- Accessibility Tests: Before release
- Performance Tests: Before release

### 5.2 Test Execution Results
| Test Category | Total Tests | Passed | Failed | Blocked | Not Run |
|---------------|-------------|--------|--------|---------|---------|
| Unit Tests    |             |        |        |         |         |
| Component Tests|            |        |        |         |         |
| Integration Tests|          |        |        |         |         |
| End-to-End Tests|           |        |        |         |         |
| Accessibility Tests|        |        |        |         |         |
| Performance Tests|          |        |        |         |         |

## 6. Defect Tracking

### 6.1 Defect Summary
| Severity | Count | Fixed | Open |
|----------|-------|-------|------|
| Critical |       |       |      |
| High     |       |       |      |
| Medium   |       |       |      |
| Low      |       |       |      |

### 6.2 Defect Details
See Bug Report Document for detailed defect information.

## 7. Test Coverage

### 7.1 Functional Coverage
- Video Playback: [Percentage]
- Search and Filter: [Percentage]
- Bookmarks: [Percentage]
- Comments: [Percentage]
- User Preferences: [Percentage]

### 7.2 Non-Functional Coverage
- Accessibility: [Percentage]
- Performance: [Percentage]
- Error Handling: [Percentage]
- Edge Cases: [Percentage]

## 8. Conclusion

### 8.1 Summary
This test plan provides a comprehensive approach to testing the TinyFlix video streaming application. It covers all functional areas, accessibility requirements, performance considerations, and edge cases. The test cases are designed to verify that the application meets quality standards and provides a good user experience.

### 8.2 Recommendations
- Implement automated testing as part of the CI/CD pipeline
- Conduct regular accessibility audits
- Monitor performance metrics in production
- Establish a bug triage process for prioritizing fixes

## 9. Appendices

### 9.1 Test Environment Setup
# Playwright Java Test Implementation Guide

## Overview

This document provides a guide to the Playwright Java test implementation for the TinyFlix video streaming platform. The tests are designed to verify critical user flows, edge cases, error scenarios, and accessibility features, with a focus on the critical issues identified in the QA assessment.

## Project Structure

```
tinyflix-playwright-tests/
├── pom.xml                 # Maven project configuration
├── src/
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── tinyflix/
│       │           └── tests/
│       │               ├── BaseTest.java             # Base test class with common functionality
│       │               ├── VideoPlaybackTests.java   # Tests for video playback functionality
│       │               ├── BookmarkTests.java        # Tests for bookmark functionality
│       │               ├── CommentTests.java         # Tests for comment functionality
│       │               ├── SearchFilterTests.java    # Tests for search and filter functionality
│       │               ├── EdgeCaseTests.java        # Tests for edge cases
│       │               ├── ErrorHandlingTests.java   # Tests for error handling
│       │               └── AccessibilityTests.java   # Tests for accessibility features
│       └── resources/
│           └── testng.xml                # TestNG configuration file
```

## Setup Instructions

### Prerequisites

- Java JDK 11 or higher
- Maven 3.6 or higher
- Playwright for Java

### Installation

1. Clone the repository:
   ```
   git clone https://github.com/thendralvanan-14/Testpress-TinyFlix-QA-Assessment.git
   cd tinyflix-playwright-tests
   ```

2. Install dependencies:
   ```
   mvn install
   ```

3. Install Playwright browsers:
   ```
   mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"
   ```

### Running Tests

To run all tests:
```
mvn test
```

To run a specific test class:
```
mvn test -Dtest=VideoPlaybackTests
```

To run a specific test method:
```
mvn test -Dtest=VideoPlaybackTests#testVideoPlayPause
```
