package com.koroliuk.painter;

import static com.koroliuk.painter.editor.PainterView.context;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.azeesoft.lib.colorpicker.ColorPickerDialog;
import com.koroliuk.painter.dialog.CreateDialog;
import com.koroliuk.painter.editor.PainterView;
import com.koroliuk.painter.editor.shapes.Shape;
import com.koroliuk.painter.editor.shapes.ShapeFactory;

import java.io.OutputStream;
import android.media.MediaScannerConnection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
public class MainActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    private static PainterView painterView;
    private static Menu menu;
    private Uri selectedImage;
    private boolean isToolbarShowed;
    private View toolbar;
    private LinearLayout mainLayout;
    private ArrayList<ImageButton> imageButtons;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        painterView = findViewById(R.id.painter_view);
        PainterView.context = this;
        isToolbarShowed = true;
        LayoutInflater inflater = getLayoutInflater();
        mainLayout = findViewById(R.id.main_linear);
        mainLayout.removeView(painterView);
        toolbar = inflater.inflate(R.layout.toolbar, mainLayout, false);
        mainLayout.addView(toolbar);
        mainLayout.addView(painterView);
        setToolBar();
        painterView.scrollView = findViewById(R.id.scroll);
        painterView.horizontalScrollView = findViewById(R.id.scroll_hor);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MainActivity.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.create:
                FragmentManager manager = getSupportFragmentManager();
                CreateDialog dialog = new CreateDialog(this, false);
                dialog.show(manager, "CreateDialog");
                break;
            case R.id.open:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
                break;
            case R.id.save:
                saveFile(selectedImage);
                break;
            case R.id.save_as:
                if (isExternalStorageWritable()) {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                    LayoutInflater inflater2 = Objects.requireNonNull(getLayoutInflater());
                    View view2 = inflater2.inflate(R.layout.save_dialog, null);
                    final String[] name = new String[1];
                    EditText editText2 = view2.findViewById(R.id.save_dialog);
                    builder2.setTitle(R.string.save_dialog_title)
                            .setView(view2)
                            .setPositiveButton(R.string.save, (dialog12, which) -> {
                                try {
                                    name[0] = String.valueOf(editText2.getText());
                                    Uri fileUri = painterView.saveFileAsPNG(name[0]);
                                    if (fileUri != null) {
                                        scanFile(MainActivity.this, fileUri);
                                    }
                                    Toast toast = Toast.makeText(MainActivity.this, "Файл збережено", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                } catch (Exception e) {
                                    Toast toast = Toast.makeText(MainActivity.this, "Перевірте коректність даних", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }
                            })
                            .setNegativeButton(R.string.dialog_negative_button, (dialog1, which) -> dialog1.cancel());
                    AlertDialog dialog2 = builder2.create();
                    dialog2.show();
                } else {
                    Toast toast = Toast.makeText(MainActivity.this, "Немає дозволу", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                break;
            case R.id.change:
                FragmentManager manager3 = getSupportFragmentManager();
                CreateDialog dialog3 = new CreateDialog(this, true);
                dialog3.color = painterView.backgroundColor;
                dialog3.show(manager3, "CreateDialog");
                break;
            case R.id.toolbar:
                if (isToolbarShowed) {
                    mainLayout.removeView(toolbar);
                } else {
                    mainLayout.removeView(painterView);
                    mainLayout.addView(toolbar);
                    mainLayout.addView(painterView);
                }
                isToolbarShowed = !isToolbarShowed;
                break;
            case R.id.undo:
                painterView.setUndoBitmap();
                break;
            case R.id.redo:
                painterView.setRedoBitmap();
                break;
            case R.id.zoom_in:
                painterView.zoomIn();
                break;
            case R.id.zoom_out:
                painterView.zoomOut();
                break;
            case R.id.exit:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void saveFile(Uri imageUri) {
        String mimeType = getMimeType(imageUri);
        ContentResolver resolver = context.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Image_" + System.currentTimeMillis());
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

        Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        try (OutputStream stream = resolver.openOutputStream(Objects.requireNonNull(uri))) {
            Bitmap.CompressFormat format = mimeType.equals("image/jpeg") ? Bitmap.CompressFormat.JPEG : Bitmap.CompressFormat.PNG;
            painterView.getBitmap().compress(format, 100, stream);
            Toast.makeText(context, "Файл збережено", Toast.LENGTH_SHORT).show();
            scanFile(context, imageUri);
        } catch (IOException e) {
            Toast.makeText(context, "Помилка при збереженні файла: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            if (uri != null) {
                resolver.delete(uri, null, null);
            }
        }
    }
    private String getMimeType(Uri uri) {
        ContentResolver resolver = context.getContentResolver();
        return resolver.getType(uri);
    }
    public void scanFile(Context context, Uri fileUri) {
        String[] paths = new String[] { fileUri.getPath() };
        MediaScannerConnection.scanFile(context, paths, null, null);
    }
    public static void enableSave() {
        MenuItem changeSizeMenuItem = menu.findItem(R.id.save);
        changeSizeMenuItem.setEnabled(true);
    }
    public static void enableChangeSize() {
        MenuItem changeSizeMenuItem = menu.findItem(R.id.change);
        changeSizeMenuItem.setEnabled(true);
    }
    public static void createDrawingPlace(int width, int height, String color, Bitmap imagineBitmap) {
        painterView.mainBitmap = (imagineBitmap != null) ? imagineBitmap : Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        painterView.setLayoutParams(params);
        painterView.backgroundColor = color;
    }
    public static void  changeSize(int w, int h, String color) {
        Bitmap resizedBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resizedBitmap);
        canvas.drawColor(Color.parseColor(color));
        if (painterView.mainBitmap != null) {
            canvas.drawBitmap(painterView.mainBitmap, 0, 0, null);
        }
        painterView.cleanupBitmaps();
        painterView.mainBitmap = resizedBitmap;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, h);
        painterView.setLayoutParams(params);
        painterView.backgroundColor = color;
        painterView.invalidate();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1 && data != null) {
            selectedImage = data.getData();
            if (selectedImage != null) {
                try {
                    enableChangeSize();
                    enableSave();
                    Bitmap immutableBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    Bitmap mutableBitmap = immutableBitmap.copy(Bitmap.Config.ARGB_8888, true);
                    MainActivity.createDrawingPlace(mutableBitmap.getWidth(), mutableBitmap.getHeight(), painterView.backgroundColor, mutableBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Помилка при завантаженні зображення", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
    @RequiresApi(api = Build.VERSION_CODES.R)
    public void  setToolBar() {
        ImageButton buttonThick = findViewById(R.id.thickness);
        ImageButton buttonSetFilled = findViewById(R.id.filled);
        ImageButton buttonBrush = findViewById(R.id.brush);
        ImageButton buttonLine = findViewById(R.id.line);
        ImageButton buttonRect = findViewById(R.id.rect);
        ImageButton buttonOval = findViewById(R.id.oval);
        ImageButton buttonCube = findViewById(R.id.cube);
        ImageButton buttonErasor = findViewById(R.id.erasor);
        List<String> labels = Arrays.asList("Пензлик", "Лінія", "Прямокутник", "Овал", "Куб", "Гумка");
        imageButtons = new ArrayList<>();
        imageButtons.add(buttonBrush);
        imageButtons.add(buttonLine);
        imageButtons.add(buttonRect);
        imageButtons.add(buttonOval);
        imageButtons.add(buttonCube);
        imageButtons.add(buttonErasor);
        LinearLayout layoutStroke = findViewById(R.id.stroke);
        LinearLayout layoutFill = findViewById(R.id.fill);
        buttonThick.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater inflater = Objects.requireNonNull(getLayoutInflater());
            View view = inflater.inflate(R.layout.thickness_dialog, null);
            EditText editTextWidth = view.findViewById(R.id.get_width);
            builder.setTitle(R.string.get_width_title)
                    .setView(view)
                    .setPositiveButton(R.string.create_dialog_positive_button, (dialog, which) -> {
                        try {
                            painterView.width = Integer.parseInt(String.valueOf(editTextWidth.getText()));
                        } catch (Exception e) {
                            Toast toast = Toast.makeText(MainActivity.this, "Перевірте коректність вхідних даних", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    })
                    .setNegativeButton(R.string.dialog_negative_button, (dialog, which) -> dialog.cancel());
            builder.create();
            AlertDialog dialog1 = builder.create();
            dialog1.show();
        });
        buttonThick.setOnLongClickListener(v -> {
            Toast toast = Toast.makeText(MainActivity.this, "Товщина", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
            return true;
        });
        buttonSetFilled.setOnClickListener(v -> {
            painterView.isFilled = !painterView.isFilled;
            if (painterView.isFilled) {
                buttonSetFilled.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                Drawable drawable = layoutFill.getBackground();
                if (drawable instanceof ColorDrawable) {
                    int color = ((ColorDrawable) drawable).getColor();
                    painterView.paintFill.setColor(color);
                }
            } else {
                buttonSetFilled.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
                painterView.paintFill.setColor(Color.TRANSPARENT);
            }
        });

        buttonSetFilled.setOnLongClickListener(v -> {
            Toast toast = Toast.makeText(MainActivity.this, "Заповнення", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
            return true;
        });

        for (int i = 0; i < imageButtons.size(); i++) {
            ImageButton button = imageButtons.get(i);
            int finalI = i;
            button.setOnLongClickListener(v -> {
                Toast toast = Toast.makeText(MainActivity.this, labels.get(finalI), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();
                return true;
            });
            button.setOnClickListener(v -> {
                if (painterView.selectedType == finalI +1) {
                    painterView.selectedType = 0;
                    painterView.selectedTool = "Пензлик".equals(labels.get(finalI)) || "Гумка".equals(labels.get(finalI));
                    painterView.scrollView.setEnableScrolling(true);
                    painterView.horizontalScrollView.setEnableScrolling(true);
                    button.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);

                } else {
                    painterView.scrollView.setEnableScrolling(false);
                    painterView.horizontalScrollView.setEnableScrolling(false);
                    optionOn(button);
                    painterView.selectedType = finalI +1;
                    painterView.selectedTool = "Пензлик".equals(labels.get(finalI)) || "Гумка".equals(labels.get(finalI));
                    Shape currentShape = ShapeFactory.createShapeByName(labels.get(finalI));
                    painterView.start(currentShape);
                }
            });
        }

        LinearLayout layout1 = findViewById(R.id.linear1);
        LinearLayout layout2 = findViewById(R.id.linear2);
        LinearLayout layout3 = findViewById(R.id.linear3);
        LinearLayout layout4 = findViewById(R.id.linear4);
        LinearLayout layout5 = findViewById(R.id.linear5);
        LinearLayout layout6 = findViewById(R.id.linear6);
        LinearLayout layout7 = findViewById(R.id.linear7);
        LinearLayout layout8 = findViewById(R.id.linear8);
        LinearLayout layout9 = findViewById(R.id.linear9);
        LinearLayout layout10 = findViewById(R.id.linear10);
        LinearLayout layout11 = findViewById(R.id.linear11);
        LinearLayout layout12 = findViewById(R.id.linear12);
        painterView.paintStroke.setColor(Color.BLACK);
        List<LinearLayout> layouts = Arrays.asList(layout1, layout2, layout3, layout4,
                layout5, layout6, layout7, layout8,
                layout9, layout10);
        List<LinearLayout> spec = Arrays.asList(layout11, layout12);

        for (LinearLayout layout : layouts) {
            layout.setOnClickListener(v -> {
                Drawable drawable = layout.getBackground();
                if (drawable instanceof ColorDrawable) {
                    int color = ((ColorDrawable) drawable).getColor();
                    layoutStroke.setBackgroundColor(color);
                    painterView.paintStroke.setColor(color);
                }
            });
            layout.setOnLongClickListener(v -> {
                buttonSetFilled.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                Drawable drawable = layout.getBackground();
                if (drawable instanceof ColorDrawable) {
                    int color = ((ColorDrawable) drawable).getColor();
                    layoutFill.setBackgroundColor(color);
                    painterView.paintFill.setColor(color);
                }
                return true;
            });
        }
        for (LinearLayout layout : spec) {
            layout.setOnClickListener(v -> {
                Drawable drawable = layout.getBackground();
                if (drawable instanceof ColorDrawable) {
                    int color = ((ColorDrawable) drawable).getColor();
                    StringBuilder c = new StringBuilder("");
                    ColorPickerDialog colorPickerDialog = ColorPickerDialog.createColorPickerDialog(this);
                    colorPickerDialog.setPositiveActionText("Обрати");
                    colorPickerDialog.setNegativeActionText("Назад");
                    colorPickerDialog.setLastColor(color);
                    colorPickerDialog.setInitialColor(Color.BLUE);
                    colorPickerDialog.setSliderThumbColor(Color.RED);
                    colorPickerDialog.setOnColorPickedListener((chosenColor, hexVal) -> {
                        c.append(hexVal);
                        layout.setBackgroundColor(Color.parseColor(hexVal));
                        layoutStroke.setBackgroundColor(Color.parseColor(String.valueOf(c)));
                        painterView.paintStroke.setColor(Color.parseColor(String.valueOf(c)));
                    });
                    colorPickerDialog.show();
                }
            });
            layout.setOnLongClickListener(v -> {
                buttonSetFilled.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                Drawable drawable = layout.getBackground();
                if (drawable instanceof ColorDrawable) {
                    int color = ((ColorDrawable) drawable).getColor();
                    StringBuilder c = new StringBuilder("");
                    ColorPickerDialog colorPickerDialog = ColorPickerDialog.createColorPickerDialog(this);
                    colorPickerDialog.setPositiveActionText("Обрати");
                    colorPickerDialog.setNegativeActionText("Назад");
                    colorPickerDialog.setLastColor(color);
                    colorPickerDialog.setInitialColor(color);
                    colorPickerDialog.setSliderThumbColor(Color.RED);
                    colorPickerDialog.setOnColorPickedListener((chosenColor, hexVal) -> {
                        c.append(hexVal);
                        layout.setBackgroundColor(Color.parseColor(hexVal));
                        layoutFill.setBackgroundColor(Color.parseColor(String.valueOf(c)));
                        painterView.paintFill.setColor(Color.parseColor(String.valueOf(c)));
                    });
                    colorPickerDialog.show();
                }
                return true;
            });
        }
    }
    public void optionOn(ImageButton button) {
        for (ImageButton button1 : imageButtons) {
            button1.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
        }
        button.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
    }
}