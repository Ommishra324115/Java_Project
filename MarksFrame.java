import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;

public class MarksFrame extends JFrame {
    public MarksFrame(String roll) {
        setTitle("Marks Sheet");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        String[] cols = {"Subject", "Marks Obtained", "Max Marks"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        add(new JScrollPane(table));

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT subject_name, marks_obtained, max_marks FROM marks WHERE roll_no=?"
            );
            ps.setString(1, roll);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("subject_name"),
                        rs.getInt("marks_obtained"),
                        rs.getInt("max_marks")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading marks: " + e.getMessage());
        }
    }
}
