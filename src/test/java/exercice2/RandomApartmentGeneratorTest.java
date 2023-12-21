package exercice2;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

class RandomApartmentGeneratorTest {

    private ApartmentRater apartmentRater;

    @Before
    public void setUp() {
        apartmentRater = new ApartmentRater();
    }

    @Test
    public void should_ReturnCorrectRating_When_CorrectApartment() {
        // Best price/area ratio (rating 0)
        Apartment apartment1 = new Apartment(60.0, new BigDecimal(3000.0 * 60));
        int rating1 = apartmentRater.rateApartment(apartment1);
        assertEquals(0, rating1);

        // Moderate price/area ratio (rating 1)
        Apartment apartment2 = new Apartment(50.0, new BigDecimal(7000.0 * 50));
        int rating2 = apartmentRater.rateApartment(apartment2);
        assertEquals(1, rating2);

        // Worst price/area ratio (rating 2)
        Apartment apartment3 = new Apartment(40.0, new BigDecimal(10000.0 * 40));
        int rating3 = apartmentRater.rateApartment(apartment3);
        assertEquals(2, rating3);
    }

    @Test
    public void should_ReturnErrorValue_When_IncorrectApartment() {
        // Incorrect apartment with area 0.0
        Apartment incorrectApartment = new Apartment(0.0, new BigDecimal(0.0));
        int rating = apartmentRater.rateApartment(incorrectApartment);
        assertEquals(-1, rating);
    }

    @Test
    public void should_CalculateAverageRating_When_CorrectApartmentList() {
        // Calculate average rating for a list of correct apartments
        List<Apartment> apartments = new ArrayList<>();
        apartments.add(new Apartment(60.0, new BigDecimal(3000.0 * 60)));
        apartments.add(new Apartment(50.0, new BigDecimal(7000.0 * 50)));
        apartments.add(new Apartment(40.0, new BigDecimal(10000.0 * 40)));

        double averageRating = apartmentRater.calculateAverageRating(apartments);
        assertEquals(1.0, averageRating, 0.01); // Expected average rating is 1.0
    }

    @Test
    public void should_ThrowExceptionInCalculateAverageRating_When_EmptyApartmentList() {
        // Calculate average rating for an empty list
        List<Apartment> apartments = new ArrayList<>();

        Executable executable = () -> apartmentRater.calculateAverageRating(apartments);
        assertThrows(RuntimeException.class, executable);
    }

    @Test
    public void should_GenerateCorrectApartment_When_DefaultMinAreaMinPrice() {
        RandomApartmentGenerator generator = new RandomApartmentGenerator();

        for (int i = 0; i < 10; i++) {
            Apartment apartment = generator.generate();

            double area = apartment.getArea();
            BigDecimal price = apartment.getPrice();
            BigDecimal pricePerSquareMeter = price.divide(new BigDecimal(area), 2, RoundingMode.HALF_UP);

            // Check if area is within [30.0120]
            assertTrue(area >= 30.0 && area <= 120.0);

            // Check if price per square meter is within [3000.0, 12000.0]
            assertTrue(pricePerSquareMeter.doubleValue() >= 3000.0 && pricePerSquareMeter.doubleValue() <= 12000.0);
        }
    }

    @Test
    public void should_GenerateCorrectApartment_When_CustomMinAreaMinPrice() {
        double minArea = 50;
        BigDecimal minPricePerSquareMeter = new BigDecimal(5000.0);
        RandomApartmentGenerator generator = new RandomApartmentGenerator(minArea, minPricePerSquareMeter);

        for (int i = 0; i < 10; i++) {
            Apartment apartment = generator.generate();

            double area = apartment.getArea();
            BigDecimal price = apartment.getPrice();
            BigDecimal pricePerSquareMeter = price.divide(new BigDecimal(area), 2, RoundingMode.HALF_UP);

            // Check if area is within [minArea, minArea * 4.0]
            assertTrue(area >= minArea && area <= minArea * 4.0);
            // Check if price per square meter is within [minPricePerSquareMeter, minPricePerSquareMeter * 4.0]
            assertTrue(pricePerSquareMeter.doubleValue() >= minPricePerSquareMeter.doubleValue() && pricePerSquareMeter.doubleValue() <= minPricePerSquareMeter.doubleValue() * 4.0);
        }
    }
}