import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginFrame extends JFrame {
    JTextField rollField;
    JPasswordField passField;
    JButton loginBtn;

    public LoginFrame() {
        setTitle("Student Login ");
        setSize(500,300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        rollField = new JTextField(15);
        passField = new JPasswordField(15);
        loginBtn = new JButton("Login");

        JPanel panel = new JPanel(new GridLayout(3,2));
        panel.add(new JLabel("Roll No:"));
        panel.add(rollField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(new JLabel(""));
        panel.add(loginBtn);

        add(panel);

        loginBtn.addActionListener(e -> checkLogin());
    }

    void checkLogin() {
        String roll = rollField.getText();
        String pass = new String(passField.getPassword());

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users  WHERE roll_no=? AND password=?"
            );
            ps.setString(1, roll);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                dispose();
                new StudentDashboard(roll).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LoginFrame().setVisible(true);
    }
}
