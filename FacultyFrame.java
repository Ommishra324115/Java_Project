import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;

public class FacultyFrame extends JFrame {
    public FacultyFrame(String roll) {
        setTitle("Faculty Information");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        String[] cols = {"Faculty Name", "Department", "Email", "Phone"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        add(new JScrollPane(table));

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps1 = con.prepareStatement(
                    "SELECT branch FROM students WHERE roll_no=?"
            );
            ps1.setString(1, roll);
            ResultSet rs1 = ps1.executeQuery();

            if (rs1.next()) {
                String branch = rs1.getString("branch");

                PreparedStatement ps2 = con.prepareStatement(
                        "SELECT DISTINCT name, department, email, phone FROM faculty WHERE department=?"
                );

                ps2.setString(1, branch);
                ResultSet rs2 = ps2.executeQuery();

                while (rs2.next()) {
                    model.addRow(new Object[]{
                            rs2.getString("name"),
                            rs2.getString("department"),
                            rs2.getString("email"),
                            rs2.getString("phone")
                    });
                }

                rs2.close();
                ps2.close();
            } else {
                JOptionPane.showMessageDialog(this, "Student not found for Roll No: " + roll);
            }

            rs1.close();
            ps1.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading faculty data: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FacultyFrame("STU002").setVisible(true));
    }
}
