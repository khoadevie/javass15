import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Movie {
    private String id;
    private String title;
    private String director;
    private LocalDate releaseDate;
    private double rating;

    public Movie(String id, String title, String director, LocalDate releaseDate, double rating) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDirector() { return director; }
    public LocalDate getReleaseDate() { return releaseDate; }
    public double getRating() { return rating; }

    public void setTitle(String title) { this.title = title; }
    public void setDirector(String director) { this.director = director; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }
    public void setRating(double rating) { this.rating = rating; }

    public String toString() {
        return id + " | " + title + " | " + director + " | " + releaseDate + " | " + rating;
    }
}

class MovieManager<T extends Movie> {
    private List<T> movies = new ArrayList<>();

    public void add(T movie) {
        movies.add(movie);
    }

    public boolean deleteById(String id) {
        return movies.removeIf(m -> m.getId().equals(id));
    }

    public T findById(String id) {
        for (T m : movies) if (m.getId().equals(id)) return m;
        return null;
    }

    public List<T> findByTitle(String title) {
        List<T> result = new ArrayList<>();
        for (T m : movies) if (m.getTitle().toLowerCase().contains(title.toLowerCase())) result.add(m);
        return result;
    }

    public List<T> filterRating(double min) {
        List<T> result = new ArrayList<>();
        for (T m : movies) if (m.getRating() > min) result.add(m);
        return result;
    }

    public List<T> getAll() {
        return movies;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MovieManager<Movie> manager = new MovieManager<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        while (true) {
            System.out.println("\n1. Thêm phim");
            System.out.println("2. Xóa phim");
            System.out.println("3. Sửa phim");
            System.out.println("4. Hiển thị phim");
            System.out.println("5. Tìm phim theo tên");
            System.out.println("6. Lọc phim theo rating");
            System.out.println("7. Thoát");
            System.out.print("Chọn: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                continue;
            }

            switch (choice) {
                case 1:
                    try {
                        System.out.print("ID: ");
                        String id = sc.nextLine();
                        System.out.print("Title: ");
                        String title = sc.nextLine();
                        System.out.print("Director: ");
                        String director = sc.nextLine();
                        System.out.print("Release date (dd-MM-yyyy): ");
                        LocalDate date = LocalDate.parse(sc.nextLine(), fmt);
                        System.out.print("Rating: ");
                        double rating = Double.parseDouble(sc.nextLine());
                        manager.add(new Movie(id, title, director, date, rating));
                    } catch (Exception e) {
                        System.out.println("Dữ liệu không hợp lệ");
                    }
                    break;

                case 2:
                    System.out.print("ID cần xóa: ");
                    if (!manager.deleteById(sc.nextLine())) System.out.println("Không tìm thấy");
                    break;

                case 3:
                    System.out.print("ID cần sửa: ");
                    Movie m = manager.findById(sc.nextLine());
                    if (m == null) {
                        System.out.println("Không tìm thấy");
                        break;
                    }
                    try {
                        System.out.print("Title mới: ");
                        m.setTitle(sc.nextLine());
                        System.out.print("Director mới: ");
                        m.setDirector(sc.nextLine());
                        System.out.print("Date mới: ");
                        m.setReleaseDate(LocalDate.parse(sc.nextLine(), fmt));
                        System.out.print("Rating mới: ");
                        m.setRating(Double.parseDouble(sc.nextLine()));
                    } catch (Exception e) {
                        System.out.println("Sai dữ liệu");
                    }
                    break;

                case 4:
                    manager.getAll().forEach(System.out::println);
                    break;

                case 5:
                    System.out.print("Nhập tên: ");
                    List<Movie> found = manager.findByTitle(sc.nextLine());
                    if (found.isEmpty()) System.out.println("Không tìm thấy phim");
                    else found.forEach(System.out::println);
                    break;

                case 6:
                    manager.filterRating(8.0).forEach(System.out::println);
                    break;

                case 7:
                    return;
            }
        }
    }
}
