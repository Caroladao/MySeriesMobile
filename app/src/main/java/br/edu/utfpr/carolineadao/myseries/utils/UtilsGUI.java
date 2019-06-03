package br.edu.utfpr.carolineadao.myseries.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import br.edu.utfpr.carolineadao.myseries.R;

public class UtilsGUI {

    public static void warningError(Context context, int idText){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.warning);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(idText);

        builder.setNeutralButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void confirmAction(Context context,
                                    String message,
                                    DialogInterface.OnClickListener listener){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.confirmation);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setMessage(message);

        builder.setPositiveButton(R.string.yes, listener);
        builder.setNegativeButton(R.string.no, listener);

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static String checkText(Context context,
                                  EditText editText,
                                  int idMessage){

        String text = editText.getText().toString();

        if (UtilsString.stringEmpty(text)){
            UtilsGUI.warningError(context, idMessage);
            editText.setText(null);
            editText.requestFocus();
            return null;
        }else{
            return text.trim();
        }
    }
}

