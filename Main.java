import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nГОЛОВНЕ МЕНЮ");
            System.out.println("1. Завдання 1");
            System.out.println("2. Завдання 2");
            System.out.println("3. Завдання 3");
            System.out.println("0. Вихід");
            System.out.print("Оберіть опцію: ");
            
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Angle.runDemo(scanner);
                    break;
                case 2:
                    Vector3D.runDemo(scanner);
                    break;
                case 3:
                    YearCalendar.runDemo(scanner);
                    break;
                case 0:
                    System.out.println("Вихід з програми.");
                    break;
                default:
                    System.out.println("Невірний вибір.");
            }
        } while (choice != 0);

        scanner.close();
    }
}

class Angle implements Comparable<Angle> {
    private int degrees;
    private int minutes;

    public Angle(int degrees, int minutes) {
        setAngle(degrees, minutes);
    }

    public void setAngle(int degrees, int minutes) {
        int totalMinutes = degrees * 60 + minutes;
        int totalMinInCircle = 360 * 60;
        
        totalMinutes = totalMinutes % totalMinInCircle;
        if (totalMinutes < 0) {
            totalMinutes += totalMinInCircle;
        }
        
        this.degrees = totalMinutes / 60;
        this.minutes = totalMinutes % 60;
    }

    public void normalize() {
        setAngle(this.degrees, this.minutes);
    }

    public double toRadians() {
        double totalDegrees = degrees + (double) minutes / 60.0;
        return Math.toRadians(totalDegrees);
    }

    public void increase(int deg, int min) {
        int totalMin = (this.degrees + deg) * 60 + (this.minutes + min);
        setAngle(0, totalMin);
    }

    public void decrease(int deg, int min) {
        int totalMin = (this.degrees - deg) * 60 + (this.minutes - min);
        setAngle(0, totalMin);
    }

    public double getSine() {
        return Math.sin(toRadians());
    }

    @Override
    public int compareTo(Angle other) {
        int thisTotal = this.degrees * 60 + this.minutes;
        int otherTotal = other.degrees * 60 + other.minutes;
        return Integer.compare(thisTotal, otherTotal);
    }

    @Override
    public String toString() {
        return degrees + "° " + minutes + "'";
    }

    public static void runDemo(Scanner scanner) {
        System.out.print("Введіть градуси першого кута: ");
        int deg1 = scanner.nextInt();
        System.out.print("Введіть хвилини першого кута: ");
        int min1 = scanner.nextInt();

        Angle angle1 = new Angle(deg1, min1);
        System.out.println("Створений кут: " + angle1);
        System.out.println("У радіанах: " + angle1.toRadians());
        System.out.println("Синус кута: " + angle1.getSine());

        System.out.print("Введіть кількість градусів для збільшення: ");
        int incDeg = scanner.nextInt();
        System.out.print("Введіть кількість хвилин для збільшення: ");
        int incMin = scanner.nextInt();
        angle1.increase(incDeg, incMin);
        System.out.println("Кут після збільшення (з приведеним діапазоном 0-360): " + angle1);

        System.out.print("Введіть градуси другого кута для порівняння: ");
        int deg2 = scanner.nextInt();
        System.out.print("Введіть хвилини другого кута для порівняння: ");
        int min2 = scanner.nextInt();
        Angle angle2 = new Angle(deg2, min2);
        System.out.println("Другий кут: " + angle2);

        int comp = angle1.compareTo(angle2);
        if (comp > 0) {
            System.out.println("Перший кут більший за другий.");
        } else if (comp < 0) {
            System.out.println("Перший кут менший за другий.");
        } else {
            System.out.println("Кути рівні.");
        }
    }
}

class Vector3D {
    private double x, y, z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vector3D multiply(double scalar) {
        return new Vector3D(x * scalar, y * scalar, z * scalar);
    }

    public Vector3D add(Vector3D other) {
        return new Vector3D(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public double dot(Vector3D other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public Vector3D cross(Vector3D other) {
        double cx = this.y * other.z - this.z * other.y;
        double cy = this.z * other.x - this.x * other.z;
        double cz = this.x * other.y - this.y * other.x;
        return new Vector3D(cx, cy, cz);
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f, %.2f)", x, y, z);
    }

    public static void runDemo(Scanner scanner) {
        System.out.println("\nДемонстрація класу Vector3D");
        System.out.println("Введіть координати першого вектора (x y z): ");
        double x1 = scanner.nextDouble();
        double y1 = scanner.nextDouble();
        double z1 = scanner.nextDouble();
        Vector3D v1 = new Vector3D(x1, y1, z1);

        System.out.println("Введіть координати другого вектора (x y z): ");
        double x2 = scanner.nextDouble();
        double y2 = scanner.nextDouble();
        double z2 = scanner.nextDouble();
        Vector3D v2 = new Vector3D(x2, y2, z2);

        System.out.println("Вектор 1: " + v1);
        System.out.println("Модуль Вектора 1: " + v1.magnitude());

        System.out.print("Введіть число для множення першого вектора: ");
        double scalar = scanner.nextDouble();
        System.out.println("Результат множення на скаляр: " + v1.multiply(scalar));

        System.out.println("Сума векторів (1 + 2): " + v1.add(v2));
        System.out.println("Скалярний добуток (1 * 2): " + v1.dot(v2));
        System.out.println("Векторний добуток (1 x 2): " + v1.cross(v2));
    }
}

class YearCalendar {
    public static void printDateByDayOfYear(int year, int dayOfYear) {
        GregorianCalendar calendar = new GregorianCalendar();
        
        int maxDays = calendar.isLeapYear(year) ? 366 : 365;
        if (dayOfYear < 1 || dayOfYear > maxDays) {
            System.out.println("Помилка: для цього року день має бути в діапазоні від 1 до " + maxDays + ".");
            return;
        }

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);

        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int monthIndex = calendar.get(Calendar.MONTH);
        int dayOfWeekIndex = calendar.get(Calendar.DAY_OF_WEEK);

        String[] monthNames = {
            "січня", "лютого", "березня", "квітня", "травня", "червня",
            "липня", "серпня", "вересня", "жовтня", "листопада", "грудня"
        };

        String[] dayOfWeekNames = {
            "", "неділя", "понеділок", "вівторок", "середа", "четвер", "п'ятниця", "субота"
        };

        System.out.println("Результат: " + dayOfMonth + " " + monthNames[monthIndex] + ", " + dayOfWeekNames[dayOfWeekIndex]);
    }

    public static void runDemo(Scanner scanner) {
        System.out.print("Введіть рік: ");
        int year = scanner.nextInt();
        System.out.print("Введіть порядковий номер дня в році: ");
        int dayOfYear = scanner.nextInt();

        printDateByDayOfYear(year, dayOfYear);
    }
}