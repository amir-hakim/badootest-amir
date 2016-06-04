package com.badoo.test.helper;

import com.badoo.test.view.BaseApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utilities {

    /*
     * Read json file from the assets folder and returns a string of it's content
     */
    public static String readJsonFromAssetFile(String fileName) {
        String jsonResponse = null;
        try {
            jsonResponse = "";
            StringBuilder stringBuilder = new StringBuilder();
            InputStream inputStream = BaseApplication.getContext().getResources().getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            if (inputStream != null) {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
            jsonResponse = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }
}
