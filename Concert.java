public class Concert {
    private String name;
    private String date;
    private String location;
    private double price;

    public Concert(String name, String date, String location, double price) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDetails() {
        return name + " | Tanggal: " + date + " | Lokasi: " + location + " | Harga: Rp " + price;
    }
}
