/*
 * Copyright (C) 2014 hdm
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package openseiho;

/**
 *
 * @author TANAKA_Hidemune
 */
public class OsText extends javax.swing.JTextField {
    private int mode = 0;//nanimosinai
    
    public OsText() {
        super();
        
        this.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                OsText.henkan((OsText) e.getSource());
            }
        });
    }
    public OsText(String string) {
        super.setText(string);
    }
    public void setMode(int mode) {
        this.mode = mode;
    }
    public int getMode() {
        return this.mode;
    }
    //roma ji kana henkan
    public static void henkan(OsText Pthis) {
        String str = Pthis.getText();
        StringBuilder sb = new StringBuilder();
        String[][] dic =  {
            {"a", "あ"},
            {"i", "い"},
            {"u", "う"},
            {"e", "え"},
            {"o", "お"},
            {"ka", "か"},
            {"ki", "き"},
            {"ku", "く"},
            {"ke", "け"},
            {"ko", "こ"},
            {"sa", "さ"},
            {"si", "し"},
            {"su", "す"},
            {"se", "せ"},
            {"so", "そ"},
            {"ta", "た"},
            {"ti", "ち"},
            {"tu", "つ"},
            {"te", "て"},
            {"to", "と"},
            {"na", "な"},
            {"ni", "に"},
            {"nu", "ぬ"},
            {"ne", "ね"},
            {"no", "の"},
            {"ha", "は"},
            {"hi", "ひ"},
            {"hu", "ふ"},
            {"he", "へ"},
            {"ho", "ほ"},
            {"ma", "ま"},
            {"mi", "み"},
            {"mu", "む"},
            {"me", "め"},
            {"mo", "も"},
            {"ya", "や"},
            {"yi", "い"},
            {"yu", "ゆ"},
            {"ye", "え"},
            {"yo", "よ"},
            {"ra", "ら"},
            {"ri", "り"},
            {"ru", "る"},
            {"re", "れ"},
            {"ro", "ろ"},
            {"wa", "わ"},
            {"wi", "い"},
            {"wu", "う"},
            {"we", "え"},
            {"wo", "を"},
            {"ga", "が"},
            {"gi", "ぎ"},
            {"gu", "ぐ"},
            {"ge", "げ"},
            {"go", "ご"},
            {"za", "ざ"},
            {"zi", "じ"},
            {"zu", "ず"},
            {"ze", "ぜ"},
            {"zo", "ぞ"},
            {"da", "だ"},
            {"di", "ぢ"},
            {"du", "づ"},
            {"de", "で"},
            {"do", "ど"},
            {"ba", "ば"},
            {"bi", "び"},
            {"bu", "ぶ"},
            {"be", "べ"},
            {"bo", "ぼ"},
            {"sya", "しゃ"},
            {"syi", "しぃ"},
            {"syu", "しゅ"},
            {"sye", "しぇ"},
            {"syo", "しょ"},
            {"pya", "ぴゃ"},
            {"pyi", "ぴぃ"},
            {"pyu", "ぴゅ"},
            {"pye", "ぴぇ"},
            {"pyo", "ぴょ"},
            {"nn", "ん"},
            {"la", "ぁ"},
            {"li", "ぃ"},
            {"lu", "ぅ"},
            {"le", "ぇ"},
            {"lo", "ぉ"},
            {"xa", "ぁ"},
            {"xi", "ぃ"},
            {"xu", "ぅ"},
            {"xe", "ぇ"},
            {"xo", "ぉ"},
        };
        for (int i=0;i<str.length();i++) {
            String sub = str.substring(i).toLowerCase();
            int j;
            for( j=0;j<dic.length;j++) {
                if (sub.startsWith(dic[j][0])) {
                    sb.append(dic[j][1]);
                    i = i + dic[j][0].length() - 1;
                    break;
                }
            }
            if (sub.charAt(0) == sub.charAt(1)) {
                sb.append("っ");
            }else if (j >= dic.length) {
                //gaitounasi
                sb.append(sub.charAt(0));
            }
        }
        Pthis.setText(sb.toString());
    }
    /**
     *
     * @param str
     */
    @Override
    public void setText(String str) {
        str = str.replaceAll("''*", "'");
        super.setText(str);
    }
    /**
     * SQLインジェクション対策をここで行う
     * @return
     */
    @Override
    public String getText() {
        String wk = super.getText();
        
        wk = wk.replaceAll("''*", "'");
        wk = wk.replaceAll("'", "''");
        //wk = wk.replaceAll("\\\\", "");
        return wk;
    }
}
