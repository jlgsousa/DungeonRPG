package com.homemade.dungeondroid.map.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapLoader {
    private static final char openBracket = '{';
    private static final char closeBracket = '}';
    private static final char openSquareBracket = '[';
    private static final char closeSquareBracket = ']';
    private static final char comma = ',';

    public MapLoader() {
    }

    public Integer[][] loadMap(Context context, int resourceId) {
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);

            StringBuilder mapJsonString = new StringBuilder();
            mapJsonString.append(openBracket);
            mapJsonString.append("\"levelMap\": ");
            mapJsonString.append(openSquareBracket);

            for(int row = 0; row < bitmap.getWidth(); ++row) {
                mapJsonString.append(openSquareBracket);

                for(int column = 0; column < bitmap.getHeight(); ++column) {
                    mapJsonString.append(bitmap.getPixel(row, column) * -1 / 655360);
                    if (column != bitmap.getHeight() - 1) {
                        mapJsonString.append(comma);
                    }
                }

                mapJsonString.append(closeSquareBracket).append("\n");
                if (row <= bitmap.getWidth() - 2) {
                    mapJsonString.append(comma);
                }
            }

            mapJsonString.append(closeSquareBracket);
            mapJsonString.append(closeBracket);

            Log.e("GameMap", mapJsonString.toString());
            JSONObject jsonObject = new JSONObject(mapJsonString.toString());

            JSONArray levelMapArray = (JSONArray) jsonObject.get("levelMap");
            JSONArray firstArray = levelMapArray.getJSONArray(0);
            Integer[][] array = new Integer[levelMapArray.length()][firstArray.length()];
            for(int i=0; i < levelMapArray.length(); i++) {
                for (int j = 0; j < firstArray.length(); j++) {
                    array[i][j] = levelMapArray.getJSONArray(i).getInt(j);
                }
            }

            return array;
        } catch (JSONException e) {
            System.out.println("Error occurred during map loading");
            e.printStackTrace();
            return null;
        }
    }
}
