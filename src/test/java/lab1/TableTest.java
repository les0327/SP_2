package lab1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TableTest {

    private static Table<Long, Float> table = new Table<>();

    @Before
    public void setUpTable() {
        table.add((long) 6, "eeFglFm", 9.103f);
        table.add((long) 3, "ckefg", 5.7f);
        table.add((long) 1, "abCde", 2.0f);
        table.add((long) 2, "bcdeF", 3.3f);
        table.add((long) 4, "ageEfzFF", 6.66f);
    }

    @After
    public void tearDownTable() {
        table.clear();
    }

    @Test
    public void add() throws Exception {
        System.out.printf("Table before add:%n%s%n", table.toString());
        table.add((long) 7, "FFOLk", 11.9f);
        System.out.printf("Table after add:%n%s%n", table.toString());
    }

    @Test
    public void insert() throws Exception {
        System.out.printf("Table before insert:%n%s%n", table.toString());
        table.insert(0,(long) 7, "FFOLk", 11.9f);
        System.out.printf("Table after insert:%n%s%n", table.toString());
    }

    @Test
    public void get() throws Exception {
        System.out.println(table);
        System.out.printf("get(%d) : %s%n", 0, table.get(0).toString());
        System.out.printf("get(%d) : %s%n", 3, table.get(3).toString());
    }

    @Test
    public void select() throws Exception {
        System.out.println(table);
        System.out.printf("Select(%d)(linear) : %s%n", 2,     table.select((long) 2));
        System.out.printf("Select(%d)(linear) : %s%n", Long.MAX_VALUE, table.select(Long.MAX_VALUE));

        table.sort((row1, row2) -> row1.getDirectAddressKey().compareTo(row2.getDirectAddressKey()));
        System.out.printf("Sorted %s%n", table.toString());
        System.out.printf("Select(%d)(binary) : %s%n", 2,     table.select((long) 2));
        System.out.printf("Select(%d)(binary) : %s%n", Long.MAX_VALUE, table.select(Long.MAX_VALUE));
    }

    @Test
    public void update() throws Exception {
        System.out.printf("Before update : %n%s%n", table);
        table.update((long) 6, "UPDATED", Float.MAX_VALUE);
        System.out.printf("After update : %n%s%n", table);
    }

    @Test
    public void delete() throws Exception {
        System.out.printf("Before delete : %n%s%n", table);
        table.delete((long) 6);
        table.delete((long) 2);
        System.out.printf("After delete : %n%s%n", table);
    }

    @Test
    public void foreignAddressKeySearch() throws Exception {
        System.out.println(table);
        String key = "eefz";
        System.out.printf("Search by foreign key(\"%s\") : %s%n", key, table.foreignAddressKeySearch(key));
        key = "NoSuchKeyInTable";
        System.out.printf("Search by foreign key(\"%s\") : %s%n", key, table.foreignAddressKeySearch(key));
        key = "abc";
        System.out.printf("Search by foreign key(\"%s\") : %s%n", key, table.foreignAddressKeySearch(key));
    }

}