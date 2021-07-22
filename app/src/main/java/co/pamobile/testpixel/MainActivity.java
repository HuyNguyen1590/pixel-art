package co.pamobile.testpixel;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.spout.nbt.ByteArrayTag;
import org.spout.nbt.CompoundTag;
import org.spout.nbt.ListTag;
import org.spout.nbt.ShortTag;
import org.spout.nbt.StringTag;
import org.spout.nbt.Tag;
import org.spout.nbt.stream.NBTOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    ImageView imageView, imageView1;
    TextView textView;
    Button button;
    public static String []blockRGBString = {
            "35:0",
            "35:1",
            "35:2",
            "35:3",
            "35:4",
            "35:5",
            "35:6",
            "35:7",
            "35:8",
            "35:9",
            "35:10",
            "35:11",
            "35:12",
            "35:13",
            "35:14",
            "35:15",

            "172:0",
            "172:1",
            "172:2",
            "172:3",
            "172:4",
            "172:5",
            "172:6",
            "172:7",
            "172:8",
            "172:9",
            "172:10",
            "172:11",
            "172:12",
            "172:13",
            "172:14",
            "172:15",

            "236:0",
            "236:1",
            "236:2",
            "236:3",
            "236:4",
            "236:5",
            "236:6",
            "236:7",
            "236:8",
            "236:9",
            "236:10",
            "236:11",
            "236:12",
            "236:13",
            "236:14",
            "236:15",

            "57:0",
            "41:0",
            "24:3",
            "1:6",
            "1:2"
    };
    //region blockRGB
    public static int [][] blockRGB = {
            {233, 236, 236}, // White Wool 35:0
            {240, 118, 19 }, // Orange Wool 35:1
            {189, 68 , 179}, // Magenta Wool(品红色) 35:2
            {58 , 175, 217}, // Light Blue Wool(淡蓝色) 35:3
            {248, 197, 39 }, // Yellow Wool 35:4
            {112, 185, 25 }, // Lime Wool(黄绿色) 35:5
            {237, 141, 172}, // Pink Wool 35:6
            {62 , 68 , 71 }, // Gray Wool 35:7
            {142, 142, 134}, // Light Gray Wool(Sliver) 35:8
            {21 , 137, 145}, // Cyan Wool(青色) 35:9
            {121, 42 , 172}, // Purple Wool 35:10
            {53 , 57 , 157}, // Blue Wool 35:11
            {114, 71 , 40 }, // Brown Wool 35:12
            {84 , 109, 27 }, // Green Wool 35:13
            {160, 39 , 34 }, // Red Wool 35:14
            {20 , 21 , 25 }, // Black Wool 35:15

            {209, 178, 161}, // White Terracotta(陶瓦) 172:0
            {161, 83 , 37 }, // Orange Terracotta 172:1
            {149, 88 , 108}, // Magenta Terracotta 172:2
            {113, 108, 137}, // Light Blue Terracotta 172:3
            {186, 133, 35 }, // Yellow Terracotta 172:4
            {103, 117, 52 }, // Lime Terracotta 172:5
            {161, 78 , 78 }, // Pink Terracotta 172:6
            {57 , 42 , 35 }, // Gray Terracotta 172:7
            {135, 106, 97 }, // Light Gray Terracotta 172:8
            {86 , 91 , 91 }, // Cyan Terracotta 172:9
            {118, 70 , 86 }, // Purple Terracotta 172:10
            {74 , 59 , 91 }, // Blue Terracotta 172:11
            {77 , 51 , 35 }, // Brown Terracotta 172:12
            {76 , 83 , 42 }, // Green Terracotta 172:13
            {143, 61 , 46 }, // Red Terracotta 172:14
            {37 , 22 , 16 }, // Black Terracotta 172:15

            {207, 213, 214}, // White Concrete 236:0
            {224, 97 , 0  }, // Orange Concrete 236:1
            {169, 48 , 159}, // Magenta Concrete 236:2
            {35 , 137, 198}, // Light Blue Concrete 236:3
            {240, 175, 21 }, // Yellow Concrete 236:4
            {94 , 168, 24 }, // Lime Concrete 236:5
            {213, 101, 142}, // Pink Concrete 236:6
            {54 , 57 , 61 }, // Gray Concrete 236:7
            {125, 125, 115}, // Light Gray Concrete 236:8
            {21 , 119, 136}, // Cyan Concrete 236:9
            {100, 31 , 156}, // Purple Concrete 236:10
            {44 , 46 , 143}, // Blue Concrete 236:11
            {96 , 59 , 31 }, // Brown Concrete 236:12
            {73 , 91 , 36 }, // Green Concrete 236:13
            {142, 32 , 32 }, // Red Concrete 236:14
            {8  , 10 , 15 }, // Black Concrete 236:15

            {97 , 219, 213}, // Block of Diamond 57:0
            {249, 236, 78 }, // Block of Gold 41:0
            {219, 211, 161}, // Smooth Sandstone 24:3
            {133, 133, 134}, // Polished Andesite 1:6
            {159, 114, 98 }, // Polished Granite 1:2
    };
    public static String[] glassColorData = {
            "241:0",
            "241:1",
            "241:2",
            "241:3",
            "241:4",
            "241:5",
            "241:6",
            "241:7",
            "241:8",
            "241:9",
            "241:10",
            "241:11",
            "241:12",
            "241:13",
            "241:14",
            "241:15"
    };
    //endregion
    //region glass colors
    public static
    int [][] glass_colors = {
            {255, 255, 255}, // White Glass 241:0
            {216, 127, 51 }, // Orange Glass 241:1
            {178, 76 , 216}, // Magenta Glass 241:2
            {102, 153, 216}, // Light Blue Glass 241:3
            {229, 229, 51 }, // Yellow Glass 241:4
            {127, 204, 25 }, // Lime Glass 241:5
            {242, 127, 165}, // Pink Glass 241:6
            {76 , 76 , 76 }, // Gray Glass 241:7
            {153, 153, 153}, // Light Gray Glass 241:8
            {76 , 127, 153}, // Cyan Glass 241:9
            {127, 63 , 178}, // Purple Glass 241:10
            {51 , 76 , 178}, // Blue Glass 241:11
            {102, 76 , 51 }, // Brown Glass 241:12
            {102, 127, 51 }, // Green Glass 241:13
            {153, 51 , 51 }, // Red Glass 241:14
            {25 , 25 , 25 }, // Black Glass 241:15
    };
    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        imageView = findViewById(R.id.iv);
        imageView1 = findViewById(R.id.iv1);
        textView = findViewById(R.id.tv);
        button = findViewById(R.id.btn);
        button.setOnClickListener(v -> {
            try {
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,()->{
                    mGetContent.launch("image/*");
                    return null;
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri
                    if (imageView!=null && uri!=null){
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Save")
                                .setMessage("Saving...")
                                .setCancelable(false)
                                .create();
                        alertDialog.show();
                        imageView.setImageURI(uri);
                        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                        int width = bitmap.getWidth();
                        int height = bitmap.getHeight();
                        byte[][]temp = new byte[width][height];
                        byte[][]temp1 = new byte[width][height];
//                        byte[]blocks = new byte[bitmap.getByteCount()];
//                        byte[]metadata = new byte[bitmap.getByteCount()];
                        imageView1.setImageBitmap(bitmap);
                        for (int i = 0; i<width; i++){
                            for (int j = 0; j<height; j++){
                                int pixel = bitmap.getPixel(i,j);
                                int redValue = Color.red(pixel);
                                int blueValue = Color.blue(pixel);
                                int greenValue = Color.green(pixel);
                                boolean isTransparent = (pixel & 0xff000000) == 0x0;
                                String data = "";
                                int[] color = new int[3];
                                color[0] = redValue;
                                color[1] = greenValue;
                                color[2] = blueValue;
                                if (isTransparent){
                                    data = mapColor(color, 16);
                                }else {
                                    data = mapColor(color, 53);
                                }
                                int id = Integer.parseInt(data.split(":")[0]);
                                temp[width-1-i][height-1-j]= (byte) id;
                                temp1[width-1-i][height-1-j] = Byte.parseByte(data.split(":")[1]);
                            }
                        }
                        File schematic = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/pixelArt.schematic");
                        try {

                            save(schematic,(short) width,(short) height,(short) 1,output(temp),output(temp1));
                            alertDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            try {
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,()->{
                    mGetContent.launch("image/*");
                    return null;
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String mapColor(int[] c, int total){
        int id = -1;
        double min_dist = 100000000.0;
        for(int i = 0; i < total; i++){
            double dis;
            if(total == 16){
                dis = colorDistance(c,glass_colors[i]);
            }else {
                dis = colorDistance(c, blockRGB[i]);
            }
            if(dis < min_dist){
                min_dist = dis;
                id = i;
            }
        }
        String data = "0:0";
        if (total == 16){
            data = glassColorData[id];
        }else {
            data = blockRGBString[id];
        }
        Log.e("EEE","data: "+data);
        return data;
    }

    private static double colorDistance(int[] c, int[] c1) {
        double d = Math.pow((Math.pow(c1[0] - c[0], 2) +
                Math.pow(c1[1] - c[1], 2) +
                Math.pow(c1[2] - c[2], 2) *
                        1.0), 0.5);
        return d;
    }
    public static void save(File file, short width, short height, short length, byte[]blocks, byte[]metaData) throws IOException {
        List<Tag> tags = new ArrayList<Tag>();
        tags.add(new ShortTag("Width", (short) width));
        tags.add(new ShortTag("Height", (short) height));
        tags.add(new ShortTag("Length", (short) length));
        tags.add(new StringTag("Materials", "Alpha")); //technically Pocket uses different materails, but this is the closest
        tags.add(new ByteArrayTag("Blocks", blocks));
        tags.add(new ByteArrayTag("Data", metaData));
        //TODO: entities
        tags.add(new ListTag<CompoundTag>("Entities", CompoundTag.class, (List<CompoundTag>) Collections.EMPTY_LIST));
        tags.add(new ListTag<CompoundTag>("TileEntities", CompoundTag.class, (List<CompoundTag>) Collections.EMPTY_LIST));
        CompoundTag mainTag = new CompoundTag("Schematic", tags);

        NBTOutputStream stream = new NBTOutputStream(new FileOutputStream(file));
        stream.writeTag(mainTag);
        stream.close();
    }
    public void checkPermission(String mManifestPermission, Callable callable) throws Exception {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permission = ActivityCompat.checkSelfPermission(this, mManifestPermission);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{mManifestPermission}, 0);
                }else {
                callable.call();
                return;
            }
        }
    }
    public byte[] output(byte[][] input){
        byte[] out = new byte[input.length * input[0].length];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                out[i + (j * input.length)] = input[i][j];
            }
        }
        return out;
    }
}