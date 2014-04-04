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

    public OsText() {
        super();
    }
    public OsText(String string) {
        super.setText(string);
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
