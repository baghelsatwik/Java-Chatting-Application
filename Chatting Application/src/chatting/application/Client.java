package chatting.application;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.border.EmptyBorder;
import java.net.*;
import java.io.*;

public class Client implements ActionListener{
    
    JButton send;
    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataInputStream din;
    static DataOutputStream dout;
    
    Client(){
        f.setLayout(null);
        
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);
        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent me){
                System.exit(0);
            }
        });
        
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 50, 50);
        p1.add(profile);
        
        JLabel name = new JLabel("Bunty");
        name.setBounds(110,20,200,20);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Tahmoia",Font.BOLD,18));
        p1.add(name);
        
        JLabel status = new JLabel("Active Now");
        status.setBounds(110,45,200,15);
        status.setForeground(Color.GREEN);
        p1.add(status);
        
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel videoCall = new JLabel(i9);
        videoCall.setBounds(300, 20, 30, 30);
        p1.add(videoCall);
        
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phoneCall = new JLabel(i12);
        phoneCall.setBounds(360, 20, 30, 30);
        p1.add(phoneCall);
        
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel dot3 = new JLabel(i15);
        dot3.setBounds(410, 20, 10, 25);
        p1.add(dot3);
        
        a1 = new JPanel();
        a1.setBounds(5,75,440,500);
        f.add(a1);
        
        text = new JTextField();
        text.setBounds(5,580,310,40);
        text.setFont(new Font("SAN SERIF",Font.PLAIN,16));
        f.add(text);
        
        send = new JButton("Send");
        send.setBounds(320,580,125,40);
        send.setForeground(Color.WHITE);
        send.setFont(new Font("Dialog",Font.BOLD,16));
        send.setBackground(new Color(7, 94, 84));
        send.addActionListener(this);
        f.add(send);
        
        f.setSize(450,625);
        f.setUndecorated(true);
        f.setLocation(800, 50);
        f.getContentPane().setBackground(Color.WHITE);

        f.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        try{
            String out = text.getText();
       
        JPanel p2 = formatLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        
        a1.setLayout(new BorderLayout());
        JPanel right = new JPanel(new BorderLayout());
        right.add(p2,BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        
        a1.add(vertical, BorderLayout.PAGE_START);
        
        dout.writeUTF(out);
        
        text.setText("");
        
        f.repaint();
        f.invalidate();
        f.validate();    
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel(out);
        output.setFont(new Font("Tahomia",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel timo = new JLabel();
        timo.setText(sdf.format(cal.getTime()));
        panel.add(timo);
        return panel;
    }
    
    public static void main(String[] args){
        new Client();
        
        try{
                Socket s = new Socket("127.0.0.1",6001);
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                while(true){
                    a1.setLayout(new BorderLayout());
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);
                    
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    
                    vertical.add(Box.createVerticalStrut(15));
                    a1.add(vertical, BorderLayout.PAGE_START);
                    f.validate();
                } 
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
