import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.example.JunitTests.*;

public class JunitTests {

    private static int k = 0;

    @BeforeAll
    @DisplayName("Начинаем тестирование")
    public static void beginTesting() {
        System.out.println("*** Начинаем тестирование ***");
    }

    @BeforeEach
    @DisplayName("Добавим красок")
    public void colorChange() {
        System.out.print(colors[k]);
        k++;
    }

    // Факториал
    @Test
    @DisplayName("Тест модуля 'factorial'")
    public void factorialTest() {
        Assertions.assertEquals(720, factorial(6));
        System.out.println("\n" + factorial(6));
    }

    @Test
    @DisplayName("Тест исключения модуля 'factorial' для отрицательного числа")
    public void factorialExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> factorial(-6));
    }

    // Площадь
    @Test
    @DisplayName("Тест модуля 'square'")
    public void squareTest() {
        Assertions.assertEquals(2.9, square(2, 3, 4), 0.01);
        System.out.print("\n\n" + square(2, 3, 4));
    }

    // Арифметика
    @ParameterizedTest
    @DisplayName("Тест модуля 'arithmetic'")
    @CsvSource({
            "1, '+', 2, 3.0",
            "3, '-', 4, -1.0",
            "5, '*', 6, 30.0",
            "5, '/', 2, 2.5"
    })
    public void arithmeticTest(int number1, String expression, int number2, double result) {
        Assertions.assertEquals(result, arithmetic(number1, expression, number2), 0.01);
        System.out.print("\n" + arithmetic(number1, expression, number2));
    }

    @Test
    @DisplayName("Тест исключения модуля 'arithmetic' при не верном указании арифметической операции")
    public void arithmeticExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> arithmetic(4, "aaa", 2));
    }

    // Сравнение
    @Test
    @DisplayName("Тест модуля сравнения 'compare'")
    public void compareTest() {
        Assertions.assertAll(
                () -> Assertions.assertTrue(compare(5, 4)),
                () -> Assertions.assertFalse(compare(14, 15)),
                () -> Assertions.assertFalse(compare(50, 50)));
        System.out.println();
    }

    @AfterAll
    @DisplayName("Завершаем тестирование")
    public static void endTesting() {
        System.out.println("\n\n*** Завершаем тестирование ***");
    }
}