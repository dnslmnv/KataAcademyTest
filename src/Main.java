import java.util.*;
public class Main {
    public enum Romanian{
        I, II, III, IV, V, VI, VII, VIII, IX, X
    }
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        //проверка ошибки математической операции
        if (input.length() < 3){
            throw new Exception("Строка не является математической операцией");
        }

        //проверка ошибки нескольких операторов
        int exception_counter = 0;
        char[] exception_array = input.toCharArray();
        for (char element:exception_array){
            if (element == '+' | element == '/' | element == '-' | element == '*'){
                exception_counter += 1;
            }
        }
        if (exception_counter > 1){
            throw new Exception("Формат математической операции не удовлетворяет заданию - два операнда и один оператор " +
                    "(+, -, /, *)");
        }

        //создаем флаг для определения типа операции
        int plus = input.indexOf("+");
        int minus = input.indexOf("-");
        int divide = input.indexOf("/");
        int multiply = input.indexOf("*");
        int flag = 0;
        if (plus != -1){
            flag = 1;
        } else if (minus != -1){
            flag = 2;
        } else if (divide != -1){
            flag = 3;
        } else if (multiply != -1){
            flag = 4;
        }

        /*
        Разбиваем строку на два элемента и сравниваем системы счисления, обрабатываем ошибки больших значений
        и разных систем счисления
        */
        String[] elements = elements(input, flag);
        int type1 = type(elements[0]);
        int type2 = type(elements[1]);
        int num1;
        int num2;
        if (type1 + type2 == 2){
            num1 = Integer.parseInt(elements[0]);
            num2 = Integer.parseInt(elements[1]);
            if (num1 > 10 | num2 > 10){
                throw new Exception("Значение больше 10");
            }
            int answer = answer(num1, num2, flag);
            System.out.println(answer);
        } else if (type1 + type2 == 4){
            num1 = romanian(elements[0]);
            num2 = romanian(elements[1]);
            if (num1 > 10 | num2 > 10){
                throw new Exception("Значение больше 10");
            }
            int answer = answer(num1, num2, flag);
            romanianResult(answer);
        } else {
            throw new Exception("Используются разные системы счисления");
        }



    }

    //Функция для разбития строки на два элемента
    static String[] elements(String string, int flag){
        switch (flag){
            case 1:
                return string.split("\\+");
            case 2:
                return string.split("-");
            case 3:
                return string.split("/");
            case 4:
                return string.split("\\*");
            default:
                String[] a = {""};
                return a;
        }
    }

    //Функция для определения типа числа - арабское или римское
    static int type(String string){
        try {
            Integer.parseInt(string);
            return 1;
        } catch (NumberFormatException e){
            return 2;
        }
    }

    /*
    Функция для перевода числа из римского в арабское, чтобы можно было отследить ошибку с отрицательными
    числами в римской системе счисления
     */
    static int romanian(String string){
        int five_position = string.indexOf("V");
        int ten_position = string.indexOf("X");
        if (five_position != -1){
            if (string.length() == 1){
                return 5;
            } else{
                char[] number_array = string.toCharArray();
                int result = 5;
                for (int i = 0; i < number_array.length; i++){
                    String tmp = "" + number_array[i];
                    Romanian number = Romanian.valueOf(tmp);
                    if (i < five_position & number_array[i] != 'V'){
                        result -= number.ordinal() + 1;
                    } else if (i > five_position & number_array[i] != 'V'){
                        result += number.ordinal() + 1;
                    }
                }
                return result;
            }
        } else if (ten_position != -1){
            if (string.length() == 1){
                return 10;
            } else{
                return 9;
            }
        } else {
            Romanian number = Romanian.valueOf(string);
            return number.ordinal() + 1;
        }
    }

    //Функция для вывода суммы римских чисел, обработка ошибки отрицательных чисел в римской системе счисления
    static void romanianResult(int a) throws Exception{
        if (a < 1){
            throw new Exception("В римской системе нет отрицательных чисел");
        }
        int ten = a / 10;
        int ed = a - (ten * 10);
        if (ten == 0 & ed <= 9){
            Romanian num = Romanian.values()[ed - 1];
            System.out.println(num);
        } else if (ten == 1 & ed == 0) {
            System.out.println("X");
        } else if (ten >= 2 & ten <= 3 & ed == 0) {
            System.out.println("X".repeat(ten));
        } else if (ten >= 2 & ten <= 3 & ed <= 9) {
            Romanian num = Romanian.values()[ed - 1];
            System.out.println("X".repeat(ten) + num);
        } else if (ten == 4 & ed == 0) {
            System.out.println("XL");
        } else if (ten == 4 & ed <= 9) {
            Romanian num = Romanian.values()[ed - 1];
            System.out.println("XL" + num);
        } else if (ten >= 5 & ten <= 8 & ed == 0) {
            System.out.println("L" + "X".repeat(ten - 5));
        } else if (ten >= 5 & ten <= 8 & ed <= 9) {
            Romanian num = Romanian.values()[ed - 1];
            System.out.println("L" + "X".repeat(ten - 5) + num);
        } else if (ten == 9 & ed == 0) {
            System.out.println("XC");
        } else if (ten == 9 & ed <= 9) {
            Romanian num = Romanian.values()[ed - 1];
            System.out.println("XC" + num);
        } else {
            System.out.println("C");
        }
    }

    //Функция для совершения математической операции
    static int answer(int num1, int num2, int flag){ //
        switch (flag){
            case 1:
                return num1 + num2;
            case 2:
                return num1 - num2;
            case 3:
                return num1/num2;
            case 4:
                return num1 * num2;
            default:
                return 0;
        }
    }


}