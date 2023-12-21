package org.xproce;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.function.Executable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BMICalculatorTest {

    @Test
    void test() {
        assertTrue(BMICalculator.isDietRecommended(89.0, 1.72));
    }

    @Test
    void should_ReturnTrue_When_Diet_Recommended() {
        //given: initial conditions or inputs valus
        double weight = 89.0;
        double height = 1.72;
        // when: act
        boolean recommended = BMICalculator.isDietRecommended(weight, height);
        //then: assert
        assertTrue(recommended);
    }

    @Test
    void should_ThrowArithmeticException_When_HeightZero() {
        //given
        double weight = 50.0;
        double height = 0.0;
        // when
        Executable executable = () -> BMICalculator.isDietRecommended(weight, height);
        //then
        assertThrows(ArithmeticException.class, executable);
    }

    @Test
    void should_ReturnCoderWithWorstBMI_when_CoderListNotEmpty() {
        // given
        List<Coder> coders = new ArrayList<>();
        coders.addAll(List.of(new Coder(1.80, 60.0), new Coder(1.82, 98.0), new Coder(1.82, 64.7)));
        // when
        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
        // then
        assertEquals(1.82, coderWorstBMI.getHeight());
        assertEquals(98.0, coderWorstBMI.getWeight());
    }

    @Test
    void should_ReturnCoderWithWorstBMI_when_CoderListNotEmpty_ByTesting_AllAssertions() {
        // given
        List<Coder> coders = new ArrayList<>();
        coders.addAll(List.of(new Coder(1.80, 60.0), new Coder(1.82, 98.0), new Coder(1.82, 64.7)));
        // when
        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
        // then
        assertAll(
                () -> assertEquals(1.82, coderWorstBMI.getHeight()),
                () -> assertEquals(98.0, coderWorstBMI.getWeight())
        );
    }

    @Nested
    class IsDietRecommandedtests {
        @Test
        void should_ReturnCorrectBMIScoreArray_when_CoderListNotEmpty() {
            // given
            List<Coder> coders = new ArrayList<>();
            coders.addAll(List.of(new Coder(1.80, 60.0), new Coder(1.82, 98.0), new Coder(1.82, 64.7)));
            double[] expected = {18.52, 29.59, 19.53};
            // when
            double[] bmiScores = BMICalculator.getBMIScores(coders);
            //then
            assertArrayEquals(expected, bmiScores);
        }

        private String environement = "dev";

        @Test
        void should_ReturnCoderWithWorstBMIIn1Ms_when_CoderListHas10000Elements() {
            // given
            Assumptions.assumeTrue(this.environement.equals("prod"));
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

    @Test
    @DisplayName(">>> sample method display name")
    @Disabled
    @DisabledOnOs(OS.WINDOWS)
    void should_ReturnFalse_When_Diet_Not_Recommended() {
        //given
        double weight = 50.0;
        double height = 1.92;
        // when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);
        //then
        assertFalse(recommended);
    }

}