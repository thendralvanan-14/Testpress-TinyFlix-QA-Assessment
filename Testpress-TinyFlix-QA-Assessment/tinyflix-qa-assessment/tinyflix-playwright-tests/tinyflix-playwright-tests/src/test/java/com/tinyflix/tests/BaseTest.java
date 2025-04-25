package com.tinyflix.tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import org.testng.annotations.*;
import static org.testng.Assert.*;

public class BaseTest {
    
    // Playwright objects
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    
    // Application URL use your local host and correct port number
    protected String baseUrl = "http://localhost:5173/";
    
    @BeforeClass
    public void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(true)
                .setSlowMo(50));

    }
    
    @BeforeMethod
    public void createContextAndPage() {
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1280, 720)
                .setIgnoreHTTPSErrors(true));
        
        // Enable accessibility testing
        context.setExtraHTTPHeaders(java.util.Map.of("X-Playwright-Accessibility", "true"));
        
        page = context.newPage();
    }
    
    @AfterMethod
    public void closeContext() {
        if (context != null) {
            context.close();
            context = null;
            page = null;
        }
    }
    
    @AfterClass
    public void closeBrowser() {
        if (browser != null) {
            browser.close();
            browser = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }
    protected void navigateToApp() {
        page.navigate(baseUrl);
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    protected void selectVideo(String title) {
        page.locator("text=" + title).first().click();
        page.waitForSelector(".video-player");
    }

    protected void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
