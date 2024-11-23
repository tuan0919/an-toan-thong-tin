import Controller.*;
import View.*;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame {
    private JButton TraditionalTab_Button,
            SymmetricTab_Button,
            AsymmetricTab_Button,
            SignatureTab_Button,
            HashTab_Button;
    private JPanel MainPanel;
    private JPanel ContentPanel;
    private TraditionalScreen_View TraditionalScreenView = new TraditionalScreen_View();
    {
        new TraditionalScreen_Controller(TraditionalScreenView);
    }
    private SymmetricScreen_View SymmetricScreenView = new SymmetricScreen_View();
    {
        new SymmetricScreen_Controller(SymmetricScreenView);
    }
    private AsymmetricScreen_View AsymmetricScreenView = new AsymmetricScreen_View();
    {
        new AsymmetricScreen_Controller(AsymmetricScreenView);
    }
    private HashScreen_View HashScreenView = new HashScreen_View();
    {
        new HashScreen_Controller(HashScreenView);
    }
    private SignatureScreen_View SignatureScreenView = new SignatureScreen_View();
    {
        new SignatureScreen_Controller(SignatureScreenView);
    }

    public App(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1100,740);
        this.setTitle("Tool Mã Hóa");

        MainPanel = new JPanel();
        ContentPanel = new JPanel();
        TraditionalTab_Button = new JButton("Truyền thống");
        SymmetricTab_Button = new JButton("Đối xứng");
        AsymmetricTab_Button = new JButton("Bất đối xứng");
        SignatureTab_Button = new JButton("Ký điện tử");
        HashTab_Button = new JButton("Hash");
        JPanel listTab = new JPanel();
        listTab.setSize(new Dimension(300,50));

        listTab.setLayout(new FlowLayout(FlowLayout.LEFT));
        listTab.add(TraditionalTab_Button);
        listTab.add(SymmetricTab_Button);
        listTab.add(AsymmetricTab_Button);
        listTab.add(HashTab_Button);
        listTab.add(SignatureTab_Button);


        MainPanel.setLayout(new BorderLayout());
        MainPanel.add(listTab,BorderLayout.NORTH);

        MainPanel.add(ContentPanel,BorderLayout.CENTER);

        activeTab();
//        Tự động target tab Truyền thống khi mới khởi chạy
        TraditionalTab_Button.doClick();
        this.setContentPane(MainPanel);
        this.setVisible(true);
    }
    public void activeTab() {
        TraditionalTab_Button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                resetTabColors();
                TraditionalTab_Button.setBackground(Color.GREEN);
                ContentPanel.removeAll();
                ContentPanel.add(TraditionalScreenView);
                ContentPanel.revalidate();
                ContentPanel.repaint();
            }
        });

        SymmetricTab_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTabColors();
                SymmetricTab_Button.setBackground(Color.GREEN);
                ContentPanel.removeAll();
                ContentPanel.add(SymmetricScreenView);
                ContentPanel.revalidate();
                ContentPanel.repaint();
            }
        });

        AsymmetricTab_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTabColors();
                AsymmetricTab_Button.setBackground(Color.GREEN);
                ContentPanel.removeAll();
                ContentPanel.add(AsymmetricScreenView);
                ContentPanel.revalidate();
                ContentPanel.repaint();
            }
        });

        SignatureTab_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTabColors();
                SignatureTab_Button.setBackground(Color.GREEN);
            }
        });
        HashTab_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTabColors();
                HashTab_Button.setBackground(Color.GREEN);
                ContentPanel.removeAll();
                ContentPanel.add(HashScreenView);
                ContentPanel.revalidate();
                ContentPanel.repaint();
            }
        });
        SignatureTab_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTabColors();
                SignatureTab_Button.setBackground(Color.GREEN);
                ContentPanel.removeAll();
                ContentPanel.add(SignatureScreenView);
                ContentPanel.revalidate();
                ContentPanel.repaint();
            }
        });

    }

    private void resetTabColors() {
        TraditionalTab_Button.setBackground(null);
        SymmetricTab_Button.setBackground(null);
        AsymmetricTab_Button.setBackground(null);
        SignatureTab_Button.setBackground(null);
        HashTab_Button.setBackground(null);
    }

    public static void main(String[] args) {
        try {
            FlatLightLaf.setup();
            App mainPanel = new App();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
