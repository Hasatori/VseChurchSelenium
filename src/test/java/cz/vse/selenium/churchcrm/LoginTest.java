package cz.vse.selenium.churchcrm;

import cz.vse.selenium.churchcrm.testframework.page.ChangePassword;
import cz.vse.selenium.churchcrm.testframework.page.Dashboard;
import cz.vse.selenium.churchcrm.testframework.page.LoginPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static cz.vse.selenium.churchcrm.testframework.page.LoginPage.VALID_PASSWORD;
import static cz.vse.selenium.churchcrm.testframework.page.LoginPage.VALID_USERNAME;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest extends AChurchCrmTest {

    private static final String INVALID_USERNAME = "INVALID_USERNAME";
    private static final String INVALID_PASSWORD = "INVALID_PASSWORD";

    @Test
    public void loginTest_CorrectCredentials_ShouldLogin() {
        // Given - User is on the login page
        LoginPage loginPage = new LoginPage(driver);
        loginPage.goTo();

        // When - User fills valid credentials
        loginPage.loginWithValidCredentials();

        // Then - User should login = Url is dashboard url, title is dashboard title and login form is not present
        assertAll(
                () -> assertEquals(new Dashboard(driver).getUrl(), driver.getCurrentUrl()),
                () -> assertEquals("ChurchCRM: Welcome to", driver.getTitle()),
                loginPage::assertLoginFormIsNotPresent
        );
    }

    @ParameterizedTest
    @MethodSource(value = "provideNotEmptyInvalidUsernamePasswordCombinations")
    public void loginTest_InvalidCredentials_LoginErrorMessageShouldBeShown(String username, String password) {
        // Given - User is on the login page
        LoginPage loginPage = new LoginPage(driver);
        loginPage.goTo();

        // When - User fills invalid credentials
        loginPage.login(username, password);

        // Then - Login error message should be shown, current url is not dashboard and login form is present
        assertAll(
                () -> assertEquals(loginPage.getUrl(), driver.getCurrentUrl()),
                loginPage::assertLoginFormIsPresent,
                loginPage::assertLoginErrorMessageIsShown
        );
    }

    protected static Stream<Arguments> provideNotEmptyInvalidUsernamePasswordCombinations() {
        return Stream.of(
                // Applied black box method decision table for all possible combinations of entries
                Arguments.of(INVALID_USERNAME, VALID_PASSWORD),
                Arguments.of(VALID_USERNAME, INVALID_PASSWORD),
                Arguments.of(INVALID_USERNAME, INVALID_PASSWORD)
        );
    }

    @ParameterizedTest
    @MethodSource(value = "provideEmptyUsernameAndPasswordCombinations")
    public void loginTest_UserNameOrPasswordNotFillByUser_ShouldNotLogIn(String username, String password) {
        //Given - User is on the login page
        LoginPage loginPage = new LoginPage(driver);
        loginPage.goTo();

        //When - Username is not fill by user
        loginPage.login(username, password);

        //Then - User is still on login page and login form is present
        assertAll(
                () -> assertEquals(loginPage.getUrl(), driver.getCurrentUrl()),
                loginPage::assertLoginFormIsPresent
        );
    }

    protected static Stream<Arguments> provideEmptyUsernameAndPasswordCombinations() {
        // Applied black box method decision table for all possible combinations of entries
        return Stream.of(
                Arguments.of("", VALID_PASSWORD),
                Arguments.of(VALID_USERNAME, ""),
                Arguments.of("", ""),
                Arguments.of("INVALID_USERNAME", ""),
                Arguments.of("", "INVALID_PASSWORD")
        );
    }

    @Test
    public void loginTest_IForgotMyPasswordClicked_ShouldBeRedirectedToTheChangePasswordPage() {
        // Given - User is on the login page
        LoginPage loginPage = new LoginPage(driver);
        loginPage.goTo();

        // When - User click on I forgot my password
        loginPage.goToForgotMyPasswod();

        // Then - User should be on the change password page
        assertEquals(new ChangePassword(driver).getUrl(), driver.getCurrentUrl());
    }

}
