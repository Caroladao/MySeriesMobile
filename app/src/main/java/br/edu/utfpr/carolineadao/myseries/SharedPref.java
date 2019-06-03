package br.edu.utfpr.carolineadao.myseries;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    SharedPreferences mySharedPreferences;

    public SharedPref (Context context){
        mySharedPreferences = (SharedPreferences) context.getSharedPreferences("filename",Context.MODE_PRIVATE);
    }

    //salva o status true ou false
    public void setDarkThemeState( Boolean state ){
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putBoolean("DarkTheme",state);
        editor.commit();
    }

    //carrega o dark theme
    public Boolean loadDarkModeState () {
        Boolean state = mySharedPreferences.getBoolean("DarkTheme",false);
        return state;
    }

}
