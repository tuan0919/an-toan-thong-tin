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
        scrollBarContainer2 = new JPanel();
        scrollbar2 = new JScrollPane();
        textArea2 = new JTextArea();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();

        //======== this ========
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing.
        border. EmptyBorder( 0, 0, 0, 0) , "JFor\u006dDesi\u0067ner \u0045valu\u0061tion", javax. swing. border. TitledBorder. CENTER
        , javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog" ,java .awt .Font
        .BOLD ,12 ), java. awt. Color. red) , getBorder( )) );  addPropertyChangeListener (
        new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("bord\u0065r"
        .equals (e .getPropertyName () )) throw new RuntimeException( ); }} );
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
                            label1.setText("Plain text: ");
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
                            header2.add(hSpacer2);

                            //---- encode2 ----
                            encode2.setText("decrypt \ud83d\udd13");
                            header2.add(encode2);
                        }
                        DecodeSection.add(header2, BorderLayout.NORTH);

                        //======== scrollBarContainer2 ========
                        {
                            scrollBarContainer2.setLayout(new BorderLayout());

                            //======== scrollbar2 ========
                            {
                                scrollbar2.setViewportView(textArea2);
                            }
                            scrollBarContainer2.add(scrollbar2, BorderLayout.CENTER);
                        }
                        DecodeSection.add(scrollBarContainer2, BorderLayout.CENTER);
                    }
                    Split.setBottomComponent(DecodeSection);
                }
                BasicCipherContainer.add(Split);
            }
            tabbedPane1.addTab("M\u00e3 ho\u00e1 c\u01a1 b\u1ea3n", BasicCipherContainer);

            //======== panel2 ========
            {
                panel2.setLayout(new GridLayout());
            }
            tabbedPane1.addTab("M\u00e3 ho\u00e1 \u0111\u1ed1i x\u1ee9ng", panel2);

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
    private JPanel scrollBarContainer2;
    private JScrollPane scrollbar2;
    private JTextArea textArea2;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
