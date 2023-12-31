package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Selenide.*;

class CardDeliveryTest {

    String generateDate (int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldAcceptCardDelivery() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Уфа");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        String setDate = generateDate(7, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(setDate);
        $("[data-test-id='name'] input").setValue("Иванов-Петров Иван");
        $("[data-test-id='phone'] input").setValue("+79991112233");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='notification']").
                shouldBe(Condition.visible, Duration.ofSeconds(15)).
                shouldBe(Condition.text("Встреча успешно забронирована на " + setDate));
    }

}
