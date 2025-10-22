import javax.swing.*;
import java.sql.*;

public class FeesFrame extends JFrame {
    public FeesFrame(String roll) {
        setTitle("Fees Info");
        setSize(300,200);
        setLocationRelativeTo(null);
        JLabel label = new JLabel();
        add(label);

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT paid_amount,total_amount FROM fees WHERE roll_no=?");
            ps.setString(1, roll);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                double paid = rs.getDouble("paid_amount");
                double total = rs.getDouble("total_amount");
                label.setText("Paid: ₹" + paid + "    Total: ₹" + total);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
