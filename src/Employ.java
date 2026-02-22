public class Employ {

    public int id;
    public String name;
    public double basic;

    public Employ(int id, String name, double basic){
        this.id = id;
        this.name = name;
        this.basic = basic;
    }

    @Override
    public String toString() {
        return "Employee " + id + ". " + name + ", Salary = "+ basic;
    }

}
