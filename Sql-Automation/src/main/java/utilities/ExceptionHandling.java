package utilities;


import org.openqa.selenium.NoSuchElementException;

public class ExceptionHandling {

    public static void noSuchElementExcption(NoSuchElementException e){
        throw new NoSuchElementException("The element is not found on the page");
    }

    public static void exception(Exception e) throws Exception {
        throw new Exception("The element is not found on the page");
    }

    public static void handleIllegalArgumentException(IllegalArgumentException e) {
    }

    public static void handleGeneralException(Exception e) {
    }
}
