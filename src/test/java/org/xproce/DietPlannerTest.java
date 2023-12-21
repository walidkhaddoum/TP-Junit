package org.xproce;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.function.Executable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DietPlannerTest {

    private DietPlanner dietPlanner;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before all unit tests");
    }


    @BeforeEach
    void setup() {
        this.dietPlanner = new DietPlanner(20, 30, 50);
    }

    @AfterEach
    void afterEach() {
        System.out.println("A unit test was finished");
    }

    /*@Test
    void should_ReturnCorrectDiet_Plan_when_CorrectCoder() {
        // given
        Coder coder = new Coder(1.82, 75.0, 26, Gender.MALE);
        DietPlan expected = new DietPlan(2202, 110, 73, 275);
        // when
        DietPlan actual = dietPlanner.calculateDiet(coder);
        // then
        assertAll(
                () -> assertEquals(expected.getCarbohydrate(), actual.getCarbohydrate()),
                () -> assertEquals(expected.getFat(), actual.getFat()),
                () -> assertEquals(expected.getProtein(), actual.getProtein()),
                () -> assertEquals(expected.getCalories(), actual.getCalories())
        );
    }*/

    @AfterAll
    static void afterAll() {
        System.out.println("After all unit tests.");
    }


    @ParameterizedTest
    @ValueSource(doubles = {75.0, 89.0, 95.0, 110.0})
    void should_ReturnTrue_When_Diet_Recommended_SIngleValue(Double coderWeight) {
        //given
        double weight = coderWeight;
        double height = 1.72;
        // when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);
        //then
        assertTrue(recommended);
    }

    @ParameterizedTest
    @CsvSource(value = {"89.0, 1.72", "95.0, 1.75", "110.0, 1.78"})
    void should_ReturnTrue_When_Diet_Recommended_DoubleValues(Double coderWeight, Double
            coderHeight) {
        //given
        double weight = coderWeight;
        double height = coderHeight;
        // when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);
        //then
        assertTrue(recommended);
    }


    @ParameterizedTest(name = "weight = {0}, height = {1}")
    @CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
    void should_ReturnTrue_When_Diet_Recommended_MultipleValues(
            Double coderWeight, Double coderheight) {
        //given
        double weight = coderWeight;
        double height = coderheight;
        // when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);
        //then
        assertTrue(recommended);
    }

    /*
    @RepeatedTest( 10)
    void should_ReturnCorrectDiet_Plan_when_CorrectCoder() {
        // given
        Coder coder = new Coder(1.82, 75.0, 26, Gender.MALE);
        DietPlan expected = new DietPlan(2202, 110, 73, 275);
        // when
        DietPlan actual = dietPlanner.calculateDiet(coder);
        // then
        assertAll(
                () -> assertEquals(expected.getCarbohydrate(), actual.getCarbohydrate()),
                () -> assertEquals(expected.getFat(), actual.getFat()),
                () -> assertEquals(expected.getProtein(), actual.getProtein()),
                () -> assertEquals(expected.getCalories(), actual.getCalories())
        );
    }*/

    @RepeatedTest(value = 10, name = RepeatedTest.LONG_DISPLAY_NAME)
    void should_ReturnCorrectDiet_Plan_when_CorrectCoder() {
        // given
        Coder coder = new Coder(1.82, 75.0, 26, Gender.MALE);
        DietPlan expected = new DietPlan(2202, 110, 73, 275);
        // when
        DietPlan actual = dietPlanner.calculateDiet(coder);
        // then
        assertAll(
                () -> assertEquals(expected.getCarbohydrate(), actual.getCarbohydrate()),
                () -> assertEquals(expected.getFat(), actual.getFat()),
                () -> assertEquals(expected.getProtein(), actual.getProtein()),
                () -> assertEquals(expected.getCalories(), actual.getCalories())
        );
    }

    @Test
    void should_ReturnCoderWithWorstBMIIn1Ms_when_CoderListHas10000Elements() {
        // given
        List<Coder> coders = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            coders.add(new Coder(1.0 + i, 10.0 + i));
        }
        //when
        Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);
        //then
        assertTimeout(Duration.ofMillis(500), executable);
    }

}
