package br.edu.utfpr.carolineadao.myseries.utils;

public class UtilsString {

    public static boolean stringEmpty(String text){

        return text == null || text.trim().length() == 0;
    }
}