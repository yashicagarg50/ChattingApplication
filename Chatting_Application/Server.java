import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.*;

import javax.swing.border.*;
import java.util.*;
import java.text.*;
import java.util.Calendar;

public class Server implements ActionListener{

    JButton send;
    JTextField text;
    JPanel a1;
    static DataOutputStream dout;

    // this is made so that vertically rightly alligned messages we could get
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();

    Server() {
        f.setSize(450, 700);
        f.setLocation(200, 50);
        f.getContentPane().setBackground(Color.WHITE);
        f.setLayout(null);

        // To divide like if we want a header or something
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(178, 75, 243));
        p1.setBounds(0, 0, 450, 70);
        // Set the layout of panel as Null
        p1.setLayout(null);
        f.add(p1);

        // adding images
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icons\\arrow.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);

        back.setBounds(7, 20, 25, 25);
        // Adding this imageicon on the panel
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                f.setVisible(false);
            }
                
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("Icons\\serverpfp.png"));
        Image i5 = i4.getImage().getScaledInstance(60, 80, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);

        profile.setBounds(40, 0, 60, 80);
        // Adding this imageicon on the panel
        p1.add(profile);


        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("Icons\\video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);

        video.setBounds(300, 20, 25, 25);
        // Adding this imageicon on the panel
        p1.add(video);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("Icons\\phone.png"));
        Image i11 = i10.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);

        phone.setBounds(350, 20, 25, 25);
        // Adding this imageicon on the panel
        p1.add(phone);

        ImageIcon i14 = new ImageIcon(ClassLoader.getSystemResource("Icons\\more.png"));
        Image i15 = i14.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i16 = new ImageIcon(i15);
        JLabel more = new JLabel(i16);

        more.setBounds(400, 20, 10, 25);
        // Adding this imageicon on the panel
        p1.add(more);

        // write something on frame or panel

        JLabel name = new JLabel("Sasuke");
        name.setBounds(110, 20, 100, 18);
        // name.setForeground(Color.WHITE);
        name.setFont(new Font("San Serif", Font.BOLD, 16));
        p1.add(name);

        JLabel status = new JLabel("Active now");
        status.setBounds(110, 40, 100, 18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("San Serif", Font.BOLD, 10));
        p1.add(status);

        // new panel neeche k lie
        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 570);
        f.add(a1);

        // for writing msgs
        text = new JTextField();
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("San Serif", Font.PLAIN, 16));
        f.add(text);

        send = new JButton("Send");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(new Color(178, 75, 243));
        send.addActionListener(this);
        send.setFont(new Font("San Serif", Font.BOLD, 16));
        send.setForeground(Color.WHITE);
        f.add(send);


        f.setUndecorated(true);
        f.setVisible(true);
        

    }

    public void actionPerformed(ActionEvent ae) {
        try {
        // get text returns a string
        String out = text.getText();
        JPanel p2 = formatLabel(out);
        // borderlayout places the elements on border
        a1.setLayout(new BorderLayout());
        
        JPanel right = new JPanel(new BorderLayout());
        // This doesnt accepts string so we have to make a label so that we can add a panel
        right.add(p2, BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));

        a1.add(vertical,  BorderLayout.PAGE_START);

        dout.writeUTF(out);

        text.setText("");


        // we have to repaint and reload so that the msg can be shown on the frame but as the frame is already shown thus we have to repaint it
        f.repaint();
        f.invalidate();
        f.validate();
    } catch (Exception e) {
        e.printStackTrace();
    }

    }

    // EXPLAIN -----------------------------
    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tohoma", Font.PLAIN, 16));
        output.setBackground(new Color(215, 161, 249));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);

        return panel;
    }
    public static void main(String[] args) {
        
        new Server();

        try {
            ServerSocket skt = new ServerSocket(6001);
            while(true) {
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                while(true) {
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();

                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}