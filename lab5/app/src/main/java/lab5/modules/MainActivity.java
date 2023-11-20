package lab5.modules;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import lab5.modules.shape_editor.MyEditor;
import lab5.modules.shape_editor.MyEditorSingleton;
import lab5.modules.shape_editor.shapes.CircleEndedLineShape;
import lab5.modules.shape_editor.shapes.CubeShape;
import lab5.modules.shape_editor.shapes.EllipseShape;
import lab5.modules.shape_editor.shapes.LineShape;
import lab5.modules.shape_editor.shapes.PointShape;
import lab5.modules.shape_editor.shapes.RectangleShape;
import lab5.modules.shape_editor.shapes.Shape;
import lab5.modules.shape_editor.shapes.ShapeFactory;

public class MainActivity extends AppCompatActivity implements CallBack {
    private MyEditorSingleton mMyEditorSingleton;
    private Menu menu;
    private LinearLayout toolbar;
    private Table table;
    private List<File> listFiles;
    final boolean[] flag = {true};
    private LinearLayout mainLayout;
    private View tableView;
    MenuItem[] items;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        MyEditor myView = findViewById(R.id.shape_editing);
        mMyEditorSingleton = MyEditorSingleton.getInstance();
        mMyEditorSingleton.initialize(myView, this);
        toolbar = findViewById(R.id.tool_bar);
        createToolBar(toolbar);
        LayoutInflater inflater = getLayoutInflater();
        mainLayout = findViewById(R.id.main_layout);
        tableView = inflater.inflate(R.layout.table, mainLayout, false);
        mainLayout.addView(tableView);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TableLayout tableLayout = findViewById(R.id.table);
        table = new Table(this, tableLayout);
        table.callback = this;
        ImageButton buttonUp = findViewById(R.id.buttonUp);
        buttonUp.setOnClickListener(v -> {
            if (!flag[0]) {
                mainLayout.addView(tableView);
                flag[0] = true;
            }
        });
        ImageButton buttonDown = findViewById(R.id.buttonDown);
        buttonDown.setOnClickListener(v -> {
            if (flag[0]) {
                mainLayout.removeView(tableView);
                flag[0] = false;
            }
        });
        setListFiles();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        init();
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (!item.isChecked()) item.setChecked(true);
        if (id == R.id.point) {
            mMyEditorSingleton.start(new PointShape());
            chooseOption(0);
            return true;
        } else if (id == R.id.line) {
            mMyEditorSingleton.start(new LineShape());
            chooseOption(1);
            return true;
        } else if (id == R.id.rectangle) {
            mMyEditorSingleton.start(new RectangleShape());
            chooseOption(2);
            return true;
        } else if (id == R.id.ellipse) {
            mMyEditorSingleton.start(new EllipseShape());
            chooseOption(3);
            return true;
        } else if (id == R.id.circle_ended_line) {
            mMyEditorSingleton.start(new CircleEndedLineShape());
            chooseOption(4);
            return true;
        } else if (id == R.id.cube) {
            mMyEditorSingleton.start(new CubeShape());
            chooseOption(5);
            return true;
        } else if (id == R.id.save_as_file) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                writeToFile();
            }
            return true;
        } else if (id == R.id.load_as_file) {
            if (listFiles.isEmpty()) {
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setTitle(R.string.info_dialog_title1)
                        .setMessage(R.string.info_dialog_message1)
                        .setNeutralButton(R.string.info_dialog_button, (dialogInterface, i) -> dialogInterface.cancel());
                AlertDialog dialogInfo1 = builder1.create();
                dialogInfo1.show();
            } else {
                String[] strings = new String[listFiles.size()];
                for (int i = 0; i < listFiles.size(); i++) {
                    String[] data = String.valueOf(listFiles.get(i)).split("/");
                    strings[i] = data[data.length-1];
                }
                final File[] selected = new File[1]; // Змінна, що зберігатиме обраний файл
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle(R.string.info_dialog_title1)
                        .setPositiveButton(R.string.action_positive, (dialogInterface, i) -> {
                            try {
                                MyEditor myEditorView = mMyEditorSingleton.getMyView();
                                myEditorView.eraseAll();
                                loadFile(selected[0]);
                                myEditorView.invalidate();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        })
                        .setNegativeButton(R.string.info_dialog_button, (dialogInterface, i) -> {
                            dialogInterface.cancel();
                            selected[0] = null;
                        })
                        .setSingleChoiceItems(strings, -1, (dialogInterface, i) -> selected[0] = listFiles.get(i)); // Встановлення функції, що обробляє вибір групи користувача
                AlertDialog dialog = builder2.create();
                dialog.show();
            }
            return true;
        } else if (id == R.id.info) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.info_dialog_title)
                    .setMessage(R.string.info_dialog_message)
                    .setNeutralButton(R.string.info_dialog_button, (dialogInterface, i) -> dialogInterface.cancel());
            AlertDialog dialogInfo = builder.create();
            dialogInfo.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        items = new MenuItem[]{menu.findItem(R.id.point), menu.findItem(R.id.line),
                menu.findItem(R.id.rectangle), menu.findItem(R.id.ellipse),
                menu.findItem(R.id.circle_ended_line), menu.findItem(R.id.cube)};
    }

    private void createToolBar(LinearLayout toolbar) {
        LinearLayout.LayoutParams imageLayoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.FILL_PARENT);
        ImageButton tableButton = new ImageButton(this);
        tableButton.setImageResource(R.drawable.table_icon);
        tableButton.setLayoutParams(imageLayoutParams);
        tableButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag[0]) {
                    mainLayout.removeView(tableView);
                    flag[0] = false;
                } else {
                    mainLayout.addView(tableView);
                    flag[0] = true;
                }
            }
        });
        tableButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (flag[0]) {
                    mainLayout.removeView(tableView);
                    flag[0] = false;
                } else {
                    mainLayout.addView(tableView);
                    flag[0] = true;
                }
                showToolTip(view, R.string.tableName);
                return true;
            }
        });
        ImageButton pointButton = new ImageButton(this);
        pointButton.setImageResource(R.drawable.dot_icon);
        pointButton.setLayoutParams(imageLayoutParams);
        pointButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mMyEditorSingleton.start(new PointShape());
                chooseOption(0);
            }
        });
        pointButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mMyEditorSingleton.start(new PointShape());
                chooseOption(0);
                showToolTip(view, R.string.point);
                return true;
            }
        });
        ImageButton lineButton = new ImageButton(this);
        lineButton.setImageResource(R.drawable.line_icon);
        lineButton.setLayoutParams(imageLayoutParams);
        lineButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mMyEditorSingleton.start(new LineShape());
                chooseOption(1);
            }
        });
        lineButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mMyEditorSingleton.start(new LineShape());
                chooseOption(1);
                showToolTip(view, R.string.line);
                return true;
            }
        });
        ImageButton rectButton = new ImageButton(this);
        rectButton.setImageResource(R.drawable.rect_icon);
        rectButton.setLayoutParams(imageLayoutParams);
        rectButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mMyEditorSingleton.start(new RectangleShape());
                chooseOption(2);
            }
        });
        rectButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mMyEditorSingleton.start(new RectangleShape());
                chooseOption(2);
                showToolTip(view, R.string.rectangle);
                return true;
            }
        });
        ImageButton ellipseButton = new ImageButton(this);
        ellipseButton.setImageResource(R.drawable.ellipse_icon);
        ellipseButton.setLayoutParams(imageLayoutParams);
        ellipseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mMyEditorSingleton.start(new EllipseShape());
                chooseOption(3);
            }
        });
        ellipseButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mMyEditorSingleton.start(new EllipseShape());
                chooseOption(3);
                showToolTip(view, R.string.ellipse);
                return true;
            }
        });
        ImageButton eraserButton = new ImageButton(this);
        eraserButton.setImageResource(R.drawable.eraser_icon);
        eraserButton.setLayoutParams(imageLayoutParams);
        eraserButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MyEditor myEditorView = mMyEditorSingleton.getMyView();
                myEditorView.eraseLast();
            }
        });
        eraserButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                MyEditor myEditorView = mMyEditorSingleton.getMyView();
                myEditorView.eraseAll();
                showToolTip(view, R.string.erase);
                return true;
            }
        });
        ImageButton circleEndedLineButton = new ImageButton(this);
        circleEndedLineButton.setImageResource(R.drawable.circle_ended_line_icon);
        circleEndedLineButton.setLayoutParams(imageLayoutParams);
        circleEndedLineButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mMyEditorSingleton.start(new CircleEndedLineShape());
                chooseOption(4);
            }
        });
        circleEndedLineButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mMyEditorSingleton.start(new CircleEndedLineShape());
                chooseOption(4);
                showToolTip(view, R.string.circle_ended_line);
                return true;
            }
        });
        ImageButton cubeButton = new ImageButton(this);
        cubeButton.setImageResource(R.drawable.cube_icon);
        cubeButton.setLayoutParams(imageLayoutParams);
        cubeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mMyEditorSingleton.start(new CubeShape());
                chooseOption(5);
            }
        });
        cubeButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mMyEditorSingleton.start(new CubeShape());
                chooseOption(5);
                showToolTip(view, R.string.cube);
                return true;
            }
        });
        toolbar.addView(pointButton);
        toolbar.addView(lineButton);
        toolbar.addView(rectButton);
        toolbar.addView(ellipseButton);
        toolbar.addView(circleEndedLineButton);
        toolbar.addView(cubeButton);
        toolbar.addView(eraserButton);
        toolbar.addView(tableButton);
    }

    private void showToolTip(View view, int string) {
        Toast toast = Toast.makeText(view.getContext(), string, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }

    private void chooseOption(int index) {
        for (MenuItem item : items) {
            item.setChecked(false);
        }
        items[index].setChecked(true);
        for (int i = 0; i < 6; i++) {
            toolbar.getChildAt(i).getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
        }
        toolbar.getChildAt(index).getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public void addCallBack(Shape shape) {
        String name = shape.getNameOfShape();
        if (shape instanceof PointShape) {
            table.addRow(name, String.valueOf((int) shape.endX),String.valueOf((int) shape.endY),
                    String.valueOf((int) shape.endX), String.valueOf((int) shape.endY), Color.BLACK);
        } else {
            table.addRow(name, String.valueOf((int) shape.startX),String.valueOf((int) shape.startY),
                    String.valueOf((int) shape.endX), String.valueOf((int) shape.endY), Color.BLACK);
        }
    }

    @Override
    public void deleteCallBack(int index) {
        table.deleteRow(index);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void writeToFile() {
        try {
            MyEditor myEditorView = mMyEditorSingleton.getMyView();
            List<String> lines = new ArrayList<>();
            String dirPath = this.getApplicationInfo().dataDir;
            StringBuilder fileName = new StringBuilder("output");
            fileName.append(listFiles.size());
            fileName.append(".txt");
            File file = new File(dirPath, String.valueOf(fileName));
            file.setWritable(true);
            file.setReadable(true);
            FileWriter writer = new FileWriter(file);
            for (Shape shape : myEditorView.showedShapes) {
                StringBuilder line = new StringBuilder(shape.getNameOfShape());
                if (shape instanceof PointShape) {
                    line.append("\t").append((int) shape.endX);
                    line.append("\t").append((int) shape.endY);
                } else {
                    line.append("\t").append((int) shape.startX);
                    line.append("\t").append((int) shape.startY);
                }
                line.append("\t").append((int) shape.endX);
                line.append("\t").append((int) shape.endY);
                line.append("\n");
                lines.add(String.valueOf(line));
                writer.append(line);
            }
            writer.flush();
            writer.close();
            Toast toast = Toast.makeText(this, "Успішно збережено в"+file.getAbsolutePath(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            listFiles.add(file);
        } catch (IOException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(this, "Щось пішло не так", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
    public void loadFile(File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        final String[] names = new String[]{
                "Точка","Лінія","Прямокутник",
                "Еліпс","Лінія з кружечками","Куб"};
        String[] input = scan.nextLine().split("\t");
        boolean flag = false;
        for (String name: names) {
            if (name.equals(input[0])) {
                flag = true;
                break;
            }
        }
        if (flag) {
            createShapeToEditArray(input[0], Integer.parseInt(input[1]), Integer.parseInt(input[2]),
                    Integer.parseInt(input[3]), Integer.parseInt(input[4]));
            while (scan.hasNextLine()) {
                input = scan.nextLine().split("\t");
                createShapeToEditArray(input[0], Integer.parseInt(input[1]), Integer.parseInt(input[2]),
                        Integer.parseInt(input[3]), Integer.parseInt(input[4]));
            }
        } else {
            Toast toast = Toast.makeText(this, "Обраний файл \nне відповідає вимогам", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        scan.close();
    }

    // Функція, що перетворює дані з файлу
    public void createShapeToEditArray(String shapeName, int sx, int sy, int ex, int ey) {
        MyEditor myEditorView = mMyEditorSingleton.getMyView();
        Shape shape = ShapeFactory.createShapeByName(shapeName);
        assert shape != null;
        shape.startX = sx;
        shape.startY = sy;
        shape.endX = ex;
        shape.endY = ey;
        myEditorView.showedShapes.add(shape);
        this.addCallBack(shape);
    }

    @Override
    public void deleteByIndex(int index) {
        MyEditor myEditorView = mMyEditorSingleton.getMyView();
        myEditorView.eraseByIndex(index);
    }

    @Override
    public void chooseFigure(int index) {
        MyEditor myEditorView = mMyEditorSingleton.getMyView();
        Shape shape = myEditorView.showedShapes.get(index-1);
        shape.setChoosePaint(getPaintStroke(), getPaintFill());
        myEditorView.canvas.drawColor(Color.WHITE);
        myEditorView.invalidate();
    }

    @Override
    public void unChooseFigure(int index) {
        MyEditor myEditorView = mMyEditorSingleton.getMyView();
        myEditorView.showedShapes.get(index-1).setPaint();
        myEditorView.canvas.drawColor(Color.WHITE);
        myEditorView.invalidate();
    }

    private Paint getPaintFill() {
        Paint paint = new Paint(Paint.DITHER_FLAG);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.parseColor("#ffefcc"));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(5);
        return paint;
    }

    private Paint getPaintStroke() {
        Paint paint = new Paint(Paint.DITHER_FLAG);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#eba434"));
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(5);
        return paint;
    }

    private void setListFiles() {
        final String dir = this.getApplicationInfo().dataDir;
        File file = new File(dir);
        File[] listOfAllFiles = Objects.requireNonNull(file.listFiles());
        listFiles = new ArrayList<>();
        for (File file1 : listOfAllFiles) {
            String[] data = String.valueOf(file1).split("/");
            String name = data[data.length-1];
            if (!(name.equals("cache") || name.equals("code_cache") || name.equals("databases") || name.contains("agent-logs"))) {
                listFiles.add(file1);
            }
        }
    }
}