import org.testng.annotations.*;

import static org.example.TestNgTests.*;
import static org.testng.Assert.*;

public class TestNgTests {

    static int k = 0;

    @BeforeClass(description = "Начинаем тестирование")
    public static void beginTesting() {
        System.out.println("*** Начинаем тестирование ***");
    }

    @BeforeMethod(description = "Добавим красок")
    public void colorChange() {
        System.out.print(colors[k]);
        k++;
    }

    @AfterGroups(groups = "factorial")
    @BeforeGroups(groups = "compareTest")
    public void separator() {
        System.out.println();
    }

    // Факториал
    @Test(description = "Тест модуля 'factorial'", groups = "factorial")
    public void factorialTest() {
        assertEquals(factorial(5), 120);
        System.out.println(factorial(5));
    }

    @Test(description = "Тест исключения модуля 'factorial' для отрицательного числа", groups = "factorial")
    public void factorialExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> factorial(-6));
    }

    // Площадь
    @Test(description = "Тест модуля 'square'")
    public void squareTest() {
        assertEquals(square(2, 3, 4), 2.9, 0.01);
        System.out.println(square(2, 3, 4));
    }

    // Арифметика
    @DataProvider(name = "arithmeticDate")
    public Object[][] arithmeticDate() {
        return new Object[][]{
                {1, "+", 2, 3.0},
                {3, "-", 4, -1.0},
                {5, "*", 6, 30.0},
                {5, "/", 2, 2.5}
        };
    }

    @Test(description = "Тест модуля 'arithmetic'", dataProvider = "arithmeticDate", groups = "arithmetic")
    public void arithmeticTest(int number1, String expression, int number2, double result) {
        assertEquals(result, arithmetic(number1, expression, number2), 0.01);
        System.out.print("\n" + arithmetic(number1, expression, number2));
    }

    @Test(description = "Тест исключения модуля 'arithmetic' при не верном указании арифметической операции", groups = "arithmetic")
    public void arithmeticExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> arithmetic(4, "aaa", 2));
    }

    // Сравнение
    @Test(description = "Тест модуля сравнения 'compare'", groups = "compareTest")
    public void compareTest() {
        assertTrue(compare(5, 4));
        assertFalse(compare(14, 15));
        assertFalse(compare(50, 50));
        System.out.println();
    }

    @AfterClass(description = "Завершаем тестирование")
    public static void endTesting() {
        System.out.println("\n\n*** Завершаем тестирование ***" + colors[k]);
    }
}