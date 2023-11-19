package lab5.modules;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.R;

import lab5.modules.shape_editor.MyEditor;
import lab5.modules.shape_editor.shapes.CircleEndedLineShape;
import lab5.modules.shape_editor.shapes.CubeShape;
import lab5.modules.shape_editor.shapes.EllipseShape;
import lab5.modules.shape_editor.shapes.LineShape;
import lab5.modules.shape_editor.shapes.PointShape;
import lab5.modules.shape_editor.shapes.RectangleShape;

public class MainActivity extends AppCompatActivity {
    private static MyEditor mMyEditor;
    private Menu menu;
    private LinearLayout toolbar;
    MenuItem[] items;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mMyEditor = findViewById(R.id.shape_editing);
        toolbar = findViewById(R.id.tool_bar);
        createToolBar(toolbar);
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
            mMyEditor.start(new PointShape());
            chooseOption(0);
            return true;
        } else if (id == R.id.line) {
            mMyEditor.start(new LineShape());
            chooseOption(1);
            return true;
        } else if (id == R.id.rectangle) {
            mMyEditor.start(new RectangleShape());
            chooseOption(2);
            return true;
        } else if (id == R.id.ellipse) {
            mMyEditor.start(new EllipseShape());
            chooseOption(3);
            return true;
        } else if (id == R.id.circle_ended_line) {
            mMyEditor.start(new CircleEndedLineShape());
            chooseOption(4);
            return true;
        } else if (id == R.id.cube) {
            mMyEditor.start(new CubeShape());
            chooseOption(5);
            return true;
        } else if (id == R.id.info) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.info_dialog_title)
                    .setMessage(R.string.info_dialog_message)
                    .setNeutralButton(R.string.info_dialog_button, new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
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
        LinearLayout.LayoutParams imageLayoutParams = new
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.FILL_PARENT);
        ImageButton pointButton = new ImageButton(this);
        pointButton.setImageResource(R.drawable.dot_icon);
        pointButton.setLayoutParams(imageLayoutParams);
        pointButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mMyEditor.start(new PointShape());
                chooseOption(0);
            }
        });
        pointButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mMyEditor.start(new PointShape());
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
                mMyEditor.start(new LineShape());
                chooseOption(1);
            }
        });
        lineButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mMyEditor.start(new LineShape());
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
                mMyEditor.start(new RectangleShape());
                chooseOption(2);
            }
        });
        rectButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mMyEditor.start(new RectangleShape());
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
                mMyEditor.start(new EllipseShape());
                chooseOption(3);
            }
        });
        ellipseButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mMyEditor.start(new EllipseShape());
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
                mMyEditor.eraseLast();
            }
        });
        eraserButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mMyEditor.eraseAll();
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
                mMyEditor.start(new CircleEndedLineShape());
                chooseOption(4);
            }
        });
        circleEndedLineButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mMyEditor.start(new CircleEndedLineShape());
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
                mMyEditor.start(new CubeShape());
                chooseOption(5);
            }
        });
        cubeButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mMyEditor.start(new CubeShape());
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
            toolbar.getChildAt(i).getBackground().setColorFilter(Color.LTGRAY,
                    PorterDuff.Mode.MULTIPLY);
        }
        toolbar.getChildAt(index).getBackground().setColorFilter(Color.GRAY,
                PorterDuff.Mode.MULTIPLY);
    }
}