package pages;

import Common.BasePage;


public class loginPageObjects extends BasePage {
    public static String user_Name = "//input[@placeholder='User Name']";
    public static String password = "//input[@placeholder='Password']";

    public static String captcha = "//iframe[contains(@src, 'recaptcha')]";

    public static String submit_Btn = "//span[normalize-space()='Submit']";

    // The <button> element wrapping the Submit label - holds the disabled state
    public static String submitButton = "//button[.//span[normalize-space()='Submit']]";

    public static String usernameRequiredMsg = "//*[normalize-space(text())='Username is required']";

    public static String loggedInUser = "(//button//span[@class='mat-button-wrapper'])[2]";

    public static String toastMessage = "//div[contains(@class, 'p-toast-detail')]";

    public static String errorToast = "//div[contains(@class, 'p-toast-message-error')]";

    public static String avatarIcon = "//span[@class='avatar-text']";

    public static String logoutBtn = "//span[normalize-space()='Logout']";

}
