/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package openseiho;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import openseiho.OsText;
import javax.swing.LayoutStyle;
import javax.swing.OverlayLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author hdm
 */
public class dbSheetFrame extends javax.swing.JFrame {
private javax.swing.JList[] lists;
private javax.swing.JSplitPane[] split;
private String tableNameWk;
private DefaultListModel ModelL[];
private final int maxCols = 26;
private openseiho.OsText[] editPre;
private openseiho.OsText[] edit;
private javax.swing.JLabel[] name;
private String[][] strRS;

private dbAccess dbAc;

    //共通部分
    public static boolean DebugMode = false;
    public static void logDebug(String str) {
        if (DebugMode) {
            System.out.println(str);
        }
    }
    
    /**
     * Creates new form dbSheetFrame
     */
    public dbSheetFrame(dbAccess dbA) {
        dbAc = dbA;
        tableNameWk = dbA.getTableName();
        logDebug("DB表示・編集開始:" + tableNameWk);
        lists = new javax.swing.JList[maxCols];
        ModelL = new DefaultListModel[maxCols];
        split = new javax.swing.JSplitPane[maxCols];
        editPre = new openseiho.OsText[maxCols];
        edit = new openseiho.OsText[maxCols];
        name = new javax.swing.JLabel[maxCols];
        
        initComponents();
        
        jTabbedPaneSheet.setSelectedIndex(0);
        
        //とりあえず放置
        lists[0] = jList1;
        lists[1] = jList2;
        lists[2] = jList3;
        lists[3] = jList4;
        lists[4] = jList5;
        lists[5] = jList6;
        lists[6] = jList7;
        lists[7] = jList8;
        lists[8] = jList9;
        lists[9] = jList10;
        lists[10] = jList11;
        lists[11] = jList12;
        lists[12] = jList13;
        lists[13] = jList14;
        lists[14] = jList15;
        lists[15] = jList16;
        lists[16] = jList17;
        lists[17] = jList18;
        lists[18] = jList19;
        lists[19] = jList20;
        lists[20] = jList21;
        lists[21] = jList22;
        lists[22] = jList23;
        lists[23] = jList24;
        lists[24] = jList25;
        lists[25] = jList26;
        
        split[0] = jSplitPaneCol1;
        split[1] = jSplitPaneCol2;
        split[2] = jSplitPaneCol3;
        split[3] = jSplitPaneCol4;
        split[4] = jSplitPaneCol5;
        split[5] = jSplitPaneCol6;
        split[6] = jSplitPaneCol7;
        split[7] = jSplitPaneCol8;
        split[8] = jSplitPaneCol9;
        split[9] = jSplitPaneCol10;
        split[10] = jSplitPaneCol11;
        split[11] = jSplitPaneCol12;
        split[12] = jSplitPaneCol13;
        split[13] = jSplitPaneCol14;
        split[14] = jSplitPaneCol15;
        split[15] = jSplitPaneCol16;
        split[16] = jSplitPaneCol17;
        split[17] = jSplitPaneCol18;
        split[18] = jSplitPaneCol19;
        split[19] = jSplitPaneCol20;
        split[20] = jSplitPaneCol21;
        split[21] = jSplitPaneCol22;
        split[22] = jSplitPaneCol23;
        split[23] = jSplitPaneCol24;
        split[24] = jSplitPaneCol25;
        split[25] = jSplitPaneCol26;
        
        for (int i = 0; i < maxCols; i++) {
            //リストボックス初期化
            ModelL[i] = new DefaultListModel();
            lists[i].setModel(ModelL[i]);
        }
        jScrollPaneSheet.setPreferredSize(new Dimension(90 * maxCols, 200));
        
        //編集画面を生成(コンストラクタで１回のみ実行)
        OsTextTableName.setText(dbA.getTableName());
        OsTextWhere.setText("");
        for (int i = 0; i < maxCols; i++) {
            editPre[i] = new OsText("");
            edit[i] = new OsText("");
            name[i] = new javax.swing.JLabel("");
            jPanelEdit.add(editPre[i]);
            jPanelEdit.add(edit[i]);
            jPanelEdit.add(name[i]);
            
            editPre[i].setEditable(false);
            edit[i].setVisible(false);
            name[i].setVisible(false);
        }
        Rectangle zero =  jLabelPosision.getBounds();
        Dimension panelsize = new Dimension(645, maxCols * 28 + zero.y + 50);
        jScrollPaneEdit.setPreferredSize(panelsize);
        jPanelEdit.setPreferredSize(panelsize);
        jLabelPosision.setVisible(false);
        for (int i = 0; i < maxCols; i++) {
            editPre[i].setBounds(zero.x + 100, zero.y + i * 28, 200, 25);
            editPre[i].setVisible(true);
            edit[i].setBounds(zero.x + 310, zero.y + i * 28, 300, 25);
            edit[i].setVisible(true);
            name[i].setBounds(zero.x, zero.y + i * 28, 200, 25);
            name[i].setVisible(true);
        }
        //TODO 最後の列が広すぎるのでなんとかしたいが、うまくいかない。
        jScrollPaneSheet.setPreferredSize(panelsize);
        jPanelSheet.setPreferredSize(panelsize);
        
        //画面を中心に表示
        java.awt.GraphicsEnvironment env = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
        // 変数desktopBoundsにデスクトップ領域を表すRectangleが代入される
        java.awt.Rectangle desktopBounds = env.getMaximumWindowBounds();
        java.awt.Rectangle thisBounds = this.getBounds();
        int x = desktopBounds.width / 2 - thisBounds.width / 2;
        int y = desktopBounds.height / 2 - thisBounds.height / 2;
        this.setBounds(x, y, thisBounds.width, thisBounds.height);
    }

