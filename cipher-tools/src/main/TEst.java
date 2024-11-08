import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Wed Nov 06 14:22:36 ICT 2024
 */

/**
 * @author Nguyen Tuan
 */
public class TEst extends JPanel {
    public TEst() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nguyễn Tuấn
        tabbedPane1 = new JTabbedPane();
        BasicCipherContainer = new JPanel();
        Split = new JSplitPane();
        EncodeSection = new JPanel();
        header = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        hSpacer1 = new JPanel(null);
        encode = new JButton();
        scrollBarContainer = new JPanel();
        scrollbar = new JScrollPane();
        textArea = new JTextArea();
        DecodeSection = new JPanel();
        header2 = new JPanel();
        label3 = new JLabel();
        label4 = new JLabel();
        hSpacer2 = new JPanel(null);
        encode2 = new JButton();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        SymmetricCipherContainer = new JPanel();
        panel1 = new JPanel();
        label5 = new JLabel();
        comboBox1 = new JComboBox();
        panel2 = new JPanel();
        label6 = new JLabel();
        textField1 = new JTextField();
        button1 = new JButton();
        panel3 = new JPanel();
        panel4 = new JPanel();

        //======== this ========
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.border.
        EmptyBorder(0,0,0,0), "JFor\u006dDesi\u0067ner \u0045valu\u0061tion",javax.swing.border.TitledBorder.CENTER,javax.swing
        .border.TitledBorder.BOTTOM,new java.awt.Font("Dia\u006cog",java.awt.Font.BOLD,12),
        java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {@Override public void propertyChange(java.beans.PropertyChangeEvent e){if("bord\u0065r".equals(e.getPropertyName()))
        throw new RuntimeException();}});
        setLayout(new BorderLayout());

        //======== tabbedPane1 ========
        {

            //======== BasicCipherContainer ========
            {
                BasicCipherContainer.setLayout(new BoxLayout(BasicCipherContainer, BoxLayout.Y_AXIS));

                //======== Split ========
                {
                    Split.setOrientation(JSplitPane.VERTICAL_SPLIT);
                    Split.setPreferredSize(new Dimension(700, 407));
                    Split.setBorder(null);

                    //======== EncodeSection ========
                    {
                        EncodeSection.setPreferredSize(new Dimension(400, 200));
                        EncodeSection.setLayout(new BorderLayout());

                        //======== header ========
                        {
                            header.setBorder(new CompoundBorder(
                                new CompoundBorder(
                                    new EmptyBorder(5, 0, 5, 0),
                                    new MatteBorder(1, 0, 1, 0, Color.black)),
                                new EmptyBorder(5, 5, 5, 5)));
                            header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));

                            //---- label1 ----
                            label1.setText("M\u00e3 h\u00f3a d\u1ecbch");
                            label1.setFont(new Font("Sitka Text", Font.PLAIN, 10));
                            label1.setVerticalAlignment(SwingConstants.TOP);
                            header.add(label1);

                            //---- label2 ----
                            label2.setText("69.8 KB");
                            header.add(label2);
                            header.add(hSpacer1);

                            //---- encode ----
                            encode.setText("encrypt \ud83d\udd10");
                            header.add(encode);
                        }
                        EncodeSection.add(header, BorderLayout.NORTH);

                        //======== scrollBarContainer ========
                        {
                            scrollBarContainer.setLayout(new BorderLayout());

                            //======== scrollbar ========
                            {
                                scrollbar.setPreferredSize(new Dimension(24, 80));
                                scrollbar.setViewportView(textArea);
                            }
                            scrollBarContainer.add(scrollbar, BorderLayout.CENTER);
                        }
                        EncodeSection.add(scrollBarContainer, BorderLayout.CENTER);
                    }
                    Split.setTopComponent(EncodeSection);

                    //======== DecodeSection ========
                    {
                        DecodeSection.setPreferredSize(new Dimension(400, 200));
                        DecodeSection.setLayout(new BorderLayout());

                        //======== header2 ========
                        {
                            header2.setBorder(new EmptyBorder(5, 5, 5, 5));
                            header2.setLayout(new BoxLayout(header2, BoxLayout.X_AXIS));

                            //---- label3 ----
                            label3.setText("Cipher text: ");
                            header2.add(label3);

                            //---- label4 ----
                            label4.setText("69.8 KB");
                            header2.add(label4);

                            //---- hSpacer2 ----
                            hSpacer2.setBorder(new BevelBorder(BevelBorder.LOWERED));
                            header2.add(hSpacer2);

                            //---- encode2 ----
                            encode2.setText("decrypt \ud83d\udd13");
                            header2.add(encode2);
                        }
                        DecodeSection.add(header2, BorderLayout.NORTH);

                        //======== scrollPane1 ========
                        {
                            scrollPane1.setViewportView(table1);
                        }
                        DecodeSection.add(scrollPane1, BorderLayout.CENTER);
                    }
                    Split.setBottomComponent(DecodeSection);
                }
                BasicCipherContainer.add(Split);
            }
            tabbedPane1.addTab("M\u00e3 ho\u00e1 c\u01a1 b\u1ea3n", BasicCipherContainer);

            //======== SymmetricCipherContainer ========
            {
                SymmetricCipherContainer.setLayout(new GridBagLayout());
                ((GridBagLayout)SymmetricCipherContainer.getLayout()).columnWidths = new int[] {0, 0};
                ((GridBagLayout)SymmetricCipherContainer.getLayout()).rowHeights = new int[] {0, 0, 0};
                ((GridBagLayout)SymmetricCipherContainer.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)SymmetricCipherContainer.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

                //======== panel1 ========
                {
                    panel1.setLayout(new GridBagLayout());
                    ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {141, 285, 0};
                    ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0};
                    ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                    ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                    //---- label5 ----
                    label5.setText("Input type:");
                    panel1.add(label5, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
                    panel1.add(comboBox1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
                }
                SymmetricCipherContainer.add(panel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                    new Insets(0, 0, 0, 0), 0, 0));

                //======== panel2 ========
                {
                    panel2.setLayout(new GridBagLayout());
                    ((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {141, 213, 61, 0};
                    ((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
                    ((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
                    ((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                    //---- label6 ----
                    label6.setText("File:");
                    panel2.add(label6, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));
                    panel2.add(textField1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));

                    //---- button1 ----
                    button1.setText("browse");
                    panel2.add(button1, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
                }
                SymmetricCipherContainer.add(panel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            tabbedPane1.addTab("M\u00e3 ho\u00e1 \u0111\u1ed1i x\u1ee9ng", SymmetricCipherContainer);

            //======== panel3 ========
            {
                panel3.setLayout(new GridLayout());
            }
            tabbedPane1.addTab("M\u00e3 ho\u00e1 b\u1ea5t \u0111\u1ed1i x\u1ee9ng", panel3);

            //======== panel4 ========
            {
                panel4.setLayout(new GridLayout());
            }
            tabbedPane1.addTab("Ch\u1eef k\u00fd \u0111i\u1ec7n t\u1eed", panel4);
        }
        add(tabbedPane1, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nguyễn Tuấn
    private JTabbedPane tabbedPane1;
    private JPanel BasicCipherContainer;
    private JSplitPane Split;
    private JPanel EncodeSection;
    private JPanel header;
    private JLabel label1;
    private JLabel label2;
    private JPanel hSpacer1;
    private JButton encode;
    private JPanel scrollBarContainer;
    private JScrollPane scrollbar;
    private JTextArea textArea;
    private JPanel DecodeSection;
    private JPanel header2;
    private JLabel label3;
    private JLabel label4;
    private JPanel hSpacer2;
    private JButton encode2;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JPanel SymmetricCipherContainer;
    private JPanel panel1;
    private JLabel label5;
    private JComboBox comboBox1;
    private JPanel panel2;
    private JLabel label6;
    private JTextField textField1;
    private JButton button1;
    private JPanel panel3;
    private JPanel panel4;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
