import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class TestCardDelivery {
    LocalDate today = LocalDate.now();
    LocalDate newDate = today.plusDays(3);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String formatDateNew = newDate.format(formatter);


    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    //правильное заполнение
    @Test
    void shouldSubitRequest() {
        $("[data-test-id=city] input").setValue("Вологда");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(formatter.format(newDate));
        $("[data-test-id=name] input").setValue("Сергей");
        $("[data-test-id=phone] input").setValue("+79212223344");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(withText("Успешно!"))
                .shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=notification] .notification__content").shouldHave(exactText("Встреча успешно" +
                " забронирована на " + formatDateNew));
    }

}
