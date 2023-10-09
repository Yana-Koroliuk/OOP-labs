package lab2.modules;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.R;

import lab2.modules.shape_editor.ShapeObjectsEditor;
import lab2.modules.shape_editor.editor.EllipseShapeEditor;
import lab2.modules.shape_editor.editor.LineShapeEditor;
import lab2.modules.shape_editor.editor.PointShapeEditor;
import lab2.modules.shape_editor.editor.RectangleShapeEditor;

public class MainActivity extends AppCompatActivity {

    public ShapeObjectsEditor mShapeObjectsEditor;

    private Menu mainMenu;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mShapeObjectsEditor = new ShapeObjectsEditor(this);
        setContentView(mShapeObjectsEditor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mainMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuItem itemChosen = mainMenu.findItem(R.id.chosen);
        int id = item.getItemId();
        if (!item.isChecked()) item.setChecked(true);
        if (id == R.id.point) {
            mShapeObjectsEditor.currentEditor = new PointShapeEditor(mShapeObjectsEditor);
            itemChosen.setTitle(R.string.point);
            return true;
        } else if (id == R.id.line) {
            mShapeObjectsEditor.currentEditor = new LineShapeEditor(mShapeObjectsEditor);
            itemChosen.setTitle(R.string.line);
            return true;
        } else if (id == R.id.rectangle) {
            mShapeObjectsEditor.currentEditor = new RectangleShapeEditor(mShapeObjectsEditor);
            itemChosen.setTitle(R.string.rectangle);
            return true;
        } else if (id == R.id.ellipse) {
            mShapeObjectsEditor. currentEditor = new EllipseShapeEditor(mShapeObjectsEditor);
            itemChosen.setTitle(R.string.ellipse);
            return true;
        } else if (id == R.id.erase) {
            mShapeObjectsEditor.eraseLast();
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
}