    //レザルトセットを画面に描画
@SuppressWarnings("empty-statement")
    public void setResultSet(String rs[][], String where) {
        OsTextWhere.setText(where);
        strRS = rs;
        String ttl = "";
        for (int i = 0; i < rs.length; i++) {
            ModelL[i].clear();
            for (int j = 0; j < rs[i].length; j++) {
                if (j == 0) {
                    ttl = "■";
                } else {
                    ttl = "";
                }
                ModelL[i].addElement(ttl + rs[i][j] + ttl);;
            }
            lists[i].setModel(ModelL[i]);
        }
        //一覧の列の幅を初期化
        for (int i = 0; i < 26; i++) {
            split[i].setDividerLocation(80);
        }
        //一旦ボタンを不可にする
        jButtonUpdt.setEnabled(false);
        jButtonDel.setEnabled(false);
    }
    
    private void selectList(JList lst)  {
        int row = lst.getSelectedIndex();

        for (int i = 0; i < 26; i++) {
            try {
                lists[i].setSelectedIndex(row);
            }catch (Exception e) {
                //何もしない
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPaneSheet = new JTabbedPane();
        jSplitPane1 = new JSplitPane();
        jPanel1 = new JPanel();
        jComboBox1 = new JComboBox();
        jPanelSheet = new JPanel();
        jScrollPaneSheet = new JScrollPane();
        jSplitPaneCol1 = new JSplitPane();
        jSplitPaneCol2 = new JSplitPane();
        jSplitPaneCol3 = new JSplitPane();
        jSplitPaneCol4 = new JSplitPane();
        jSplitPaneCol5 = new JSplitPane();
        jSplitPaneCol6 = new JSplitPane();
        jSplitPaneCol7 = new JSplitPane();
        jSplitPaneCol8 = new JSplitPane();
        jSplitPaneCol9 = new JSplitPane();
        jSplitPaneCol10 = new JSplitPane();
        jSplitPaneCol11 = new JSplitPane();
        jSplitPaneCol12 = new JSplitPane();
        jSplitPaneCol13 = new JSplitPane();
        jSplitPaneCol14 = new JSplitPane();
        jSplitPaneCol15 = new JSplitPane();
        jSplitPaneCol16 = new JSplitPane();
        jSplitPaneCol17 = new JSplitPane();
        jSplitPaneCol18 = new JSplitPane();
        jSplitPaneCol19 = new JSplitPane();
        jSplitPaneCol20 = new JSplitPane();
        jSplitPaneCol21 = new JSplitPane();
        jSplitPaneCol22 = new JSplitPane();
        jSplitPaneCol23 = new JSplitPane();
        jSplitPaneCol24 = new JSplitPane();
        jSplitPaneCol25 = new JSplitPane();
        jScrollPaneCol26 = new JScrollPane();
        jList25 = new JList();
        jSplitPaneCol26 = new JSplitPane();
        jScrollPane27 = new JScrollPane();
        jList26 = new JList();
        jPanelLast = new JPanel();
        jScrollPane25 = new JScrollPane();
        jList24 = new JList();
        jScrollPane24 = new JScrollPane();
        jList23 = new JList();
        jScrollPane23 = new JScrollPane();
        jList22 = new JList();
        jScrollPane22 = new JScrollPane();
        jList21 = new JList();
        jScrollPane21 = new JScrollPane();
        jList20 = new JList();
        jScrollPane20 = new JScrollPane();
        jList19 = new JList();
        jScrollPane19 = new JScrollPane();
        jList18 = new JList();
        jScrollPane18 = new JScrollPane();
        jList17 = new JList();
        jScrollPane17 = new JScrollPane();
        jList16 = new JList();
        jScrollPane16 = new JScrollPane();
        jList15 = new JList();
        jScrollPane15 = new JScrollPane();
        jList14 = new JList();
        jScrollPane14 = new JScrollPane();
        jList13 = new JList();
        jScrollPane13 = new JScrollPane();
        jList12 = new JList();
        jScrollPane12 = new JScrollPane();
        jList11 = new JList();
        jScrollPane11 = new JScrollPane();
        jList10 = new JList();
        jScrollPane10 = new JScrollPane();
        jList9 = new JList();
        jScrollPane9 = new JScrollPane();
        jList8 = new JList();
        jScrollPane8 = new JScrollPane();
        jList7 = new JList();
        jScrollPane7 = new JScrollPane();
        jList6 = new JList();
        jScrollPane6 = new JScrollPane();
        jList5 = new JList();
        jScrollPane5 = new JScrollPane();
        jList4 = new JList();
        jScrollPane4 = new JScrollPane();
        jList3 = new JList();
        jScrollPane3 = new JScrollPane();
        jList2 = new JList();
        jScrollPane2 = new JScrollPane();
        jList1 = new JList();
        jScrollPaneEdit = new JScrollPane();
        jPanelEdit = new JPanel();
        jLabel1 = new JLabel();
        OsTextTableName = new OsText();
        jLabel2 = new JLabel();
        OsTextWhere = new OsText();
        jLabelPosision = new JLabel();
        jButtonUpdt = new JButton();
        jButtonDel = new JButton();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        jSplitPane1.setDividerLocation(25);
        jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);

        jComboBox1.setModel(new DefaultComboBoxModel(new String[] { "00001 Page" }));

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 552, Short.MAX_VALUE)
                .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jSplitPane1.setTopComponent(jPanel1);

        jPanelSheet.setLayout(new OverlayLayout(jPanelSheet));

        jScrollPaneSheet.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPaneSheet.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jSplitPaneCol1.setBorder(null);

        jSplitPaneCol2.setBorder(null);

        jSplitPaneCol3.setBorder(null);

        jSplitPaneCol4.setBorder(null);

        jSplitPaneCol5.setBorder(null);

        jSplitPaneCol6.setBorder(null);

        jSplitPaneCol7.setBorder(null);

        jSplitPaneCol8.setBorder(null);

        jSplitPaneCol9.setBorder(null);

        jSplitPaneCol10.setBorder(null);

        jSplitPaneCol11.setBorder(null);

        jSplitPaneCol12.setBorder(null);

        jSplitPaneCol13.setBorder(null);

        jSplitPaneCol14.setBorder(null);

        jSplitPaneCol15.setBorder(null);

        jSplitPaneCol16.setBorder(null);

        jSplitPaneCol17.setBorder(null);

        jSplitPaneCol18.setBorder(null);

        jSplitPaneCol19.setBorder(null);

        jSplitPaneCol20.setBorder(null);

        jSplitPaneCol21.setBorder(null);

        jSplitPaneCol22.setBorder(null);

        jSplitPaneCol23.setBorder(null);

        jSplitPaneCol24.setBorder(null);

        jSplitPaneCol25.setBorder(null);
        jSplitPaneCol25.setMaximumSize(new Dimension(200, 2147483647));

        jList25.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList25.setMaximumSize(new Dimension(80, 80));
        jList25.setMinimumSize(new Dimension(80, 80));
        jList25.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPaneCol26.setViewportView(jList25);

        jSplitPaneCol25.setLeftComponent(jScrollPaneCol26);

        jList26.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList26.setMaximumSize(new Dimension(80, 80));
        jList26.setMinimumSize(new Dimension(80, 80));
        jList26.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane27.setViewportView(jList26);

        jSplitPaneCol26.setLeftComponent(jScrollPane27);

        jPanelLast.setMaximumSize(new Dimension(1, 32767));
        jPanelLast.setMinimumSize(new Dimension(1, 100));
        jPanelLast.setPreferredSize(new Dimension(1, 100));
        jPanelLast.setLayout(null);
        jSplitPaneCol26.setRightComponent(jPanelLast);

        jSplitPaneCol25.setRightComponent(jSplitPaneCol26);

        jSplitPaneCol24.setRightComponent(jSplitPaneCol25);

        jList24.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList24.setMaximumSize(new Dimension(80, 80));
        jList24.setMinimumSize(new Dimension(80, 80));
        jList24.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane25.setViewportView(jList24);

        jSplitPaneCol24.setLeftComponent(jScrollPane25);

        jSplitPaneCol23.setRightComponent(jSplitPaneCol24);

        jList23.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList23.setMaximumSize(new Dimension(80, 80));
        jList23.setMinimumSize(new Dimension(80, 80));
        jList23.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane24.setViewportView(jList23);

        jSplitPaneCol23.setLeftComponent(jScrollPane24);

        jSplitPaneCol22.setRightComponent(jSplitPaneCol23);

        jList22.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList22.setMaximumSize(new Dimension(80, 80));
        jList22.setMinimumSize(new Dimension(80, 80));
        jList22.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane23.setViewportView(jList22);

        jSplitPaneCol22.setLeftComponent(jScrollPane23);

        jSplitPaneCol21.setRightComponent(jSplitPaneCol22);

        jList21.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList21.setMaximumSize(new Dimension(80, 80));
        jList21.setMinimumSize(new Dimension(80, 80));
        jList21.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane22.setViewportView(jList21);

        jSplitPaneCol21.setLeftComponent(jScrollPane22);

        jSplitPaneCol20.setRightComponent(jSplitPaneCol21);

        jList20.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList20.setMaximumSize(new Dimension(80, 80));
        jList20.setMinimumSize(new Dimension(80, 80));
        jList20.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane21.setViewportView(jList20);

        jSplitPaneCol20.setLeftComponent(jScrollPane21);

        jSplitPaneCol19.setRightComponent(jSplitPaneCol20);

        jList19.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList19.setMaximumSize(new Dimension(80, 80));
        jList19.setMinimumSize(new Dimension(80, 80));
        jList19.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane20.setViewportView(jList19);

        jSplitPaneCol19.setLeftComponent(jScrollPane20);

        jSplitPaneCol18.setRightComponent(jSplitPaneCol19);

        jList18.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList18.setMaximumSize(new Dimension(80, 80));
        jList18.setMinimumSize(new Dimension(80, 80));
        jList18.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane19.setViewportView(jList18);

        jSplitPaneCol18.setLeftComponent(jScrollPane19);

        jSplitPaneCol17.setRightComponent(jSplitPaneCol18);

        jList17.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList17.setMaximumSize(new Dimension(80, 80));
        jList17.setMinimumSize(new Dimension(80, 80));
        jList17.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane18.setViewportView(jList17);

        jSplitPaneCol17.setLeftComponent(jScrollPane18);

        jSplitPaneCol16.setRightComponent(jSplitPaneCol17);

        jList16.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList16.setMaximumSize(new Dimension(80, 80));
        jList16.setMinimumSize(new Dimension(80, 80));
        jList16.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane17.setViewportView(jList16);

        jSplitPaneCol16.setLeftComponent(jScrollPane17);

        jSplitPaneCol15.setRightComponent(jSplitPaneCol16);

        jList15.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList15.setMaximumSize(new Dimension(80, 80));
        jList15.setMinimumSize(new Dimension(80, 80));
        jList15.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane16.setViewportView(jList15);

        jSplitPaneCol15.setLeftComponent(jScrollPane16);

        jSplitPaneCol14.setRightComponent(jSplitPaneCol15);

        jList14.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList14.setMaximumSize(new Dimension(80, 80));
        jList14.setMinimumSize(new Dimension(80, 80));
        jList14.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane15.setViewportView(jList14);

        jSplitPaneCol14.setLeftComponent(jScrollPane15);

        jSplitPaneCol13.setRightComponent(jSplitPaneCol14);

        jList13.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList13.setMaximumSize(new Dimension(80, 80));
        jList13.setMinimumSize(new Dimension(80, 80));
        jList13.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane14.setViewportView(jList13);

        jSplitPaneCol13.setLeftComponent(jScrollPane14);

        jSplitPaneCol12.setRightComponent(jSplitPaneCol13);

        jList12.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList12.setMaximumSize(new Dimension(80, 80));
        jList12.setMinimumSize(new Dimension(80, 80));
        jList12.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane13.setViewportView(jList12);

        jSplitPaneCol12.setLeftComponent(jScrollPane13);

        jSplitPaneCol11.setRightComponent(jSplitPaneCol12);

        jList11.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList11.setMaximumSize(new Dimension(80, 80));
        jList11.setMinimumSize(new Dimension(80, 80));
        jList11.setPreferredSize(null);
        jList11.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane12.setViewportView(jList11);

        jSplitPaneCol11.setLeftComponent(jScrollPane12);

        jSplitPaneCol10.setRightComponent(jSplitPaneCol11);

        jList10.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList10.setMaximumSize(new Dimension(80, 80));
        jList10.setMinimumSize(new Dimension(80, 80));
        jList10.setPreferredSize(null);
        jList10.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane11.setViewportView(jList10);

        jSplitPaneCol10.setLeftComponent(jScrollPane11);

        jSplitPaneCol9.setRightComponent(jSplitPaneCol10);

        jList9.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList9.setMaximumSize(new Dimension(80, 80));
        jList9.setMinimumSize(new Dimension(80, 80));
        jList9.setPreferredSize(null);
        jList9.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane10.setViewportView(jList9);

        jSplitPaneCol9.setLeftComponent(jScrollPane10);

        jSplitPaneCol8.setRightComponent(jSplitPaneCol9);

        jList8.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList8.setMaximumSize(new Dimension(80, 80));
        jList8.setMinimumSize(new Dimension(80, 80));
        jList8.setPreferredSize(null);
        jList8.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane9.setViewportView(jList8);

        jSplitPaneCol8.setLeftComponent(jScrollPane9);

        jSplitPaneCol7.setRightComponent(jSplitPaneCol8);

        jList7.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList7.setMaximumSize(new Dimension(80, 80));
        jList7.setMinimumSize(new Dimension(80, 80));
        jList7.setPreferredSize(null);
        jList7.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane8.setViewportView(jList7);

        jSplitPaneCol7.setLeftComponent(jScrollPane8);

        jSplitPaneCol6.setRightComponent(jSplitPaneCol7);

        jList6.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList6.setMaximumSize(new Dimension(80, 80));
        jList6.setMinimumSize(new Dimension(80, 80));
        jList6.setPreferredSize(null);
        jList6.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane7.setViewportView(jList6);

        jSplitPaneCol6.setLeftComponent(jScrollPane7);

        jSplitPaneCol5.setRightComponent(jSplitPaneCol6);

        jList5.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList5.setMaximumSize(new Dimension(80, 80));
        jList5.setMinimumSize(new Dimension(80, 80));
        jList5.setPreferredSize(null);
        jList5.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane6.setViewportView(jList5);

        jSplitPaneCol5.setLeftComponent(jScrollPane6);

        jSplitPaneCol4.setRightComponent(jSplitPaneCol5);

        jList4.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList4.setMaximumSize(new Dimension(80, 80));
        jList4.setMinimumSize(new Dimension(80, 80));
        jList4.setPreferredSize(null);
        jList4.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane5.setViewportView(jList4);

        jSplitPaneCol4.setLeftComponent(jScrollPane5);

        jSplitPaneCol3.setRightComponent(jSplitPaneCol4);

        jList3.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList3.setMaximumSize(new Dimension(80, 80));
        jList3.setMinimumSize(new Dimension(80, 80));
        jList3.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane4.setViewportView(jList3);

        jSplitPaneCol3.setLeftComponent(jScrollPane4);

        jSplitPaneCol2.setRightComponent(jSplitPaneCol3);

        jList2.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList2.setMaximumSize(new Dimension(80, 80));
        jList2.setMinimumSize(new Dimension(80, 80));
        jList2.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane3.setViewportView(jList2);

        jSplitPaneCol2.setLeftComponent(jScrollPane3);

        jSplitPaneCol1.setRightComponent(jSplitPaneCol2);

        jList1.setModel(new AbstractListModel() {
            String[] strings = { "■Title■", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setMaximumSize(new Dimension(80, 80));
        jList1.setMinimumSize(new Dimension(80, 80));
        jList1.setPreferredSize(null);
        jList1.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jListValueChange(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        jSplitPaneCol1.setLeftComponent(jScrollPane2);

        jScrollPaneSheet.setViewportView(jSplitPaneCol1);

        jPanelSheet.add(jScrollPaneSheet);

        jSplitPane1.setRightComponent(jPanelSheet);

        jTabbedPaneSheet.addTab("一覧", jSplitPane1);

        jLabel1.setText("テーブル名");

        OsTextTableName.setEditable(false);

        jLabel2.setText("Where句");

        OsTextWhere.setEditable(false);

        jLabelPosision.setText("+");

        jButtonUpdt.setText("更新");
        jButtonUpdt.setEnabled(false);
        jButtonUpdt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonUpdtActionPerformed(evt);
            }
        });

        jButtonDel.setText("削除");
        jButtonDel.setEnabled(false);
        jButtonDel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonDelActionPerformed(evt);
            }
        });

        jLabel3.setText("変更前");

        jLabel4.setText("変更後");

        GroupLayout jPanelEditLayout = new GroupLayout(jPanelEdit);
        jPanelEdit.setLayout(jPanelEditLayout);
        jPanelEditLayout.setHorizontalGroup(
            jPanelEditLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEditLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(OsTextWhere, GroupLayout.PREFERRED_SIZE, 504, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelEditLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(OsTextTableName, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonDel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonUpdt)
                        .addGap(30, 30, 30))
                    .addGroup(jPanelEditLayout.createSequentialGroup()
                        .addComponent(jLabelPosision, GroupLayout.PREFERRED_SIZE, 9, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanelEditLayout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addComponent(jLabel3)
                .addGap(165, 165, 165)
                .addComponent(jLabel4)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelEditLayout.setVerticalGroup(
            jPanelEditLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditLayout.createSequentialGroup()
                .addGroup(jPanelEditLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelEditLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(OsTextTableName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelEditLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonUpdt)
                        .addComponent(jButtonDel)))
                .addGap(18, 18, 18)
                .addGroup(jPanelEditLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(OsTextWhere, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelEditLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(15, 15, 15)
                .addComponent(jLabelPosision)
                .addContainerGap(332, Short.MAX_VALUE))
        );

        jScrollPaneEdit.setViewportView(jPanelEdit);

        jTabbedPaneSheet.addTab("編集", jScrollPaneEdit);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 668, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPaneSheet, GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 445, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPaneSheet, GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jListValueChange(ListSelectionEvent evt) {//GEN-FIRST:event_jListValueChange
        // TODO add your handling code here:
        //リストをクリック等で選択した場合の処理
        //すべての列で選択し直し
        selectList((JList)evt.getSource());
        //編集画面に転送
        if (lists[0].getSelectedIndex() <= 0) {
            //データが選択されていない：新規作成
            jButtonUpdt.setEnabled(true);
            jButtonDel.setEnabled(false);
            for (int i = 0; i < strRS.length; i++) {
                name[i].setText(strRS[i][0]);
                editPre[i].setText("");
                edit[i].setText("");
            }
        } else {
            //データを選択している場合
            jButtonUpdt.setEnabled(true);
            jButtonDel.setEnabled(true);
            for (int i = 0; i < strRS.length; i++) {
                name[i].setText(strRS[i][0]);
                editPre[i].setText((String)lists[i].getSelectedValue());
                edit[i].setText((String)lists[i].getSelectedValue());
            }
        }
    }//GEN-LAST:event_jListValueChange

    private void jButtonUpdtActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonUpdtActionPerformed
        // TODO add your handling code here:
        //更新ボタン
        
        String msg = "";    //ErrMsg
        
        //項目名と値を取得
        ArrayList field[] = new ArrayList[3];
        field[0] = new ArrayList();     //カラム名
        field[1] = new ArrayList();     //変更前
        field[2] = new ArrayList();     //変更後
        
        for (int i = 0; i < maxCols; i++) {
            if (name[i].getText().equals("")) {
                break;
            }
            
            field[0].add(name[i].getText());
            field[1].add(editPre[i].getText());
            field[2].add(edit[i].getText());
        }
        
        //Insert, Updateの判定
        if (jButtonDel.isEnabled()) {
            //Update
            if (JOptionPane.showConfirmDialog(this, "更新(Update)します。\nよろしいですか？", "Update", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
                return;
            }
            msg = dbAc.update(field);
        } else {
            //Insert
            if (JOptionPane.showConfirmDialog(this, "更新(Insert)します。\nよろしいですか？", "Insert", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
                return;
            }
            msg = dbAc.insert(field);
        }
        
        //エラーメッセージ表示
        if (!msg.equals("")) {
            JOptionPane.showMessageDialog(this, msg, "エラー", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(this, "更新しました。", "情報", JOptionPane.INFORMATION_MESSAGE);
        
        //一覧を再表示
        //テーブルのレザルトセットを取得
        String where = OsTextWhere.getText();
        String[][] str = dbAc.getResultSetTable(where);
        //結果を一覧にセット
        setResultSet(str, where);
        
        //更新直後は追加可能にする
        jButtonUpdt.setEnabled(true);
        
        return;
    }//GEN-LAST:event_jButtonUpdtActionPerformed

    private void jButtonDelActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonDelActionPerformed
        // TODO add your handling code here:
        //削除ボタン
        
        String msg = "";    //ErrMsg
        
        //項目名と値を取得
        ArrayList field[] = new ArrayList[3];
        field[0] = new ArrayList();     //カラム名
        field[1] = new ArrayList();     //変更前
        field[2] = new ArrayList();     //変更後
        
        for (int i = 0; i < maxCols; i++) {
            if (name[i].getText().equals("")) {
                break;
            }
            
            field[0].add(name[i].getText());
            field[1].add(editPre[i].getText());
            field[2].add(edit[i].getText());
        }
        
        //Delete
        if (JOptionPane.showConfirmDialog(this, "削除します。\nよろしいですか？", "Delete", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }
        msg = dbAc.delete(field);
        
        //エラーメッセージ表示
        if (!msg.equals("")) {
            JOptionPane.showMessageDialog(this, msg, "エラー", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(this, "削除しました。", "情報", JOptionPane.INFORMATION_MESSAGE);
        
        //一覧を再表示
        //テーブルのレザルトセットを取得
        String where = OsTextWhere.getText();
        String[][] str = dbAc.getResultSetTable(where);
        //結果を一覧にセット
        setResultSet(str, where);
        
        return;
    }//GEN-LAST:event_jButtonDelActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(dbSheetFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dbSheetFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dbSheetFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dbSheetFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        //final String tableNameMain = args[1];
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dbSheetFrame(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private OsText OsTextTableName;
    private OsText OsTextWhere;
    private JButton jButtonDel;
    private JButton jButtonUpdt;
    private JComboBox jComboBox1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabelPosision;
    private JList jList1;
    private JList jList10;
    private JList jList11;
    private JList jList12;
    private JList jList13;
    private JList jList14;
    private JList jList15;
    private JList jList16;
    private JList jList17;
    private JList jList18;
    private JList jList19;
    private JList jList2;
    private JList jList20;
    private JList jList21;
    private JList jList22;
    private JList jList23;
    private JList jList24;
    private JList jList25;
    private JList jList26;
    private JList jList3;
    private JList jList4;
    private JList jList5;
    private JList jList6;
    private JList jList7;
    private JList jList8;
    private JList jList9;
    private JPanel jPanel1;
    private JPanel jPanelEdit;
    private JPanel jPanelLast;
    private JPanel jPanelSheet;
    private JScrollPane jScrollPane10;
    private JScrollPane jScrollPane11;
    private JScrollPane jScrollPane12;
    private JScrollPane jScrollPane13;
    private JScrollPane jScrollPane14;
    private JScrollPane jScrollPane15;
    private JScrollPane jScrollPane16;
    private JScrollPane jScrollPane17;
    private JScrollPane jScrollPane18;
    private JScrollPane jScrollPane19;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane20;
    private JScrollPane jScrollPane21;
    private JScrollPane jScrollPane22;
    private JScrollPane jScrollPane23;
    private JScrollPane jScrollPane24;
    private JScrollPane jScrollPane25;
    private JScrollPane jScrollPane27;
    private JScrollPane jScrollPane3;
    private JScrollPane jScrollPane4;
    private JScrollPane jScrollPane5;
    private JScrollPane jScrollPane6;
    private JScrollPane jScrollPane7;
    private JScrollPane jScrollPane8;
    private JScrollPane jScrollPane9;
    private JScrollPane jScrollPaneCol26;
    private JScrollPane jScrollPaneEdit;
    private JScrollPane jScrollPaneSheet;
    private JSplitPane jSplitPane1;
    private JSplitPane jSplitPaneCol1;
    private JSplitPane jSplitPaneCol10;
    private JSplitPane jSplitPaneCol11;
    private JSplitPane jSplitPaneCol12;
    private JSplitPane jSplitPaneCol13;
    private JSplitPane jSplitPaneCol14;
    private JSplitPane jSplitPaneCol15;
    private JSplitPane jSplitPaneCol16;
    private JSplitPane jSplitPaneCol17;
    private JSplitPane jSplitPaneCol18;
    private JSplitPane jSplitPaneCol19;
    private JSplitPane jSplitPaneCol2;
    private JSplitPane jSplitPaneCol20;
    private JSplitPane jSplitPaneCol21;
    private JSplitPane jSplitPaneCol22;
    private JSplitPane jSplitPaneCol23;
    private JSplitPane jSplitPaneCol24;
    private JSplitPane jSplitPaneCol25;
    private JSplitPane jSplitPaneCol26;
    private JSplitPane jSplitPaneCol3;
    private JSplitPane jSplitPaneCol4;
    private JSplitPane jSplitPaneCol5;
    private JSplitPane jSplitPaneCol6;
    private JSplitPane jSplitPaneCol7;
    private JSplitPane jSplitPaneCol8;
    private JSplitPane jSplitPaneCol9;
    private JTabbedPane jTabbedPaneSheet;
    // End of variables declaration//GEN-END:variables
}
