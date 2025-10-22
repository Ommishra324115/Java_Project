import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;

public class ActivitiesFrame extends JFrame {

    public ActivitiesFrame(String roll) {
        setTitle("Activities for Your Branch");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        String[] cols = {"Title", "Description", "Start Date", "End Date", "Venue"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        add(new JScrollPane(table));

        try {
            Connection con = DBConnection.getConnection();

            // Step 1: Get student's branch using roll_no
            PreparedStatement ps1 = con.prepareStatement(
                    "SELECT branch FROM students WHERE roll_no = ?"
            );
            ps1.setString(1, roll);
            ResultSet rs1 = ps1.executeQuery();

            if (rs1.next()) {
                String branch = rs1.getString("branch");

                // Step 2: Fetch activities for that branch
                PreparedStatement ps2 = con.prepareStatement(
                        "SELECT title, description, start_date, end_date, venue " +
                                "FROM activities WHERE branch = ? ORDER BY start_date ASC"
                );
                ps2.setString(1, branch);
                ResultSet rs2 = ps2.executeQuery();

                while (rs2.next()) {
                    model.addRow(new Object[]{
                            rs2.getString("title"),
                            rs2.getString("description"),
                            rs2.getDate("start_date"),
                            rs2.getDate("end_date"),
                            rs2.getString("venue")
                    });
                }

                rs2.close();
                ps2.close();
            } else {
                JOptionPane.showMessageDialog(this, "No student found for Roll No: " + roll);
            }

            rs1.close();
            ps1.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading activities: " + e.getMessage());
        }
    }

    // For testing purpose
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ActivitiesFrame("STU001").setVisible(true));
    }
}
