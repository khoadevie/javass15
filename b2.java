import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

class Subject {
    private String code;
    private String name;
    private int credits;
    private LocalDate startDate;

    public Subject(String code, String name, int credits, LocalDate startDate) {
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.startDate = startDate;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public int getCredits() { return credits; }
    public LocalDate getStartDate() { return startDate; }

    public String toString() {
        return code + " | " + name + " | " + credits + " | " + startDate;
    }
}

class SubjectManager<T extends Subject> {
    private List<T> list = new ArrayList<>();

    public void add(T s) {
        list.add(s);
    }

    public boolean deleteByCode(String code) {
        return list.removeIf(s -> s.getCode().equals(code));
    }

    public Optional<T> findByName(String name) {
        return list.stream()
                .filter(s -> s.getName().toLowerCase().contains(name.toLowerCase()))
                .findFirst();
    }

    public List<T> filterCredits(int min) {
        return list.stream()
                .filter(s -> s.getCredits() > min)
                .toList();
    }

    public List<T> getAll() {
        return list;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SubjectManager<Subject> manager = new SubjectManager<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        while (true) {
            System.out.println("\n1. Hiển thị danh sách");
            System.out.println("2. Thêm môn học");
            System.out.println("3. Xóa môn học");
            System.out.println("4. Tìm theo tên");
            System.out.println("5. Lọc theo tín chỉ > 3");
            System.out.println("6. Thoát");
            System.out.print("Chọn: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                continue;
            }

            switch (choice) {
                case 1:
                    manager.getAll().forEach(System.out::println);
                    break;

                case 2:
                    try {
                        System.out.print("Code: ");
                        String code = sc.nextLine();
                        System.out.print("Name: ");
                        String name = sc.nextLine();
                        System.out.print("Credits: ");
                        int credits = Integer.parseInt(sc.nextLine());
                        if (credits < 0 || credits > 10) throw new IllegalArgumentException();
                        System.out.print("Start date (dd-MM-yyyy): ");
                        LocalDate date = LocalDate.parse(sc.nextLine(), fmt);
                        manager.add(new Subject(code, name, credits, date));
                    } catch (Exception e) {
                        System.out.println("Dữ liệu không hợp lệ");
                    }
                    break;

                case 3:
                    System.out.print("Nhập code cần xóa: ");
                    if (!manager.deleteByCode(sc.nextLine())) System.out.println("Không tìm thấy");
                    break;

                case 4:
                    System.out.print("Nhập tên: ");
                    Optional<Subject> s = manager.findByName(sc.nextLine());
                    System.out.println(s.map(Object::toString).orElse("Không có môn học phù hợp"));
                    break;

                case 5:
                    manager.filterCredits(3).forEach(System.out::println);
                    break;

                case 6:
                    return;
            }
        }
    }
}
