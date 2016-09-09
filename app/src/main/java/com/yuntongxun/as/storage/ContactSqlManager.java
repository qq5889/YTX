/*
 *  Copyright (c) 2013 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.yuntongxun.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package com.yuntongxun.as.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.yuntongxun.as.common.CCPAppManager;
import com.yuntongxun.as.core.ClientUser;
import com.yuntongxun.as.ui.contact.ECContacts;

import java.util.ArrayList;

/**
 * 联系人数据库管理
 *
 * @author Jorstin Chan@容联•云通讯
 * @version 4.0
 * @date 2014-12-12
 */
public class ContactSqlManager extends AbstractSQLManager {

    private static ContactSqlManager sInstance;

    private static ContactSqlManager getInstance() {
        if (sInstance == null) {
            sInstance = new ContactSqlManager();
        }
        return sInstance;
    }

    public static boolean hasContact(String contactId) {
        String sql = "select "+ContactsColumn.CONTACT_ID+" from "+DatabaseHelper.TABLES_NAME_CONTACT+" where "+ContactsColumn.CONTACT_ID+" = '" + contactId + "'";
        Cursor cursor = getInstance().sqliteDB().rawQuery(sql, null);
        if (cursor != null && cursor.getCount() > 0) {
//            cursor.close();
            return true;
        }
        return false;
    }

    /**
     * 插入联系人到数据库
     *
     * @param contact
     * @return
     */
    public static long insertContact(ECContacts contact) {
        if (contact == null || TextUtils.isEmpty(contact.getContactid())) {
            return -1;
        }
        try {
            ContentValues values = contact.buildContentValues();
            if (!hasContact(contact.getContactid())) {
                return getInstance().sqliteDB().insert(DatabaseHelper.TABLES_NAME_CONTACT, null, values);
            }
            getInstance().sqliteDB().update(DatabaseHelper.TABLES_NAME_CONTACT, values, ContactsColumn.CONTACT_ID+" = '" + contact.getContactid() + "'", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 查询联系人名称
     *
     * @param contactId
     * @return
     */
    public static ArrayList<String> getContactName(String[] contactId) {
        ArrayList<String> contacts = null;
        try {
            String sql = "select "+ContactsColumn.USERNAME+" ,"+ContactsColumn.CONTACT_ID+" from "+DatabaseHelper.TABLES_NAME_CONTACT+" where "+ContactsColumn.CONTACT_ID+" in ";
            StringBuilder sb = new StringBuilder("(");
            for (int i = 0; i < contactId.length; i++) {
                sb.append("'").append(contactId[i]).append("'");
                if (i != contactId.length - 1) {
                    sb.append(",");
                }
            }
            sb.append(")");
            Cursor cursor = getInstance().sqliteDB().rawQuery(
                    sql + sb.toString(), null);
            if (cursor != null && cursor.getCount() > 0) {
                contacts = new ArrayList<>();
                // 过滤自己的联系人账号
                ClientUser clientUser = CCPAppManager.getClientUser();
                while (cursor.moveToNext()) {
                    if (clientUser != null
                            && clientUser.getUserId().equals(
                            cursor.getString(0))) {
                        continue;
                    }
                    String displayName = cursor.getString(0);
                    String contact_id = cursor.getString(1);
                    if (TextUtils.isEmpty(displayName) || TextUtils.isEmpty(contact_id) || displayName.equals(contact_id)) {
                        continue;
                    }
                    contacts.add(displayName);
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contacts;
    }

    /**
     * 根据联系人账号查询
     *
     * @param contactId
     * @return
     */
    public static ECContacts getContact(String contactId) {
        if (TextUtils.isEmpty(contactId)) {
            return null;
        }
        ECContacts c = new ECContacts(contactId);
        c.setNickname(contactId);
        try {
            Cursor cursor = getInstance().sqliteDB().query(DatabaseHelper.TABLES_NAME_CONTACT, new String[]{ContactsColumn.ID, ContactsColumn.USERNAME, ContactsColumn.CONTACT_ID, ContactsColumn.REMARK},
                    ContactsColumn.CONTACT_ID+"=?", new String[]{contactId}, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    c = new ECContacts(cursor.getString(2));
                    c.setNickname(cursor.getString(1));
                    c.setRemark(cursor.getString(3));
                    c.setId(cursor.getInt(0));
                }
                cursor.close();
            }
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }


    public static void reset() {
        getInstance().release();
        sInstance = null;
    }
}
