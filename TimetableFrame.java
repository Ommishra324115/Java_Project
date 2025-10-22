import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;

public class TimetableFrame extends JFrame {
    public TimetableFrame(String roll) {
        setTitle("Timetable");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        String[] cols = {"Day", "Start Time", "End Time", "Subject"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        add(new JScrollPane(table));

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps1 = con.prepareStatement(
                    "SELECT branch, semester FROM students WHERE roll_no=?"
            );
            ps1.setString(1, roll);
            ResultSet rs1 = ps1.executeQuery();

            if (rs1.next()) {
                String branch = rs1.getString("branch");
                int semester = rs1.getInt("semester");
                PreparedStatement ps2 = con.prepareStatement(
                        "SELECT day_of_week, start_time, end_time, subject " +
                                "FROM timetable WHERE branch=? AND semester=? " +
                                "ORDER BY FIELD(day_of_week, 'Monday','Tuesday','Wednesday','Thursday','Friday','Saturday')"
                );
                ps2.setString(1, branch);
                ps2.setInt(2, semester);
                ResultSet rs2 = ps2.executeQuery();

                while (rs2.next()) {
                    model.addRow(new Object[]{
                            rs2.getString("day_of_week"),
                            rs2.getString("start_time"),
                            rs2.getString("end_time"),
                            rs2.getString("subject")
                    });
                }

                rs2.close();
                ps2.close();

            } else {
                JOptionPane.showMessageDialog(this, "No student found with Roll No: " + roll);
            }

            rs1.close();
            ps1.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading timetable: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TimetableFrame("STU001").setVisible(true));
    }
}
