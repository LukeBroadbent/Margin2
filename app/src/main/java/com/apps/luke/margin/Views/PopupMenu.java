package com.apps.luke.margin.Views;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;

import com.apps.luke.margin.R;

/**
 * Created by chris on 14/04/16.
 */
public class PopupMenu {

    Context context;
    String POPUP_TYPE_NEWUSER = "new_user";
    String POPUP_TYPE_EXISTINGUSER = "existing_user";
    String POPUP_TYPE_EDITUSER = "edit_user";

    Dialog popup_Dialog;

    Button ok, cancel;


    public PopupMenu()
    {

    }

    public PopupMenu(Context c, String type)
    {
        context = c;


        if(type.equals(POPUP_TYPE_EDITUSER))
        {
            popup_Dialog = new Dialog(context);
            popup_Dialog.setContentView(R.layout.new_user);
            popup_Dialog.setTitle("Edit User");

            ok = (Button) popup_Dialog.findViewById(R.id.bConfirm);
            cancel = (Button) popup_Dialog.findViewById(R.id.bCancel);
        }

        if(type.equals(POPUP_TYPE_NEWUSER))
        {
            popup_Dialog = new Dialog(context);
            popup_Dialog.setContentView(R.layout.new_user);
            popup_Dialog.setTitle("New User");

            ok = (Button) popup_Dialog.findViewById(R.id.bConfirm);
            cancel = (Button) popup_Dialog.findViewById(R.id.bCancel);
        }

        if(type.equals(POPUP_TYPE_EXISTINGUSER))
        {
            popup_Dialog = new Dialog(context);
            popup_Dialog.setContentView(R.layout.existing_users);
            popup_Dialog.setTitle("Search Users");

            ok = (Button) popup_Dialog.findViewById(R.id.bConfirm);
            cancel = (Button) popup_Dialog.findViewById(R.id.bCancel);

        }
    }

    public void showDialog()
    {
        popup_Dialog.show();
    }

    public Dialog getPopupDialog()
    {
        return popup_Dialog;
    }

    public Button returnOkButton()
    {
        return ok;
    }

    public Button returnCancelButton()
    {
        return cancel;
    }


}
