package lab1;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class Table<DK extends Comparable, FF> {

    @Getter private ArrayList<TableRow> rows;
    @Getter private boolean isSorted;

    public Table () {
        this.rows     = new ArrayList<>();
        this.isSorted = true;
    }

    /**
     *  Add new row to the end
     * @param directAddressKey  -
     * @param foreignAddressKey -
     * @param functionalField   -
     */
    public void add(DK directAddressKey, String foreignAddressKey, FF functionalField) {
        this.rows.add(new TableRow(directAddressKey, foreignAddressKey, functionalField));
        this.isSorted = false;
    }

    /**
     * Add new row to the end
     * @param row - row to add
     */
    public void add(TableRow row) {
        this.rows.add(row);
        this.isSorted = false;
    }

    /**
     * Insert new row in index position
     * @param index             -
     * @param directAddressKey  -
     * @param foreignAddressKey -
     * @param functionalField   -
     */
    public void insert(int index, DK directAddressKey, String foreignAddressKey, FF functionalField) {
        this.rows.add(index, new TableRow(directAddressKey, foreignAddressKey, functionalField));
        this.isSorted = false;
    }

    /**
     * Insert new row in index position
     * @param index -
     * @param row   -
     */
    public void insert(int index, TableRow row) {
        this.rows.add(index, row);
        this.isSorted = false;
    }

    /**
     * Select row with i index from table
     * @param i - index
     * @return row with i index or null if index > table size
     */
    public TableRow get(int i) {
        if (i < rows.size())
            return rows.get(i);
        else
            return null;
    }

    /**
     * Select row with i index from table
     * @param directKey - direct address key
     * @return row with i index or null if table is empty
     */
    public TableRow select(DK directKey) {
        if (!rows.isEmpty())
            if (isSorted)
                return binarySearch(directKey);
            else
                return linearSearch(directKey);
        else
            return null;
    }

    /**
     * Update table row if table contain it
     * @param directAddressKey  -
     * @param foreignAddressKey -
     * @param functionalField   -
     */
    public void update(DK directAddressKey, String foreignAddressKey, FF functionalField) {
        TableRow oldRow = this.select(directAddressKey);
        if (oldRow != null) {
            oldRow.setForeignAddressKey(foreignAddressKey);
            oldRow.setFunctionalField(functionalField);
        }
        this.isSorted = false;
    }

    /**
     * Delete row with directAddressKey
     * @param directAddressKey -
     */
    public void delete(DK directAddressKey) {
        rows.remove(this.select(directAddressKey));
    }

    /**
     * Search for a string that has the maximum number of characters in sequence equals ignore case with key
     * @param foreignAddressKey -
     * @return selected TableRow
     */
    public TableRow foreignAddressKeySearch(String foreignAddressKey) {
        String key = foreignAddressKey.toLowerCase();

        Iterator<TableRow> itr = rows.iterator();
        TableRow maxRow        = null;
        int max                = -1;
        do {
            TableRow row = itr.next();

            String nextKey = row.getForeignAddressKey().toLowerCase();

            int bufMax = -1;
            for (int i = 0; i < key.length(); i++) {
                for (int j = i + 1; j <= key.length(); j++) {
                    if (nextKey.contains(key.substring(i, j)) && bufMax < (j - i))
                        bufMax = j - i;
                }
            }

            if (bufMax > max) {
                max    = bufMax;
                maxRow = row;
            }

        } while (itr.hasNext());

        return (max > 0) ? maxRow : null;
    }


    public class TableRow {

        @Getter @Setter private DK directAddressKey;
        @Getter @Setter private String foreignAddressKey;
        @Getter @Setter private FF functionalField;

        public TableRow(DK directAddressKey, String foreignAddressKey, FF functionalField) {
            this.directAddressKey  = directAddressKey;
            this.foreignAddressKey = foreignAddressKey;
            this.functionalField   = functionalField;
        }

        @Override
        public java.lang.String toString() {
            return "TableRow{" +
                    "directAddressKey=" + directAddressKey +
                    ", foreignAddressKey=" + foreignAddressKey +
                    ", functionalField=" + functionalField +
                    '}';
        }
    }

    /**
     *  Linear search
     * @param directKey - direct address key
     * @return first TableRow with direct address key == directKey or null if nothing found
     */
    private TableRow linearSearch(DK directKey) {
        Iterator<TableRow> itr = rows.iterator();

        do {
            TableRow buf   = itr.next();
            if (buf.getDirectAddressKey().equals(directKey))
                return buf;
        } while (itr.hasNext());

        return null;
    }

    /**
     * Binary search
     * @param directKey - direct address key
     * @return TableRow with direct address key == directKey or null if nothing found
     */
    private TableRow binarySearch(DK directKey) {

        int left  = 0;
        int right    = rows.size() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            DK buf = rows.get(mid).getDirectAddressKey();
            if (buf.compareTo(directKey) > 0 )
                right = mid - 1;
            else if (buf.compareTo(directKey) < 0)
                left = mid + 1;
            else
                return rows.get(mid);
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer("Table{\n");
        rows.forEach(row -> buf.append('\t').append(row.toString()).append('\n'));
        return  buf.append("};").toString();
    }

    public void sort(Comparator<TableRow> comparator) {
        rows.sort(comparator);
        isSorted = true;
    }

    /**
     * Clear table
     */
    public void clear() {
        rows.clear();
    }

    /**
     * @return table's size
     */
    public long getSize() {
        return rows.size();
    }
}
