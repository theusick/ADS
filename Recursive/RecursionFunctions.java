public class RecursionFunctions {

    public static double powRecursive(double number, int power) {
        if (power == 0) {
            return 1;
        }
        if (number == 0) {
            return 0;
        }
        if (power < 0) {
            number = 1 / number;
            power = -power;
        }

        return number * powRecursive(number, power - 1);
    }

    public static int sumDigitsRecursive(int number) {
        if (number == 0) {
            return 0;
        }

        return (number % 10) + sumDigitsRecursive(number / 10);
    }

}
