package com.chat.chatApplication;

import com.chat.chatApplication.model.ChatMessage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.web.server.ui.LoginPageGeneratingWebFilter;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChatApplicationTests {

	@LocalServerPort
	public int port;

	public static WebDriver driver;

	public String baseURL;

	@BeforeAll
	public void beforeAll(){
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@BeforeEach
	public void beforeEach(){
		baseURL = "http://localhost:" + port;
	}

	@Test
	public void testSignupLoginAndSubmitMessages()  {
		String username = "dinesh_yadav";
		String password = "veryDifficultPassword";
		String messageText = "Hello, How are you!!";

		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Dinesh", "Yadav", username, password);

		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		ChatPage chatPage = new ChatPage(driver);
		chatPage.sendChatMessage(messageText);

		ChatMessage sentMessage = chatPage.getFirstMessage();

		assertEquals(username, sentMessage.getUsername());
		assertEquals(messageText, sentMessage.getMessageText());

	}

	@AfterAll
	public void afterAll(){
		driver.quit();
		driver = null;
	}

}
