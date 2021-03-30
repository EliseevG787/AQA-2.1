package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class CardOrderTest {
    @BeforeEach
    void Setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldFormFilledOutCorrectly() {
        $("[data-test-id=name] input").setValue("Михаил Салтыков-Щедрин");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=order-success]")
                .shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldIncorrectFormFilling() {
        $("[data-test-id=name] input").setValue("Ivan Petrov");
        $("[data-test-id=phone] input").setValue("79270000000");
        $(".button").click();
        $(".input_invalid .input__sub")
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldIncorrectFillingTelephone() {
        $("[data-test-id=name] input").setValue("Иван Петров");
        $("[data-test-id=phone] input").setValue("123456789");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(".input_invalid .input__sub")
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldCheckboxIsNotSelected() {
        $("[data-test-id=name] input").setValue("Василий Иванов");
        $("[data-test-id=phone] input").setValue("+12345678901");
        $(".button").click();
        $(".input_invalid .checkbox__text")
                .shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных" +
                        " и разрешаю сделать запрос в бюро кредитных историй"));
    }

    @Test
    void shouldFieldNameIsEmpty() {
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+12345678901");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(".input_invalid .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldPhoneFieldIsEmpty() {
        $("[data-test-id=name] input").setValue("Василий");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(".input_invalid .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSubmittingEmptyForm() {
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("");
        $(".button").click();
        $("[data-test-id=name] .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }
}




