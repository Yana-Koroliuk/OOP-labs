package lab3.modules;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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

import lab3.modules.shape_editor.ShapeObjectsEditor;
import lab3.modules.shape_editor.editor.EllipseShapeEditor;
import lab3.modules.shape_editor.editor.LineShapeEditor;
import lab3.modules.shape_editor.editor.PointShapeEditor;
import lab3.modules.shape_editor.editor.RectangleShapeEditor;

public class MainActivity extends AppCompatActivity {

    public ShapeObjectsEditor mShapeObjectsEditor;
    private Menu menu;
    private LinearLayout toolbar;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mShapeObjectsEditor = findViewById(R.id.shape_editing);
        toolbar = findViewById(R.id.tool_bar);
        createToolBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //MenuItem itemChosen = mainMenu.findItem(R.id.chosen);
        int id = item.getItemId();
        if (!item.isChecked()) item.setChecked(true);
        if (id == R.id.point) {
            mShapeObjectsEditor.currentEditor = new PointShapeEditor(mShapeObjectsEditor);
            //itemChosen.setTitle(R.string.point);
            return true;
        } else if (id == R.id.line) {
            mShapeObjectsEditor.currentEditor = new LineShapeEditor(mShapeObjectsEditor);
            //itemChosen.setTitle(R.string.line);
            return true;
        } else if (id == R.id.rectangle) {
            mShapeObjectsEditor.currentEditor = new RectangleShapeEditor(mShapeObjectsEditor);
            //itemChosen.setTitle(R.string.rectangle);
            return true;
        } else if (id == R.id.ellipse) {
            mShapeObjectsEditor. currentEditor = new EllipseShapeEditor(mShapeObjectsEditor);
            //itemChosen.setTitle(R.string.ellipse);
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
    private void createToolBar(LinearLayout toolbar) {
        LinearLayout.LayoutParams imageLayoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.FILL_PARENT);
        ImageButton pointButton = new ImageButton(this);
        pointButton.setImageResource(R.drawable.dot_icon);
        pointButton.setLayoutParams(imageLayoutParams);
        pointButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mShapeObjectsEditor.currentEditor = new PointShapeEditor(mShapeObjectsEditor);
                MenuItem item = menu.findItem(R.id.point);
                item.setChecked(true);
            }
        });
        pointButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mShapeObjectsEditor.currentEditor = new PointShapeEditor(mShapeObjectsEditor);
                MenuItem item = menu.findItem(R.id.point);
                item.setChecked(true);
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
                mShapeObjectsEditor.currentEditor = new LineShapeEditor(mShapeObjectsEditor);
                MenuItem item = menu.findItem(R.id.line);
                item.setChecked(true);
            }
        });
        pointButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mShapeObjectsEditor.currentEditor = new LineShapeEditor(mShapeObjectsEditor);
                MenuItem item = menu.findItem(R.id.line);
                item.setChecked(true);
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
                mShapeObjectsEditor.currentEditor = new RectangleShapeEditor(mShapeObjectsEditor);
                MenuItem item = menu.findItem(R.id.rectangle);
                item.setChecked(true);
            }
        });
        rectButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mShapeObjectsEditor.currentEditor = new RectangleShapeEditor(mShapeObjectsEditor);
                MenuItem item = menu.findItem(R.id.rectangle);
                item.setChecked(true);
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
                mShapeObjectsEditor. currentEditor = new EllipseShapeEditor(mShapeObjectsEditor);
                MenuItem item = menu.findItem(R.id.ellipse);
                item.setChecked(true);
            }
        });
        ellipseButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mShapeObjectsEditor. currentEditor = new EllipseShapeEditor(mShapeObjectsEditor);
                MenuItem item = menu.findItem(R.id.ellipse);
                item.setChecked(true);
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
                mShapeObjectsEditor.eraseLast();
            }
        });
        eraserButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mShapeObjectsEditor.eraseAll();
                showToolTip(view, R.string.erase);
                return true;
            }
        });
        toolbar.addView(pointButton);
        toolbar.addView(lineButton);
        toolbar.addView(rectButton);
        toolbar.addView(ellipseButton);
        toolbar.addView(eraserButton);
    }
    private void showToolTip(View view, int string) {
        Toast toast = Toast.makeText(view.getContext(), string, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }
}