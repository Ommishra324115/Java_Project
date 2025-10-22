import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentDashboard extends JFrame {
    String roll;

    public StudentDashboard(String roll) {
        this.roll = roll;
        setTitle("Student Dashboard - " + roll);
        setSize(400,300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton marksBtn = new JButton("View Marks");
        JButton feesBtn = new JButton("View Fees");
        JButton ttBtn = new JButton("View Time Table");
        JButton facBtn = new JButton("Faculty Info");
        JButton actBtn = new JButton("Activities Info");

        JPanel panel = new JPanel(new GridLayout(5,1,10,10));
        panel.add(marksBtn);
        panel.add(feesBtn);
        panel.add(ttBtn);
        panel.add(facBtn);
        panel.add(actBtn);
        add(panel);

        marksBtn.addActionListener(e -> new MarksFrame(roll).setVisible(true));
        feesBtn.addActionListener(e -> new FeesFrame(roll).setVisible(true));
        ttBtn.addActionListener(e -> new TimetableFrame(roll).setVisible(true));
        facBtn.addActionListener(e -> new FacultyFrame(roll).setVisible(true));
        actBtn.addActionListener(e -> new ActivitiesFrame(roll).setVisible(true));
    }
}
