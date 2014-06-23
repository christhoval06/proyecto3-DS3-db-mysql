import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: cristobal
 * Date: 06/20/14
 * Time: 04:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueryTableModel extends AbstractTableModel {

    Vector cache; // will hold String[] objects . . .
    int colCount;
    String[] headers;
    DB db;

    public QueryTableModel(DB d) {
        cache = new Vector();
        db = d;
    }

    public String getColumnName(int i) {
        return headers[i];
    }

    public int getColumnCount() {
        return colCount;
    }

    public int getRowCount() {
        return cache.size();
    }

    public Object getValueAt(int row, int col) {
        return ((String[]) cache.elementAt(row))[col];
    }

    // All the real work happens here; in a real application,
    // we'd probably perform the query in a separate thread.
    public void setQuery(String q) {
        cache = new Vector();
        try {
            // Execute the query and store the result set and its metadata
            ResultSet rs = db.select(q);
            ResultSetMetaData meta = rs.getMetaData();
            colCount = meta.getColumnCount();

            // Now we must rebuild the headers array with the new column names
            headers = new String[colCount];
            for (int h = 1; h <= colCount; h++) {
                headers[h - 1] = meta.getColumnName(h);
            }

            // and file the cache with the records from our query. This would
            // not be
            // practical if we were expecting a few million records in response
            // to our
            // query, but we aren't, so we can do this.
            while (rs.next()) {
                String[] record = new String[colCount];
                for (int i = 0; i < colCount; i++) {
                    record[i] = rs.getString(i + 1);
                }
                cache.addElement(record);
            }
            fireTableChanged(null); // notify everyone that we have a new table.
        } catch (Exception e) {
            cache = new Vector(); // blank it out and keep going.
            e.printStackTrace();
        }
    }
}
